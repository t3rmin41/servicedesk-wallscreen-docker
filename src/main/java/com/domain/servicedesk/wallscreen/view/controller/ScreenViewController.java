package com.domain.servicedesk.wallscreen.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScreenViewController {

  @RequestMapping(value = "/screensPage", method = RequestMethod.GET)
  public String viewScreensPage() {
      return "screens";
  }
  
  @RequestMapping(value = "/screenview/{screenid}", method = RequestMethod.GET)
  public String viewScreenEditPage(@PathVariable("screenid") Long screenId) {
      return "screen";
  }
}
