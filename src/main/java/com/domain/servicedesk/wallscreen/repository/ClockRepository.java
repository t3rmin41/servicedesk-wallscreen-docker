package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Clock;

public interface ClockRepository {

  Clock getClockById(Long id);
  
  Clock getClockByTitle(String title);
  
  List<Clock> getAllClocks();
  
  List<Clock> getClocksByScreenId(Long id);
  
  List<Clock> getClocksByScreenTitle(String title);
  
  List<Clock> getClocksByOrder(Long order);
  
  Clock createClock(Clock clock);
  
  Clock updateClock(Clock clock);
  
  List<Clock> getClocksByOrderAndScreen(Long order, Long screenId);
  
  void deleteClock(Clock clock);
  
  void deleteClockById(Long id);
}
