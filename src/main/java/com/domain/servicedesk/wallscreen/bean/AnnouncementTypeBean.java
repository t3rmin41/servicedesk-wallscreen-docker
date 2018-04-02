package com.domain.servicedesk.wallscreen.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementTypeBean {

  private String code;
  private String title;
  
  public String getCode() {
    return code;
  }
  public AnnouncementTypeBean setCode(String code) {
    this.code = code;
    return this;
  }
  public String getTitle() {
    return title;
  }
  public AnnouncementTypeBean setTitle(String title) {
    this.title = title;
    return this;
  }
}
