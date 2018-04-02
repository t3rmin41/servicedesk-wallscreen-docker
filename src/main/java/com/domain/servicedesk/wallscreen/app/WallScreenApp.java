package com.domain.servicedesk.wallscreen.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.domain.servicedesk.wallscreen.config.ApplicationConfig;
import com.domain.servicedesk.wallscreen.config.JpaConfig;
import com.domain.servicedesk.wallscreen.config.MvcConfig;
import com.domain.servicedesk.wallscreen.config.SwaggerConfig;
import com.domain.servicedesk.wallscreen.security.SecurityConfig;

@SpringBootApplication
@Import({ApplicationConfig.class, MvcConfig.class, JpaConfig.class, SwaggerConfig.class, SecurityConfig.class})
public class WallScreenApp { // extends SpringBootServletInitializer {
  
  private static Logger log = LoggerFactory.getLogger(WallScreenApp.class);

  //for traditional .war deployment need to extend SpringBootServletInitializer
  //@Override
  //protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //    return application.sources(WallScreenApp.class);
  //}

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(WallScreenApp.class);
    ApplicationContext context = springApplication.run(args);
    log.info("Context : " + context.getId());
  }
  
}
