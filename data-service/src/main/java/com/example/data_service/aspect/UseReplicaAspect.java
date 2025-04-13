package com.example.data_service.aspect;

import com.example.data_service.context.DBType;
import com.example.data_service.context.DbContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Аспект для переключения на реплику, если используется аннотация @UseReplica
 */
@Aspect
@Component
public class UseReplicaAspect {

    private static final Logger logger = LoggerFactory.getLogger(UseReplicaAspect.class);

    @Before("@within(com.example.replication.annotation.UseReplica) || " +
            "@annotation(com.example.replication.annotation.UseReplica)")
    public void useReplica() {
        logger.info("Switching to replica DB...");
        DbContextHolder.set(DBType.REPLICA);
    }

    @After("@within(com.example.replication.annotation.UseReplica) || " +
            "@annotation(com.example.replication.annotation.UseReplica)")
    public void clear() {
        logger.info("Clearing DB context (switching back to primary DB).");
        DbContextHolder.clear();
    }
}