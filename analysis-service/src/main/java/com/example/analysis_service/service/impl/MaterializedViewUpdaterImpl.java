package com.example.analysis_service.service.impl;

import com.example.analysis_service.service.MaterializedViewUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Set;

@Service
@EnableScheduling
public class MaterializedViewUpdaterImpl implements MaterializedViewUpdater {

    private static final Logger logger = LoggerFactory.getLogger(MaterializedViewUpdaterImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 1 0 * * *")
    public void refreshAllMaterializedViews() {
        try {
            logger.info("Refreshing all materialized views.");
            refreshMonthly();
            refreshDaily(LocalDate.now().minusDays(1));
        } catch (Exception e) {
            logger.error("Error refreshing all materialized views", e);
            throw new RuntimeException("Error refreshing all materialized views", e);
        }
    }

    public void refreshMonthly() {
        try {
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY monthly_materialized;");
            redisTemplate.delete("analytics:monthly");
        } catch (Exception e) {
            logger.error("Error refreshing monthly materialized view", e);
            throw new RuntimeException("Error refreshing monthly materialized view", e);
        }
    }

    public void refreshDaily(LocalDate localDate) {
        try {
            Integer existing = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM daily_materialized WHERE date = ?""", Integer.class, localDate);

            if (existing == 0) {
                jdbcTemplate.update("""
                        INSERT INTO daily_sales_materialized (date, chain_name, product_name, units, actual_price, promo_tag)
                        SELECT a.date, c.chain_name, p.description, a.units, a.actual_price, a.promo_tag
                        FROM actuals a
                        JOIN customers c ON a.delivery_address_code = c.address_code
                        JOIN products p ON a.material_no = p.material_no
                        WHERE a.date = ?
                    """, localDate);
            }

            Set<String> keys = redisTemplate.keys("analytics:daily:*" + localDate.toString() + "*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            logger.error("Error refreshing daily materialized view for date {}", localDate, e);
            throw new RuntimeException("Error refreshing daily materialized view", e);
        }
    }

    public void refreshDailyFull() {
        try {
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY monthly_materialized;");
            redisTemplate.delete("analytics:monthly");
        } catch (Exception e) {
            logger.error("Error refreshing full daily materialized view", e);
            throw new RuntimeException("Error refreshing full daily materialized view", e);
        }
    }
}
