package com.example.data_service.config;

import com.example.data_service.context.DBType;
import com.example.data_service.routing.RoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class DataSourceConfig {

    @Bean
    public DataSource primaryDataSource(MultiDataSourceProperties properties) {
        return properties.getPrimary().initializeDataSourceBuilder().build();
    }

    @Bean
    public List<DataSource> replicaDataSources(MultiDataSourceProperties properties) {
        return properties.getReplicas().stream()
                .map(DataSourceProperties::initializeDataSourceBuilder)
                .map(builder -> builder.build())
                .collect(Collectors.toList());
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("primaryDataSource") DataSource primary,
            @Qualifier("replicaDataSources") List<DataSource> replicas) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBType.PRIMARY, primary);
        for (int i = 0; i < replicas.size(); i++) {
            targetDataSources.put("replica" + i, replicas.get(i));
        }

        RoutingDataSource routingDataSource = new RoutingDataSource(replicas);
        routingDataSource.setDefaultTargetDataSource(primary);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }
}