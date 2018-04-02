package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.ScreenBean;

public interface ScreenService {

  List<ScreenBean> getAllScreens();
  
  List<ScreenBean> getEditableScreens();

  ScreenBean getScreenById(Long id);
  
  ScreenBean getScreenByTitle(String title);
  
  ScreenBean createScreen(ScreenBean screen);
  
  ScreenBean updateScreen(ScreenBean screen);
  
  void deleteScreenById(Long id);
}
