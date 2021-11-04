package com.vscoding.jpa.db1;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource("classpath:db1-config.properties")
@EnableJpaRepositories(
        basePackages = "com.vscoding.jpa.db1.entity",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager"
)
public class DB1Config {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "db1.datasource")
  public DataSource db1DataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public FactoryBean<EntityManagerFactory> db1EntityManagerFactory(@Qualifier("db1DataSource") DataSource db1DataSource, Environment env){
    var em = new LocalContainerEntityManagerFactoryBean();
    var va = new HibernateJpaVendorAdapter();
    var properties = new HashMap<String,Object>();

    properties.put("hibernate.hbm2ddl.auto","create");
    em.setDataSource(db1DataSource);
    em.setPackagesToScan("com.vscoding.jpa.db1.entity");
    em.setJpaVendorAdapter(va);
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public PlatformTransactionManager db1TransactionManager(@Qualifier("db1EntityManagerFactory") FactoryBean<EntityManagerFactory> db1EntityManagerFactory) throws Exception {
    var tm = new JpaTransactionManager();

    tm.setEntityManagerFactory(db1EntityManagerFactory.getObject());

    return tm;
  }
}
