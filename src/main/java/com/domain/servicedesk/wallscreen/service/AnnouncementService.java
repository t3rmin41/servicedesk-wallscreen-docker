package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;

public interface AnnouncementService {

  List<AnnouncementBean> getAllAnnouncements();
  
  List<AnnouncementBean> getAnnouncementsByScreenId(Long id);
  
  List<AnnouncementBean> getAnnouncementsByScreenTitle(String title);

  AnnouncementBean getAnnouncementById(Long id);
  
  AnnouncementBean createAnnouncement(AnnouncementBean bean);
  
  AnnouncementBean updateAnnouncement(AnnouncementBean bean);
  
  void deleteScreenById(Long id);
}
