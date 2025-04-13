package com.example.data_service.routing;

import com.example.data_service.context.DBType;
import com.example.data_service.context.DbContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

    private final List<DataSource> replicas;
    private final AtomicInteger counter = new AtomicInteger(0);

    public RoutingDataSource(List<DataSource> replicas) {
        this.replicas = replicas;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DBType dbType = DbContextHolder.get();
        if (dbType == DBType.REPLICA && !replicas.isEmpty()) {
            int index = counter.getAndIncrement() % replicas.size();
            logger.debug("Routing to replica: replica{}", index);  // DEBUG level to show replica selection
            return "replica" + index;
        }
        logger.debug("Routing to primary DB");
        return DBType.PRIMARY;
    }
}
