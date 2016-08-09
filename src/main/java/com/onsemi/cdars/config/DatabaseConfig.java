package com.onsemi.cdars.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:db.properties")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        //BasicDataSource
        /*BasicDataSource dataSource = new BasicDataSource();
         dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
         dataSource.setUsername(env.getProperty("jdbc.username"));
         dataSource.setPassword(env.getProperty("jdbc.password"));
         dataSource.setUrl(env.getProperty("jdbc.url"));
         return dataSource;*/

        //BoneCPDataSource
        /*BoneCPDataSource dataSource = new BoneCPDataSource();
         dataSource.setDriverClass(env.getProperty("jdbc.driver"));
         dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
         dataSource.setUsername(env.getProperty("jdbc.username"));
         dataSource.setPassword(env.getProperty("jdbc.password"));
         dataSource.setIdleConnectionTestPeriodInSeconds(Long.parseLong(env.getProperty("jdbc.bonecp.idle_connection_test_period")));
         dataSource.setIdleMaxAgeInSeconds(Long.parseLong(env.getProperty("jdbc.bonecp.idle_max_age")));
         dataSource.setMaxConnectionsPerPartition(Integer.parseInt(env.getProperty("jdbc.bonecp.max_connections_per_partition")));
         dataSource.setMinConnectionsPerPartition(Integer.parseInt(env.getProperty("jdbc.bonecp.min_connections_per_partition")));
         dataSource.setPartitionCount(Integer.parseInt(env.getProperty("jdbc.bonecp.partition_count")));
         dataSource.setAcquireIncrement(Integer.parseInt(env.getProperty("jdbc.bonecp.acquire_increment")));
         dataSource.setStatementsCacheSize(Integer.parseInt(env.getProperty("jdbc.bonecp.statements_cache_size")));
         return dataSource;*/
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setLeakDetectionThreshold(2000);

        return dataSource;
    }
}
