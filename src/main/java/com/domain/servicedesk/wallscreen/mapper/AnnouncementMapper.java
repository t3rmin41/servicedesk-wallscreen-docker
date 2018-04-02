package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;
import com.domain.servicedesk.wallscreen.bean.AnnouncementTypeBean;

public interface AnnouncementMapper {

  AnnouncementBean getAnnouncementById(Long id);

  List<AnnouncementBean> getAllAnnouncements();
  
  List<AnnouncementBean> getAnnouncementsByScreenId(Long id);
  
  List<AnnouncementBean> getAnnouncementsByScreenTitle(String title);

  AnnouncementBean createAnnouncement(AnnouncementBean bean);
  
  AnnouncementBean updateAnnouncement(AnnouncementBean bean);
  
  void deleteAnnouncement(AnnouncementBean bean);
  
  void deleteAnnouncementById(Long id);
  
  AnnouncementTypeBean getTypeBeanByCode(String code);
}
