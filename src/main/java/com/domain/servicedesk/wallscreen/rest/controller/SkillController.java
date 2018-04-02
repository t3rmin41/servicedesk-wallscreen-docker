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

import com.domain.servicedesk.wallscreen.bean.SkillBean;
import com.domain.servicedesk.wallscreen.service.SkillService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/skills", produces = APPLICATION_JSON_UTF8_VALUE)
public class SkillController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER"}));
  
  @Autowired
  private SkillService skillService;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<SkillBean> getAllSkills(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/all");
    return skillService.getAllSkills();
  }

  @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET)
  public @ResponseBody List<SkillBean> getSkillsByClientId(@PathVariable("clientId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/client/id");
    return skillService.getSkillsByClientId(id);
  }

  @RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET)
  public @ResponseBody List<SkillBean> getSkillsByScreen(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/screen/id");
    return skillService.getSkillsByScreenId(id);
  }
  
  @RequestMapping(value = "/{skillId}", method = RequestMethod.GET)
  public @ResponseBody SkillBean getSkillById(@PathVariable("skillId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/id");
    return skillService.getSkillById(id);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody SkillBean createSkill(@RequestBody SkillBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/create");
    return skillService.createSkill(bean);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody SkillBean updateSkill(@RequestBody SkillBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/update");
    return skillService.updateSkill(bean);
  }

  @RequestMapping(value = "/delete/{skillId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteSkillById(@PathVariable("skillId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/skills/delete/id");
    skillService.deleteScreenById(id);
  }
}
