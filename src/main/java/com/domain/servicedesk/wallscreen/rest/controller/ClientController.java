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

import com.domain.servicedesk.wallscreen.bean.ClientBean;
import com.domain.servicedesk.wallscreen.service.ClientService;
import com.domain.servicedesk.wallscreen.service.RequestValidator;

@Controller
@RequestMapping(value = "/clients", produces = APPLICATION_JSON_UTF8_VALUE)
public class ClientController {

  private List<String> allowedRoles = new LinkedList<String>(Arrays.asList(new String[]{"ROLE_ADMIN", "ROLE_MANAGER"}));
  
  @Autowired
  private ClientService clientService;

  @Autowired
  private RequestValidator requestValidator;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<ClientBean> getAllClients(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/all");
    return clientService.getAllClients();
  }

  @RequestMapping(value = "/editable", method = RequestMethod.GET)
  public @ResponseBody List<ClientBean> getEditableClients(UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/editable");
    return clientService.getEditableClients();
  }

  @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
  public @ResponseBody ClientBean getClientById(@PathVariable("clientId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/id");
    return clientService.getClientById(id);
  }

  @RequestMapping(value = "/editable/screen/{screenId}", method = RequestMethod.GET)
  public @ResponseBody List<ClientBean> getEditableClientsByScreen(@PathVariable("screenId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/editable/screen/id");
    return clientService.getEditableClientsByScreenId(id);
  }
  
  @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ClientBean createClient(@RequestBody ClientBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/create");
    return clientService.createClient(bean);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ClientBean updateClient(@RequestBody ClientBean bean, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/update");
    return clientService.updateClient(bean);
  }

  @RequestMapping(value = "/delete/{clientId}", method = RequestMethod.DELETE)
  public @ResponseBody void deleteClientById(@PathVariable("clientId") Long id, UsernamePasswordAuthenticationToken token) {
    requestValidator.validateRequestAgainstUserRoles(token, allowedRoles, "/clients/delete/id");
    clientService.deleteScreenById(id);
  }

}
