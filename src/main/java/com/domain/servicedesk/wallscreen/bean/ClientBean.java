package com.domain.servicedesk.wallscreen.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientBean implements Serializable {

  @JsonProperty("ID")
  private String ID;
  
  private String clientName;
  
  private String noSLA;
  
  private String noPanel;
  
  private String abnSla;
  
  private String aftSla;
  
  private String asaSla;
  
  private String ttaSla;
  
  private List<SkillBean> skills = new LinkedList<SkillBean>();
  
  private ScreenBean screen;

  @JsonProperty("ID")
  public String getID() {
    return ID;
  }

  public ClientBean setID(String iD) {
    ID = iD;
    return this;
  }

  public String getClientName() {
    return clientName;
  }

  public ClientBean setClientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

  public String getNoSLA() {
    return noSLA;
  }

  public ClientBean setNoSLA(String noSLA) {
    this.noSLA = noSLA;
    return this;
  }

  public String getNoPanel() {
    return noPanel;
  }

  public ClientBean setNoPanel(String noPanel) {
    this.noPanel = noPanel;
    return this;
  }

  public String getAbnSla() {
    return abnSla;
  }

  public ClientBean setAbnSla(String abnSla) {
    this.abnSla = abnSla;
    return this;
  }

  public String getAftSla() {
    return aftSla;
  }

  public ClientBean setAftSla(String aftSla) {
    this.aftSla = aftSla;
    return this;
  }

  public String getAsaSla() {
    return asaSla;
  }

  public ClientBean setAsaSla(String asaSla) {
    this.asaSla = asaSla;
    return this;
  }

  public String getTtaSla() {
    return ttaSla;
  }

  public ClientBean setTtaSla(String ttaSla) {
    this.ttaSla = ttaSla;
    return this;
  }

  public void setSkills(List<SkillBean> skills) {
    this.skills = skills;
  }

  public List<SkillBean> getSkills() {
    return skills;
  }

  public ScreenBean getScreen() {
    return screen;
  }

  public ClientBean setScreen(ScreenBean screen) {
    this.screen = screen;
    return this;
  }
 
}
