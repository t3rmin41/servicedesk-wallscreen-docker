package com.domain.servicedesk.wallscreen.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SKILL")
public class Skill {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TITLE")
  private String title;
  
  @Column(name = "SPLIT_SKILL", nullable = true)
  private Long splitSkill;
  
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="CLIENT_ID", nullable=false)
  private Client client;
  
  @Column(name = "VDN", nullable = true)
  private Long vdn;
  
  @Column(name = "IGNORE_STAFFING", nullable = true)
  private Integer ignoreStaffing;
  
  @Column(name = "CREATED", nullable = true)
  private Date created;
  
  @Column(name = "UPDATED", nullable = true)
  private Date updated;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getSplitSkill() {
    return splitSkill;
  }

  public void setSplitSkill(Long splitSkill) {
    this.splitSkill = splitSkill;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Long getVdn() {
    return vdn;
  }

  public void setVdn(Long vdn) {
    this.vdn = vdn;
  }

  public Integer getIgnoreStaffing() {
    return ignoreStaffing;
  }

  public void setIgnoreStaffing(Integer ignoreStaffing) {
    this.ignoreStaffing = ignoreStaffing;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }
  
}
