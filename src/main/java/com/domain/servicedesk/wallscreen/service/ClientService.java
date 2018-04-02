package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.ClientBean;

public interface ClientService {

  List<ClientBean> getAllClients();
  
  List<ClientBean> getEditableClients();
  
  List<ClientBean> getEditableClientsByScreenId(Long id);
  
  List<ClientBean> getEditableClientsByScreenTitle(String title);

  ClientBean getClientById(Long id);
  
  ClientBean createClient(ClientBean bean);
  
  ClientBean updateClient(ClientBean bean);
  
  void deleteScreenById(Long id);
}
