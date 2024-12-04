package com.fileconsumer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

    @Configuration
    public class DataSourceConfig {

        @Bean
        public DataSource dataSource() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/yourdatabasename");
            config.setUsername("yourusername");
            config.setPassword("yourpassword");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // HikariCP-specific settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(20000);
            config.setMaxLifetime(1800000);

            return new HikariDataSource(config);
        }
    }


