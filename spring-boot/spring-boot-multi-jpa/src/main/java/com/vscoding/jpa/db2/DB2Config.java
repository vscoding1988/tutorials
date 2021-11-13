package com.vscoding.jpa.db2;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource("classpath:db2-config.properties")
@EnableJpaRepositories(
        basePackages = "com.vscoding.jpa.db2.entity",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager"
)
public class DB2Config {

  @Bean
  @ConfigurationProperties(prefix = "db2.datasource")
  public DataSource db2DataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public FactoryBean<EntityManagerFactory> db2EntityManagerFactory(@Qualifier("db2DataSource") DataSource db2DataSource, Environment env){
    var em = new LocalContainerEntityManagerFactoryBean();
    var va = new HibernateJpaVendorAdapter();
    var properties = new HashMap<String,Object>();

    properties.put("hibernate.hbm2ddl.auto", env.getProperty("db2.hibernate.hbm2ddl.auto"));
    em.setDataSource(db2DataSource);
    em.setPackagesToScan("com.vscoding.jpa.db2.entity");
    em.setJpaVendorAdapter(va);
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public TransactionManager db2TransactionManager(@Qualifier("db2EntityManagerFactory") FactoryBean<EntityManagerFactory> db2EntityManagerFactory) throws Exception {
    var tm = new JpaTransactionManager();

    tm.setEntityManagerFactory(db2EntityManagerFactory.getObject());

    return tm;
  }

  @Bean
  @Profile("with-init")
  public DataSourceInitializer dataSourceInitializer2(@Qualifier("db2DataSource") DataSource datasource) {
    var populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("db2.sql"));

    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(datasource);
    dataSourceInitializer.setDatabasePopulator(populator);

    return dataSourceInitializer;
  }
}
