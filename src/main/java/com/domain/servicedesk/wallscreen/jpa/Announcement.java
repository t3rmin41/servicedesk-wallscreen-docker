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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TEXT")
  private String text;
  
  @Column(name = "TYPE")
  private String type;
  
  @Column(name = "STATUS")
  private String status;
  
  @Column(name = "PRIORITY")
  private String priority;
  
  @Column(name = "TIME")
  private String time;
  
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="SCREEN_ID", nullable=false)
  private Screen screen;
  
  @OneToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="USER_ID", nullable=false)
  private User owner;

  @OneToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="MODIFIED_BY", nullable=false)
  private User modifiedBy;

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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Screen getScreen() {
    return screen;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
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
