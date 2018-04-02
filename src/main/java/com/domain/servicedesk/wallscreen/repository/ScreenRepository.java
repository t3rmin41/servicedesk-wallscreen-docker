package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Screen;

public interface ScreenRepository {

  List<Screen> getAllScreens();
  
  List<Screen> getEditableScreens();
  
  Screen getScreenById(Long id);
  
  Screen getScreenByTitle(String title);
  
  Screen createScreen(Screen screen);
  
  Screen updateScreen(Screen screen);
  
  void deleteScreenById(Long id);

}
