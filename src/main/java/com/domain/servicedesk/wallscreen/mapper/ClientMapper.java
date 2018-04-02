package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.ClientBean;
import com.domain.servicedesk.wallscreen.jpa.Client;

public interface ClientMapper {

  ClientBean getClientById(Long id);

  List<ClientBean> getAllClients();
  
  List<ClientBean> getEditableClients();
  
  List<ClientBean> getEditableClientsByScreenId(Long id);
  
  List<ClientBean> getEditableClientsByScreenTitle(String title);

  ClientBean createClient(ClientBean bean);
  
  ClientBean updateClient(ClientBean bean);
  
  ClientBean convertJpaToBean(Client jpa);

  void deleteClientById(Long id);
}
