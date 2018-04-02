package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.ClockBean;
import com.domain.servicedesk.wallscreen.mapper.ClockMapper;

@Service
public class ClockServiceImpl implements ClockService {

  @Autowired
  private ClockMapper mapper;
  
  @Override
  public ClockBean getClockById(Long id) {
    return mapper.getClockById(id);
  }

  @Override
  public ClockBean getClockByTitle(String title) {
    return mapper.getClockByTitle(title);
  }

  @Override
  public List<ClockBean> getAllClocks() {
    return mapper.getAllClocks();
  }

  @Override
  public List<ClockBean> getClocksByScreenId(Long id) {
    return mapper.getClocksByScreenId(id);
  }

  @Override
  public List<ClockBean> getClocksByScreenTitle(String title) {
    return mapper.getClocksByScreenTitle(title);
  }
  
  @Override
  public List<ClockBean> getClocksByOrder(Long order) {
    return mapper.getClocksByOrder(order);
  }

  @Override
  public ClockBean createClock(ClockBean clock) {
    return mapper.createClock(clock);
  }

  @Override
  public ClockBean updateClock(ClockBean clock) {
    return mapper.updateClock(clock);
  }

  @Override
  public void deleteClock(ClockBean clock) {
    mapper.deleteClock(clock);
  }

  @Override
  public void deleteClockById(Long id) {
    mapper.deleteClockById(id);
  }

}
