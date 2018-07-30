package com.mxt.mysql.masterSlave.config;

public class DatabaseContextHolder {
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DataSourceType type) {
        contextHolder.set(type);
    }

    public static DataSourceType getDatabaseType() {
        return contextHolder.get();
    }
}
