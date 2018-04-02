package com.domain.servicedesk.wallscreen.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.servicedesk.wallscreen.bean.AmbasadorBean;
import com.domain.servicedesk.wallscreen.service.AmbasadorService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/ambasadors", produces = APPLICATION_JSON_UTF8_VALUE)
public class AmbasadorController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER"}));
  
  @Autowired
  private AmbasadorService ambasadorService;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<AmbasadorBean> getAllAmbasadors(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/all");
    return ambasadorService.getAllAmbasadors();
  }

  @RequestMapping(value = "/{ambasadorId}", method = RequestMethod.GET)
  public @ResponseBody AmbasadorBean getAmbasadorById(@PathVariable("ambasadorId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/id");
    return ambasadorService.getAmbasadorById(id);
  }
  
  @RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET)
  public @ResponseBody List<AmbasadorBean> getAmbasadorsByScreen(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/screen/id");
    return ambasadorService.getAmbasadorsByScreenId(id);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody AmbasadorBean createAmbasador(@RequestBody AmbasadorBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/create");
    return ambasadorService.createAmbasador(bean);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody AmbasadorBean updateAmbasador(@RequestBody AmbasadorBean ambasadorBean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/update");
    return ambasadorService.updateAmbasador(ambasadorBean);
  }

  @RequestMapping(value = "/delete/{ambasadorId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteAmbasadorById(@PathVariable("ambasadorId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/ambasadors/delete");
    ambasadorService.deleteScreenById(id);
  }


}
