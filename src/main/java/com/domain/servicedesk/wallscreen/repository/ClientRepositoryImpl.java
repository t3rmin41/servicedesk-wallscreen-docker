package com.domain.servicedesk.wallscreen.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Client;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public List<Client> getAllClients() {
    String q = "SELECT c FROM Client c ORDER BY c.id ASC";
    TypedQuery<Client> query = em.createQuery(q, Client.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Client> getEditableClients() {
    String q = "SELECT c FROM Client c WHERE c.id > 0 ORDER BY c.id ASC";
    TypedQuery<Client> query = em.createQuery(q, Client.class);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Client> getEditableClientsByScreenId(Long id) {
    String q = "SELECT c FROM Client c WHERE c.id > 0 AND c.screen.id = :pid ORDER BY c.id ASC";
    TypedQuery<Client> query = em.createQuery(q, Client.class);
    query.setParameter("pid", id);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Client> getEditableClientsByScreenTitle(String title) {
    String q = "SELECT c FROM Client c WHERE c.id > 0 AND c.screen.title = :ptitle ORDER BY c.id ASC";
    TypedQuery<Client> query = em.createQuery(q, Client.class);
    query.setParameter("ptitle", title);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public Client getClientById(Long id) {
    String q = "SELECT c FROM Client c WHERE c.id = :pid";
    TypedQuery<Client> query = em.createQuery(q, Client.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Client createClient(Client jpa) {
    jpa.setCreated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public Client updateClient(Client jpa) {
    jpa.setUpdated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public void deleteClientById(Long id) {
    Client jpa = getClientById(id);
    em.remove(jpa);
  }

}
