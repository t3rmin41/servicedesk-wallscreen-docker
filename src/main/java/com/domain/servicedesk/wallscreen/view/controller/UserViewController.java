package com.domain.servicedesk.wallscreen.view.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserViewController {

  @RequestMapping(value = "/usersPage", method = RequestMethod.GET)
  public String viewUserspage(Principal principal) {
      return "users";
  }
  
  @RequestMapping(value = "/userview/{userId}", method = RequestMethod.GET)
  public String viewUserEditPage(@PathVariable("userId") Long userId) {
      return "user";
  }

}
