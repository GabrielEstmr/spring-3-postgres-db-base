package com.challengers.accounts.configs.postgres;

import java.util.Properties;


import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// @PropertySource("file:C:/springconfig/qpmlib.properties")
@ComponentScan(basePackages = {"com.challengers.accounts.gateways.output.postgress.repositories"})
@EnableJpaRepositories(basePackages = {"com.challengers.accounts.gateways.output.postgress.repositories"})
@EnableTransactionManagement
public abstract class PGDBConfig {

  @Autowired org.springframework.core.env.Environment env;

  public static final String DB_NAME = "app_account_challenger";

  @Bean
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dmds = new DriverManagerDataSource();
    dmds.setDriverClassName("org.postgresql.Driver");
    dmds.setUrl("jdbc:postgresql://localhost:5432/" + DB_NAME);
    dmds.setUsername(env.getProperty("admin"));
    dmds.setPassword(env.getProperty("admin"));
    return dmds;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setPersistenceUnitName(DB_NAME);
    factory.setPackagesToScan("test.postgressql");
    factory.setJpaVendorAdapter(jpaAdapter());
    factory.setJpaProperties(jpaProperties());
    factory.afterPropertiesSet();
    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txm = new JpaTransactionManager(entityManagerFactory().getObject());
    return txm;
  }

  @Bean
  public JpaVendorAdapter jpaAdapter() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabase(Database.POSTGRESQL);
    adapter.setGenerateDdl(true);
    adapter.setShowSql(true);
    return adapter;
  }

  @Bean
  public HibernateExceptionTranslator exceptionTranslator() {
    return new HibernateExceptionTranslator();
  }

  public Properties jpaProperties() {
    Properties properties = new Properties();
    properties.put(Environment.SHOW_SQL, "true");
    properties.put(Environment.HBM2DDL_AUTO, "create");
    properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
    return properties;
  }
}
