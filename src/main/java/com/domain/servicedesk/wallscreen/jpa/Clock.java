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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "CLOCK", uniqueConstraints=@UniqueConstraint(columnNames = {"CLOCK_ORDER", "SCREEN_ID"}))
public class Clock {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "TITLE")
  private String title;
  
  @Column(name = "GMT")
  private Double GMT;
  
  @Column(name = "CLOCK_ORDER")
  private Long order;
  
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getGMT() {
    return GMT;
  }

  public void setGMT(Double gMT) {
    GMT = gMT;
  }

  public Long getOrder() {
    return order;
  }

  public void setOrder(Long order) {
    this.order = order;
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
