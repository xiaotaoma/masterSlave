package com.mxt.mysql.masterSlave.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Value("${datasource.driverClassName}")
    private String driverName;

    @Value("${datasource.master.url}")
    private String masterUrl;
    @Value("${datasource.master.username}")
    private String masterUserName;
    @Value("${datasource.master.password}")
    private String masterPassword;
    @Value("${datasource.slave01.url}")
    private String slaveUrl1;
    @Value("${datasource.slave01.username}")
    private String slaveUserName1;
    @Value("${datasource.slave01.password}")
    private String slavePassword1;
    @Value("${datasource.slave02.url}")
    private String slaveUrl2;
    @Value("${datasource.slave02.username}")
    private String slaveUserName2;
    @Value("${datasource.slave02.password}")
    private String slavePassword2;

    @Autowired
    private Environment env;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(masterUrl);
        dataSource.setUsername(masterUserName);
        dataSource.setPassword(masterPassword);
        dataSource.setDriverClassName(driverName);
        return dataSource;
    }

    @Bean(name = "slaveDataSource1")
    public DataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slaveUrl1);
        dataSource.setUsername(slaveUserName1);
        dataSource.setPassword(slavePassword1);
        dataSource.setDriverClassName(driverName);
        return dataSource;
    }

    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource master,
                                               @Qualifier("slaveDataSource1") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.master, master);
        targetDataSources.put(DataSourceType.slave, slave);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(master);// 默认的datasource设置为myTestDbDataSourcereturn dataSource;

        String read = env.getProperty("spring.datasource.read");
        String write = env.getProperty("spring.datasource.write");

        dataSource.setMethodType(DataSourceType.slave, read);
        dataSource.setMethodType(DataSourceType.master, write);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource myTestDbDataSource,
                                               @Qualifier("slaveDataSource1") DataSource myTestDb2DataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dynamicDataSource(myTestDbDataSource, myTestDb2DataSource));
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }
}
