package com.domain.servicedesk.wallscreen.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.servicedesk.wallscreen.bean.ClockBean;
import com.domain.servicedesk.wallscreen.service.ClockService;
import com.domain.servicedesk.wallscreen.security.UserNotAllowedException;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/clocks", produces = APPLICATION_JSON_UTF8_VALUE)
public class ClockController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER"}));
  
  @Autowired
  private ClockService clocks;
  
  @Autowired
  private RequestValidator requestValidator;
  

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<ClockBean> getAllClocks(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/all");
    return clocks.getAllClocks();
  }

  @RequestMapping(value = "/{clockId}", method = RequestMethod.GET)
  public @ResponseBody ClockBean getClockById(@PathVariable("clockId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/id");
    return clocks.getClockById(id);
  }

  @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
  public @ResponseBody ClockBean getClockByTitle(@PathVariable("title") String title, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/title");
    return clocks.getClockByTitle(title);
  }

  @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
  public @ResponseBody List<ClockBean> getClocksByOrder(@PathVariable("orderId") Long orderId, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/order/id");
    return clocks.getClocksByOrder(orderId);
  }

  @RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET)
  public @ResponseBody List<ClockBean> getClocksByScreen(@PathVariable("screenId") Long screenId, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/screen/id");
    return clocks.getClocksByScreenId(screenId);
  }
  
  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ClockBean createClock(@RequestBody ClockBean clock, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/create");
    return clocks.createClock(clock);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ClockBean updateClock(@RequestBody ClockBean clock, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/update");
    return clocks.updateClock(clock);
  }

  @RequestMapping(value = "/delete/{clockId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteClockById(@PathVariable("clockId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clocks/delete");
    clocks.deleteClockById(id);
  }
}
