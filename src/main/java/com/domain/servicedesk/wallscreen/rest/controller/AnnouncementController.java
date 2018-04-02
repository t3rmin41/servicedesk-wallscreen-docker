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

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;
import com.domain.servicedesk.wallscreen.bean.AnnouncementTypeBean;
import com.domain.servicedesk.wallscreen.enums.AnnouncementType;
import com.domain.servicedesk.wallscreen.service.AnnouncementService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/announcements", produces = APPLICATION_JSON_UTF8_VALUE)
public class AnnouncementController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_OPERATOR"}));

  @Autowired
  private AnnouncementService announcementService;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<AnnouncementBean> getAllAnnouncements(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/all");
    return announcementService.getAllAnnouncements();
  }

  @RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET)
  public @ResponseBody List<AnnouncementBean> getAnnouncementsByScreen(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/screen/id");
    return announcementService.getAnnouncementsByScreenId(id);
  }
  
  @RequestMapping(value = "/{announcementId}", method = RequestMethod.GET)
  public @ResponseBody AnnouncementBean getAnnouncementById(@PathVariable("announcementId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/id");
    return announcementService.getAnnouncementById(id);
  }
  
  @RequestMapping(value = "/types", method = RequestMethod.GET)
  public @ResponseBody List<AnnouncementTypeBean> getAnnouncementTypes(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/types");
    List<AnnouncementTypeBean> types = new LinkedList<AnnouncementTypeBean>();
    for (AnnouncementType type : AnnouncementType.values()) {
      types.add(new AnnouncementTypeBean().setCode(type.toString()).setTitle(type.getTitle()));
    }
    return types;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody AnnouncementBean createAnnouncement(@RequestBody AnnouncementBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/create");
    return announcementService.createAnnouncement(bean);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody AnnouncementBean updateAnnouncement(@RequestBody AnnouncementBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/update");
    return announcementService.updateAnnouncement(bean);
  }

  @RequestMapping(value = "/delete/{announcementId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteAnnouncementById(@PathVariable("announcementId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/announcements/delete/id");
    announcementService.deleteScreenById(id);
  }

}
