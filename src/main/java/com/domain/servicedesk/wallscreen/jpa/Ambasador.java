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
@Table(name = "AMBASADOR")
public class Ambasador {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "NAME")
  private String name;
  
  @Column(name = "AVAYA_ID", nullable = true)
  private Integer avayaId;
  
  @Column(name = "PHONE_ID", nullable = true)
  private Integer phoneId;
  
  @Column(name = "EN_MEMBER", nullable = true)
  private Integer enMember;
  
  @Column(name = "LOWER_SKILL", nullable = true)
  private Integer lowerSkill;
  
  @Column(name = "WATCH_LIST", nullable = true)
  private Integer watchlist;
  
  @Column(name = "AUX", nullable = true)
  private String aux;
  
  @Column(name = "TIMEINS", nullable = true)
  private String timeins;
  
  @Column(name = "AUX_CODE", nullable = true)
  private String auxCode;
  
  @Column(name = "HIDE_ACCOUNT", nullable = true)
  private Integer hideAccount;
  
  @Column(name = "EN_SME", nullable = true)
  private Integer enSme;
  
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="SCREEN_ID", nullable=false)
  private Screen screen;
  
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAvayaId() {
    return avayaId;
  }

  public void setAvayaId(Integer avayaId) {
    this.avayaId = avayaId;
  }

  public Integer getPhoneId() {
    return phoneId;
  }

  public void setPhoneId(Integer phoneId) {
    this.phoneId = phoneId;
  }

  public Integer getEnMember() {
    return enMember;
  }

  public void setEnMember(Integer enMember) {
    this.enMember = enMember;
  }

  public Integer getLowerSkill() {
    return lowerSkill;
  }

  public void setLowerSkill(Integer lowerSkill) {
    this.lowerSkill = lowerSkill;
  }

  public Integer getWatchlist() {
    return watchlist;
  }

  public void setWatchlist(Integer watchlist) {
    this.watchlist = watchlist;
  }

  public String getAux() {
    return aux;
  }

  public void setAux(String aux) {
    this.aux = aux;
  }

  public String getTimeins() {
    return timeins;
  }

  public void setTimeins(String timeins) {
    this.timeins = timeins;
  }

  public String getAuxCode() {
    return auxCode;
  }

  public void setAuxCode(String auxCode) {
    this.auxCode = auxCode;
  }

  public Integer getHideAccount() {
    return hideAccount;
  }

  public void setHideAccount(Integer hideAccount) {
    this.hideAccount = hideAccount;
  }

  public Integer getEnSme() {
    return enSme;
  }

  public void setEnSme(Integer enSme) {
    this.enSme = enSme;
  }

  public Screen getScreen() {
    return screen;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
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
