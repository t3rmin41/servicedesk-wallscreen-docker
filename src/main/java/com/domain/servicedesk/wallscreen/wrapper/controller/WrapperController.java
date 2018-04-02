package com.domain.servicedesk.wallscreen.wrapper.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.servicedesk.wallscreen.service.AmbasadorService;
import com.domain.servicedesk.wallscreen.service.AnnouncementService;
import com.domain.servicedesk.wallscreen.service.ClientService;
import com.domain.servicedesk.wallscreen.service.ClockService;
import com.domain.servicedesk.wallscreen.wrapper.AmbasadorWrapper;
import com.domain.servicedesk.wallscreen.wrapper.AnnouncementWrapper;
import com.domain.servicedesk.wallscreen.wrapper.ClientWrapper;
import com.domain.servicedesk.wallscreen.wrapper.ClockWrapper;

@Controller
@RequestMapping(value = "/wrapper", produces = APPLICATION_JSON_UTF8_VALUE)
public class WrapperController {

  @Autowired
  private ClockService clocks;
  
  @Autowired
  private AmbasadorService ambasadorService;
  
  @Autowired
  private AnnouncementService announcementService;
  
  @Autowired
  private ClientService clientService;
  
  @RequestMapping(value = "/clocks/all", method = RequestMethod.GET)
  public @ResponseBody ClockWrapper getAllClocksWrapped() {
    ClockWrapper wrapper = new ClockWrapper();
    wrapper.getClocks().addAll(clocks.getAllClocks());
    return wrapper;
  }
  
  @RequestMapping(value = "/clocks/screen/id/{screenId}", method = RequestMethod.GET)
  public @ResponseBody ClockWrapper getClocksByScreenIdWrapped(@PathVariable("screenId") Long id) {
    ClockWrapper wrapper = new ClockWrapper();
    wrapper.getClocks().addAll(clocks.getClocksByScreenId(id));
    return wrapper;
  }
  
  @RequestMapping(value = "/clocks/screen/title/{title}", method = RequestMethod.GET)
  public @ResponseBody ClockWrapper getClocksByScreenTitleWrapped(@PathVariable("title") String title) {
    ClockWrapper wrapper = new ClockWrapper();
    wrapper.getClocks().addAll(clocks.getClocksByScreenTitle(title));
    return wrapper;
  }

  @RequestMapping(value = "/ambasadors/all", method = RequestMethod.GET)
  public @ResponseBody AmbasadorWrapper getAllAmbasadorsWrapped() {
    AmbasadorWrapper wrapper = new AmbasadorWrapper();
    wrapper.getUsers().addAll(ambasadorService.getAllAmbasadors());
    return wrapper;
  }
  
  @RequestMapping(value = "/ambasadors/screen/id/{screenId}", method = RequestMethod.GET)
  public @ResponseBody AmbasadorWrapper getAmbasadorsByScreenIdWrapped(@PathVariable("screenId") Long id) {
    AmbasadorWrapper wrapper = new AmbasadorWrapper();
    wrapper.getUsers().addAll(ambasadorService.getAmbasadorsByScreenId(id));
    return wrapper;
  }

  @RequestMapping(value = "/ambasadors/screen/title/{title}", method = RequestMethod.GET)
  public @ResponseBody AmbasadorWrapper getAmbasadorsByScreenTitleWrapped(@PathVariable("title") String title) {
    AmbasadorWrapper wrapper = new AmbasadorWrapper();
    wrapper.getUsers().addAll(ambasadorService.getAmbasadorsByScreenTitle(title));
    return wrapper;
  }

  @RequestMapping(value = "/announcements/all", method = RequestMethod.GET)
  public @ResponseBody AnnouncementWrapper getAllAnnouncementsWrapped() {
    AnnouncementWrapper wrapper = new AnnouncementWrapper();
    wrapper.getRecords().addAll(announcementService.getAllAnnouncements());
    return wrapper;
  }
  
  @RequestMapping(value = "/announcements/screen/id/{screenId}", method = RequestMethod.GET)
  public @ResponseBody AnnouncementWrapper getAnnouncementsByScreenIdWrapped(@PathVariable("screenId") Long id) {
    AnnouncementWrapper wrapper = new AnnouncementWrapper();
    wrapper.getRecords().addAll(announcementService.getAnnouncementsByScreenId(id));
    return wrapper;
  }
  
  @RequestMapping(value = "/announcements/screen/title/{title}", method = RequestMethod.GET)
  public @ResponseBody AnnouncementWrapper getAnnouncementsByScreenTitleWrapped(@PathVariable("title") String title) {
    AnnouncementWrapper wrapper = new AnnouncementWrapper();
    wrapper.getRecords().addAll(announcementService.getAnnouncementsByScreenTitle(title));
    return wrapper;
  }
  
  @RequestMapping(value = "/clients/all", method = RequestMethod.GET)
  public @ResponseBody ClientWrapper getAllClientsWrapped() {
    ClientWrapper wrapper = new ClientWrapper();
    wrapper.getClient2().addAll(clientService.getAllClients());
    return wrapper;
  }
  
  @RequestMapping(value = "/clients/screen/id/{screenId}", method = RequestMethod.GET)
  public @ResponseBody ClientWrapper getClientsByScreenIdWrapped(@PathVariable("screenId") Long id) {
    ClientWrapper wrapper = new ClientWrapper();
    wrapper.getClient2().addAll(clientService.getEditableClientsByScreenId(id));
    return wrapper;
  }
  
  @RequestMapping(value = "/clients/screen/title/{title}", method = RequestMethod.GET)
  public @ResponseBody ClientWrapper getClientsByScreenTitleWrapped(@PathVariable("title") String title) {
    ClientWrapper wrapper = new ClientWrapper();
    wrapper.getClient2().addAll(clientService.getEditableClientsByScreenTitle(title));
    return wrapper;
  }
  
}
