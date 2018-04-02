package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Client;

public interface ClientRepository {

  List<Client> getAllClients();
  
  List<Client> getEditableClients();
  
  List<Client> getEditableClientsByScreenId(Long id);
  
  List<Client> getEditableClientsByScreenTitle(String title);
  
  Client getClientById(Long id);

  Client createClient(Client jpa);
  
  Client updateClient(Client jpa);
  
  void deleteClientById(Long id);
}
