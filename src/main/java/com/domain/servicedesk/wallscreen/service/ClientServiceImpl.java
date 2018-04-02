package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.ClientBean;
import com.domain.servicedesk.wallscreen.mapper.ClientMapper;

@Component
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientMapper mapper;
  
  @Override
  public List<ClientBean> getAllClients() {
    return mapper.getAllClients();
  }
  
  @Override
  public List<ClientBean> getEditableClients() {
    return mapper.getEditableClients();
  }

  @Override
  public List<ClientBean> getEditableClientsByScreenId(Long id) {
    return mapper.getEditableClientsByScreenId(id);
  }

  @Override
  public List<ClientBean> getEditableClientsByScreenTitle(String title) {
    return mapper.getEditableClientsByScreenTitle(title);
  }
  
  @Override
  public ClientBean getClientById(Long id) {
    return mapper.getClientById(id);
  }

  @Override
  public ClientBean createClient(ClientBean bean) {
    return mapper.createClient(bean);
  }

  @Override
  public ClientBean updateClient(ClientBean bean) {
    return mapper.updateClient(bean);
  }

  @Override
  public void deleteScreenById(Long id) {
    mapper.deleteClientById(id);
  }

}
