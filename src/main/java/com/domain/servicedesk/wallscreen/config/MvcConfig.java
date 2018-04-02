package com.domain.servicedesk.wallscreen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter   {
 
    /*
    // configuration for multiple JSP
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/ui-resources/**").addResourceLocations("/ui-resources/");
    }
 
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
      InternalResourceViewResolver bean = new InternalResourceViewResolver();
      bean.setViewClass(JstlView.class);
      bean.setPrefix("/jsp/views/");
      bean.setSuffix(".jsp");
      return bean;
    }
    /**/

  //Configuration for single-page application
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/resources/**", "/app/**").addResourceLocations("/static/", "/app/");
  }

  @Bean
  public InternalResourceViewResolver jspViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setViewClass(JstlView.class);
      viewResolver.setPrefix("/app/");
      viewResolver.setSuffix(".html");
      //viewResolver.setSuffix(".jsp");
      return viewResolver;
  }
  /**/
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
  
}