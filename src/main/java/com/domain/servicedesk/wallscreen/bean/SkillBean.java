package com.domain.servicedesk.wallscreen.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillBean implements Serializable {

  private String id;
          
  private String clientID;
  
  private String skillName;
  
  private String splitSkill;
  
  private String vdn;
  
  private String ignoreStaffing;
  
  private String clientName;

  public String getId() {
    return id;
  }

  public SkillBean setId(String id) {
    this.id = id;
    return this;
  }

  public String getClientID() {
    return clientID;
  }

  public SkillBean setClientID(String clientID) {
    this.clientID = clientID;
    return this;
  }

  public String getSkillName() {
    return skillName;
  }

  public SkillBean setSkillName(String skillName) {
    this.skillName = skillName;
    return this;
  }

  public String getSplitSkill() {
    return splitSkill;
  }

  public SkillBean setSplitSkill(String splitSkill) {
    this.splitSkill = splitSkill;
    return this;
  }

  public String getVdn() {
    return vdn;
  }

  public SkillBean setVdn(String vdn) {
    this.vdn = vdn;
    return this;
  }

  public String getIgnoreStaffing() {
    return ignoreStaffing;
  }

  public SkillBean setIgnoreStaffing(String ignoreStaffing) {
    this.ignoreStaffing = ignoreStaffing;
    return this;
  }

  public String getClientName() {
    return clientName;
  }

  public SkillBean setClientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

}
