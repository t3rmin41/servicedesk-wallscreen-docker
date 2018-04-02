package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;
import com.domain.servicedesk.wallscreen.mapper.AnnouncementMapper;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

  @Autowired
  private AnnouncementMapper mapper;
  
  @Override
  public List<AnnouncementBean> getAllAnnouncements() {
    return mapper.getAllAnnouncements();
  }
  
  @Override
  public List<AnnouncementBean> getAnnouncementsByScreenId(Long id) {
    return mapper.getAnnouncementsByScreenId(id);
  }

  @Override
  public List<AnnouncementBean> getAnnouncementsByScreenTitle(String title) {
    return mapper.getAnnouncementsByScreenTitle(title);
  }

  @Override
  public AnnouncementBean getAnnouncementById(Long id) {
    return mapper.getAnnouncementById(id);
  }

  @Override
  public AnnouncementBean createAnnouncement(AnnouncementBean bean) {
    return mapper.createAnnouncement(bean);
  }

  @Override
  public AnnouncementBean updateAnnouncement(AnnouncementBean bean) {
    return mapper.updateAnnouncement(bean);
  }

  @Override
  public void deleteScreenById(Long id) {
    mapper.deleteAnnouncementById(id);
  }

}
