package com.domain.servicedesk.wallscreen.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCREEN")
public class Screen {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "TITLE")
  private String title;
  
  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="screen")
  private List<Ambasador> ambasadors = new ArrayList<Ambasador>();

  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="screen")
  private List<Client> clients = new ArrayList<Client>();
  
  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="screen")
  private List<Clock> clocks = new ArrayList<Clock>();
  
  @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="screen")
  private List<Announcement> announcements = new ArrayList<Announcement>();

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

  public List<Ambasador> getAmbasadors() {
    return ambasadors;
  }

  public void setAmbasadors(List<Ambasador> ambasadors) {
    this.ambasadors = ambasadors;
  }

  public List<Client> getClients() {
    return clients;
  }

  public void setClients(List<Client> clients) {
    this.clients = clients;
  }

  public List<Clock> getClocks() {
    return clocks;
  }

  public void setClocks(List<Clock> clocks) {
    this.clocks = clocks;
  }

  public List<Announcement> getAnnouncements() {
    return announcements;
  }

  public void setAnnouncements(List<Announcement> announcements) {
    this.announcements = announcements;
  }

}
