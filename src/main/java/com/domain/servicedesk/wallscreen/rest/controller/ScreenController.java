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

import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.service.ScreenService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/screens", produces = APPLICATION_JSON_UTF8_VALUE)
public class ScreenController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER"}));
  
  @Autowired
  private ScreenService screens;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<ScreenBean> getAllScreens(UsernamePasswordAuthenticationToken token) {
    List<String> allAllowedRoles = new LinkedList<String>();
    allAllowedRoles.addAll(allowedRoles);
    allAllowedRoles.add("ROLE_OPERATOR");
    requestValidator.validateRequestAgainstUserRoles(token, allAllowedRoles, "/screens/all");
    return screens.getAllScreens();
  }

  @RequestMapping(value = "/editable", method = RequestMethod.GET)
  public @ResponseBody List<ScreenBean> getEditableScreens(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/editable");
    return screens.getEditableScreens();
  }

  @RequestMapping(value = "/{screenId}", method = RequestMethod.GET)
  public @ResponseBody ScreenBean getScreenById(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/id");
    return screens.getScreenById(id);
  }

  @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
  public @ResponseBody ScreenBean getScreenByTitle(@PathVariable("title") String title, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/title");
    return screens.getScreenByTitle(title);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ScreenBean createScreen(@RequestBody ScreenBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/create");
    return screens.createScreen(bean);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ScreenBean updateScreen(@RequestBody ScreenBean Screen, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/update");
    return screens.updateScreen(Screen);
  }

  @RequestMapping(value = "/delete/{screenId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteScreenById(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/screens/delete/id");
    screens.deleteScreenById(id);
  }
  
}
