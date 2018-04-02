package com.domain.servicedesk.wallscreen.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClockBean implements Serializable {

  private String id;

  private String clockName;

  private String clockGMT;

  private String clockOrder;
  
  private ScreenBean screen;

  public String getId() {
    return id;
  }

  public ClockBean setId(String id) {
    this.id = id;
    return this;
  }

  public String getClockName() {
    return clockName;
  }

  public ClockBean setClockName(String clockName) {
    this.clockName = clockName;
    return this;
  }

  public String getClockGMT() {
    return clockGMT;
  }

  public ClockBean setClockGMT(String clockGMT) {
    this.clockGMT = clockGMT;
    return this;
  }

  public String getClockOrder() {
    return clockOrder;
  }

  public ClockBean setClockOrder(String clockOrder) {
    this.clockOrder = clockOrder;
    return this;
  }

  public ScreenBean getScreen() {
    return screen;
  }

  public ClockBean setScreen(ScreenBean screen) {
    this.screen = screen;
    return this;
  }

}
