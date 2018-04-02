package com.domain.servicedesk.wallscreen.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.servicedesk.wallscreen.bean.RoleBean;
import com.domain.servicedesk.wallscreen.bean.UserBean;
import com.domain.servicedesk.wallscreen.enums.RoleType;
import com.domain.servicedesk.wallscreen.service.UserService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/users", produces = APPLICATION_JSON_UTF8_VALUE)
public class UserController {


  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN"}));
  
  @Autowired
  private UserService users;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/login/success", method = RequestMethod.POST)
  public @ResponseBody UserBean loginSuccessfull(HttpSession session, Principal principal) {
      UserBean bean = users.getUserByEmail(principal.getName());
      return bean;
  }
  
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  public @ResponseBody UserBean logout(HttpSession session, Principal principal) {
    UserBean bean = users.getUserByEmail(principal.getName());
    return bean;
  }

  @RequestMapping(value = "/roles", method = RequestMethod.GET)
  public @ResponseBody List<RoleBean> getUserRoleMap(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/roles");
    List<RoleBean> roleList = new LinkedList<RoleBean>();
    for (RoleType role : RoleType.values()) {
      roleList.add(new RoleBean().setCode(role.toString()).setTitle(role.getTitle()));
    }
    return roleList;
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<UserBean> getUsers(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/all");
    return users.getAllUsers();
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody UserBean createUser(@RequestBody UserBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/create");
    return users.createUser(bean);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody UserBean getUserById(@PathVariable("id") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/id");
    return users.getUserById(id);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody UserBean updateUser(@RequestBody UserBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/update");
    return users.updateUser(bean);
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public @ResponseBody boolean deleteUser(@PathVariable("id") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/users/delete/id");
    return users.deleteUserById(id);
  }
  
}
