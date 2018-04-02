package com.domain.servicedesk.wallscreen.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleBean {

  private String code;
  private String title;

  public RoleBean setCode(String code) {
    this.code = code;
    return this;
  }
  
  public RoleBean setTitle(String title) {
    this.title = title;
    return this;
  }
  
  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }

}
