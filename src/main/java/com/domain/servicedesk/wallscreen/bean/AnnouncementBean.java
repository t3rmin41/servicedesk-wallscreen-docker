package com.domain.servicedesk.wallscreen.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementBean implements Serializable {

  private String id;
  
  private String text;
  
  private AnnouncementTypeBean type;
  
  private String status;
  
  private UserBean owner;
  
  private UserBean modifiedBy;
  
  private String priority;
  
  private String time;
  
  private ScreenBean screen;

  public String getId() {
    return id;
  }

  public AnnouncementBean setId(String id) {
    this.id = id;
    return this;
  }

  public String getText() {
    return text;
  }

  public AnnouncementBean setText(String text) {
    this.text = text;
    return this;
  }

  public AnnouncementTypeBean getType() {
    return type;
  }

  public AnnouncementBean setType(AnnouncementTypeBean type) {
    this.type = type;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public AnnouncementBean setStatus(String status) {
    this.status = status;
    return this;
  }

  public UserBean getOwner() {
    return owner;
  }

  public AnnouncementBean setOwner(UserBean owner) {
    this.owner = owner;
    return this;
  }

  public UserBean getModifiedBy() {
    return modifiedBy;
  }

  public AnnouncementBean setModifiedBy(UserBean modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  public String getPriority() {
    return priority;
  }

  public AnnouncementBean setPriority(String priority) {
    this.priority = priority;
    return this;
  }

  public String getTime() {
    return time;
  }

  public AnnouncementBean setTime(String time) {
    this.time = time;
    return this;
  }

  public ScreenBean getScreen() {
    return screen;
  }

  public AnnouncementBean setScreen(ScreenBean screen) {
    this.screen = screen;
    return this;
  }
  
}
