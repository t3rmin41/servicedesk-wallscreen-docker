package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.ClockBean;

public interface ClockService {

  ClockBean getClockById(Long id);
  
  ClockBean getClockByTitle(String title);
  
  List<ClockBean> getAllClocks();
  
  List<ClockBean> getClocksByOrder(Long order);
  
  List<ClockBean> getClocksByScreenId(Long id);
  
  List<ClockBean> getClocksByScreenTitle(String title);
  
  ClockBean createClock(ClockBean clock);
  
  ClockBean updateClock(ClockBean clock);
  
  void deleteClock(ClockBean clock);
  
  void deleteClockById(Long id);
  
}
