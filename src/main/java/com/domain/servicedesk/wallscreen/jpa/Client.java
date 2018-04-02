package com.domain.servicedesk.wallscreen.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "TITLE")
  private String title;
  
  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="client")
  private List<Skill> skills = new ArrayList<Skill>();
  
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="SCREEN_ID", nullable=false)
  private Screen screen;
  
  @Column(name = "ASA_SLA", nullable = true)
  private Double asaSla;
  
  @Column(name = "ABN_SLA", nullable = true)
  private Double abnSla;
  
  @Column(name = "AHT_SLA", nullable = true)
  private Double ahtSla;
  
  @Column(name = "TTA_SLA", nullable = true)
  private Double ttaSla;
  
  @Column(name = "AFT_SLA", nullable = true)
  private Double aftSla;
  
  @Column(name = "NO_SLA", nullable = true)
  private Integer noSla;
  
  @Column(name = "NO_PANEL", nullable = true)
  private Integer noPanel;
  
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

  public List<Skill> getSkills() {
    return skills;
  }

  public void setSkills(List<Skill> skills) {
    this.skills = skills;
  }

  public Screen getScreen() {
    return screen;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public Double getAsaSla() {
    return asaSla;
  }

  public void setAsaSla(Double asaSla) {
    this.asaSla = asaSla;
  }

  public Double getAbnSla() {
    return abnSla;
  }

  public void setAbnSla(Double abnSla) {
    this.abnSla = abnSla;
  }

  public Double getAhtSla() {
    return ahtSla;
  }

  public void setAhtSla(Double ahtSla) {
    this.ahtSla = ahtSla;
  }

  public Double getTtaSla() {
    return ttaSla;
  }

  public void setTtaSla(Double ttaSla) {
    this.ttaSla = ttaSla;
  }

  public Double getAftSla() {
    return aftSla;
  }

  public void setAftSla(Double aftSla) {
    this.aftSla = aftSla;
  }

  public Integer getNoSla() {
    return noSla;
  }

  public void setNoSla(Integer noSla) {
    this.noSla = noSla;
  }

  public Integer getNoPanel() {
    return noPanel;
  }

  public void setNoPanel(Integer noPanel) {
    this.noPanel = noPanel;
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
