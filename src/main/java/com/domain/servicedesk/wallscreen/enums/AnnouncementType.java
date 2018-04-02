package com.domain.servicedesk.wallscreen.enums;

public enum AnnouncementType {

  MAJOR_INCIDENT("Major incident"),
  ANNOUNCEMENT("Announcement"),
  PLANNED_CHANGE("Planned change");
  
  private String title;
  
  private AnnouncementType(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return title;
  }
  
  public static AnnouncementType getTypeByTitle(String title) {
    for (AnnouncementType type : AnnouncementType.values()) {
      if (type.getTitle().equals(title)) {
        return type;
      }
    }
    return null;
  }
  
  public static String getTitleByType(AnnouncementType type) {
    for (AnnouncementType currentType : AnnouncementType.values()) {
      if (currentType.equals(type)) {
        return currentType.getTitle();
      }
    }
    return null;
  }
  
  public static AnnouncementType getTypeByCode(String code) {
    for (AnnouncementType type : AnnouncementType.values()) {
      if (type.toString().equals(code)) {
        return type;
      }
    }
    return null;
  }
  
}
