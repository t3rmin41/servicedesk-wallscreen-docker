package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.jpa.Screen;

public interface ScreenMapper {

  List<ScreenBean> getAllScreens();
  
  List<ScreenBean> getEditableScreens();
  
  ScreenBean getScreenById(Long id);

  ScreenBean getScreenByTitle(String title);
  
  ScreenBean createScreen(ScreenBean screen);
  
  ScreenBean updateScreen(ScreenBean screen);
  
  void deleteScreenById(Long id);
  
  public ScreenBean convertJpaToBean(Screen jpa);
}
