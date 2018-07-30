package com.mxt.mysql.masterSlave.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    static final Map<DataSourceType, List<String>> methodTypeMap = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType type = DatabaseContextHolder.getDatabaseType();
        logger.info("====================dataSource ==========" + type);
        return type;
    }

    public void setMethodType(DataSourceType type, String content) {
        List<String> list = Arrays.asList(content.split(","));
        methodTypeMap.put(type, list);
    }
}
