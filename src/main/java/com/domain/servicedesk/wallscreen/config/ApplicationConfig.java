package com.domain.servicedesk.wallscreen.config;

import java.io.File;

import javax.servlet.Servlet;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.domain.servicedesk.wallscreen.app.ApplicationContextProvider;
import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
@ComponentScan(basePackages = {"com.domain.servicedesk.wallscreen.view.controller", "com.domain.servicedesk.wallscreen.rest.controller", "com.domain.servicedesk.wallscreen.wrapper.controller",
                               "com.domain.servicedesk.wallscreen.service", "com.domain.servicedesk.wallscreen.mapper",
                               "com.domain.servicedesk.wallscreen.errorhandling", "com.domain.servicedesk.wallscreen.scheduled",
                               "com.domain.servicedesk.wallscreen.security", "com.domain.servicedesk.wallscreen.repository"})
@EnableScheduling
public class ApplicationConfig {

  @Bean
  public ServletRegistrationBean h2servletRegistration(){
      ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
      registrationBean.addUrlMappings("/h2-console/*");
      return registrationBean;
  }

  @Bean
  public ApplicationContextProvider applicationContextProvider() {
    return new ApplicationContextProvider();
  }
  
  /*
  @Bean
  public EmbeddedServletContainerCustomizer customizer() {
      return new EmbeddedServletContainerCustomizer() {

          @Override
          public void customize(ConfigurableEmbeddedServletContainer container) {
              if (container instanceof TomcatEmbeddedServletContainerFactory) {
                  customizeTomcat((TomcatEmbeddedServletContainerFactory) container);
              }
          }

          private void customizeTomcat(
              TomcatEmbeddedServletContainerFactory tomcat) {
                  tomcat.setDocumentRoot(new File("src/main/ui"));
              }
      };
  }
  /**/

  @Bean
  @ConditionalOnClass({Servlet.class, Tomcat.class }) 
  public EmbeddedServletContainerCustomizer containerCustomizer(){
      return new EmbeddedServletContainerCustomizer() {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer factory) {
          if(factory instanceof TomcatEmbeddedServletContainerFactory){
            TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
            
            LogbackValve logbackValve = new LogbackValve();
            logbackValve.setFilename("logback-access.xml");
            //containerFactory.setBaseDirectory(new File("."));
            containerFactory.addContextValves(logbackValve);
          }
        }
      };
  }
  /**/
}
