package com.domain.servicedesk.wallscreen.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenBean implements Serializable {

  private String id;
  
  private String title;

  public String getId() {
    return id;
  }

  public ScreenBean setId(String id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public ScreenBean setTitle(String title) {
    this.title = title;
    return this;
  }
  
}
