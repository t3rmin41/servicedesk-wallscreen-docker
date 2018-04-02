package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.mapper.ScreenMapper;

@Service
public class ScreenServiceImpl implements ScreenService {

  @Autowired
  private ScreenMapper screenMapper;
  
  @Override
  public List<ScreenBean> getAllScreens() {
    return screenMapper.getAllScreens();
  }

  @Override
  public List<ScreenBean> getEditableScreens() {
    return screenMapper.getEditableScreens();
  }
  
  @Override
  public ScreenBean getScreenById(Long id) {
    return screenMapper.getScreenById(id);
  }

  @Override
  public ScreenBean getScreenByTitle(String title) {
    return screenMapper.getScreenByTitle(title);
  }

  @Override
  public ScreenBean createScreen(ScreenBean screen) {
    return screenMapper.createScreen(screen);
  }

  @Override
  public ScreenBean updateScreen(ScreenBean screen) {
    return screenMapper.updateScreen(screen);
  }

  @Override
  public void deleteScreenById(Long id) {
    screenMapper.deleteScreenById(id);
  }

}
