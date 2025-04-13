package com.example.data_service.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.datasource")
public class MultiDataSourceProperties {

    private DataSourceProperties primary;
    private List<DataSourceProperties> replicas;

    public DataSourceProperties getPrimary() {
        return primary;
    }

    public void setPrimary(DataSourceProperties primary) {
        this.primary = primary;
    }

    public List<DataSourceProperties> getReplicas() {
        return replicas;
    }

    public void setReplicas(List<DataSourceProperties> replicas) {
        this.replicas = replicas;
    }
}
