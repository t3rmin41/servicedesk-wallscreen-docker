package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Announcement;

public interface AnnouncementRepository {

  List<Announcement> getAllAnnouncements();
  
  List<Announcement> getAnnouncementByScreenId(Long id);
  
  List<Announcement> getAnnouncementByScreenTitle(String title);
  
  Announcement getAnnouncementById(Long id);

  Announcement createAnnouncement(Announcement jpa);
  
  Announcement updateAnnouncement(Announcement jpa);
  
  void deleteAnnouncementById(Long id);
}
