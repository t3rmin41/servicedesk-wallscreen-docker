package com.domain.servicedesk.wallscreen.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClockViewController {

  @RequestMapping(value = "/clocksPage", method = RequestMethod.GET)
  public String viewClocksPage() {
      return "clocks";
  }
  
  @RequestMapping(value = "/clockview/{clockid}", method = RequestMethod.GET)
  public String viewClockEditPage(@PathVariable("clockid") Long clockId) {
      return "clock";
  }

}
