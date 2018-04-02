package com.domain.servicedesk.wallscreen.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

  @Value("${spring.profiles.active}")
  private String activeProfile;
  
  @Bean(name = "dataSource")
  public DriverManagerDataSource dataSource() {
      DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
      driverManagerDataSource.setDriverClassName("org.h2.Driver");
      driverManagerDataSource.setUsername("sa");
      if ("prod".equals(activeProfile)) {
          driverManagerDataSource.setUrl("jdbc:h2:./db/prod/bin;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
          driverManagerDataSource.setPassword("pr0ds3cr3t");
      } else if ("test".equals(activeProfile)) {
          driverManagerDataSource.setUrl("jdbc:h2:./db/test/bin;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
          driverManagerDataSource.setPassword("t3sts3cr3t");
      } else if ("dev".equals(activeProfile)) {
        driverManagerDataSource.setUrl("jdbc:h2:./db/dev/bin;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
        //driverManagerDataSource.setPassword("d3vs3cr3t");
      }
      return driverManagerDataSource;
  }
  
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
     JpaTransactionManager transactionManager = new JpaTransactionManager();
     transactionManager.setEntityManagerFactory(emf);
     return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
     return new PersistenceExceptionTranslationPostProcessor();
  }
  
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(new String[] { "com.domain.servicedesk.wallscreen.repository", "com.domain.servicedesk.wallscreen.jpa" });
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());
    return em;
  }
  
  Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    properties.setProperty("h2.console.settings.web-allow-others", "true");
    if ("prod".equals(activeProfile)) {
      properties.setProperty("hibernate.show_sql", "false");
    } else if ("test".equals(activeProfile)) {
      properties.setProperty("hibernate.show_sql", "true");
    } else if ("dev".equals(activeProfile)) {
      properties.setProperty("hibernate.show_sql", "true");
    }
    return properties;
 }

}
