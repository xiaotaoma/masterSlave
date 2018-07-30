package com.mxt.mysql.masterSlave.config;

public enum  DataSourceType {
    master("write"), slave("read");

    DataSourceType(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataSourceType{" +
                "name='" + name + '\'' +
                '}';
    }
}
