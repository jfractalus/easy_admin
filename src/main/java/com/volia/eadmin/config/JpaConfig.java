package com.volia.eadmin.config;

import com.volia.eadmin.core.service.SecurityService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.volia.eadmin.repository"})
public class JpaConfig extends HikariConfig {
    @Value("${spring.datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${spring.jpa.hibernate.dialect}")
    private String dialect;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Value("${spring.jpa.hibernate.show-sql}")
    private String showSql;
    @Value("${spring.jpa.hibernate.format_sql}")
    private String formatSql;
    @Value("${spring.flyway.migrate}")
    private boolean migrate;
    @Autowired
    private SecurityService securityService;

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.volia.eadmin.domain");
        entityManagerFactoryBean.setJpaProperties(createJpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        setMaximumPoolSize(10);
        setDataSourceClassName(dataSourceClassName);
        addDataSourceProperty("url", jdbcUrl);
        addDataSourceProperty("user", username);
        addDataSourceProperty("password", securityService.decryptPassword(password));
        return new HikariDataSource(this);
    }

    @Bean
    public Flyway flyway() throws SQLException{
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setEncoding("UTF-8");
        flyway.setTable("schema_version");
        if(migrate){
            flyway.setBaselineVersion(MigrationVersion.fromVersion("1.0"));
            flyway.baseline();
            flyway.migrate();
        }
        return flyway;
    }

    private Properties createJpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", dialect);
        jpaProperties.put("hibernate.hbm2ddl.auto", ddl);
        jpaProperties.put("hibernate.show_sql", showSql);
        jpaProperties.put("hibernate.format_sql", formatSql);
        return jpaProperties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
