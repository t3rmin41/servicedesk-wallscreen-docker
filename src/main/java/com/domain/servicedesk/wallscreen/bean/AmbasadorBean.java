package com.domain.servicedesk.wallscreen.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmbasadorBean implements Serializable {

  private String id;
  
  private String name;
  
  private String phoneID;
  
  private String avayaID;
  
  @JsonProperty("ENmember")
  private String ENmember;
  
  @JsonProperty("Lowerskill")
  private String Lowerskill;
  
  @JsonProperty("ENSME")
  private String ENSME;
  
  private String hideAccount;
  
  private String watchlist;
  
  private String timeins;
  
  private String aux;
  
  private String auxCode;
  
  private ScreenBean screen;

  public String getId() {
    return id;
  }

  public AmbasadorBean setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public AmbasadorBean setName(String name) {
    this.name = name;
    return this;
  }

  public String getPhoneID() {
    return phoneID;
  }

  public AmbasadorBean setPhoneID(String phoneID) {
    this.phoneID = phoneID;
    return this;
  }

  public String getAvayaID() {
    return avayaID;
  }

  public AmbasadorBean setAvayaID(String avayaID) {
    this.avayaID = avayaID;
    return this;
  }

  @JsonProperty("ENmember")
  public String getENmember() {
    return ENmember;
  }

  public AmbasadorBean setENmember(String eNmember) {
    ENmember = eNmember;
    return this;
  }

  @JsonProperty("Lowerskill")
  public String getLowerskill() {
    return Lowerskill;
  }

  public AmbasadorBean setLowerskill(String lowerskill) {
    Lowerskill = lowerskill;
    return this;
  }

  @JsonProperty("ENSME")
  public String getENSME() {
    return ENSME;
  }

  public AmbasadorBean setENSME(String eNSME) {
    ENSME = eNSME;
    return this;
  }

  public String getHideAccount() {
    return hideAccount;
  }

  public AmbasadorBean setHideAccount(String hideAccount) {
    this.hideAccount = hideAccount;
    return this;
  }

  public String getWatchlist() {
    return watchlist;
  }

  public AmbasadorBean setWatchlist(String watchlist) {
    this.watchlist = watchlist;
    return this;
  }

  public String getTimeins() {
    return timeins;
  }

  public AmbasadorBean setTimeins(String timeins) {
    this.timeins = timeins;
    return this;
  }

  public String getAux() {
    return aux;
  }

  public AmbasadorBean setAux(String aux) {
    this.aux = aux;
    return this;
  }
  
  public String getAuxCode() {
    return auxCode;
  }

  public AmbasadorBean setAuxCode(String auxCode) {
    this.auxCode = auxCode;
    return this;
  }

  public ScreenBean getScreen() {
    return screen;
  }

  public AmbasadorBean setScreen(ScreenBean screen) {
    this.screen = screen;
    return this;
  }

}
