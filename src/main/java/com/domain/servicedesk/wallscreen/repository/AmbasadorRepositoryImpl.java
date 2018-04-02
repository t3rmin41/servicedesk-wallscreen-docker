package com.domain.servicedesk.wallscreen.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Ambasador;

@Repository
public class AmbasadorRepositoryImpl implements AmbasadorRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public List<Ambasador> getAllAmbasadors() {
    String q = "SELECT a FROM Ambasador a WHERE a.id > 0";
    TypedQuery<Ambasador> query = em.createQuery(q, Ambasador.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Ambasador> getAmbasadorsByScreenId(Long id) {
    String q = "SELECT a FROM Ambasador a WHERE a.screen.id = :pid AND a.id > 0";
    TypedQuery<Ambasador> query = em.createQuery(q, Ambasador.class);
    query.setParameter("pid", id);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Ambasador> getAmbasadorsByScreenTitle(String title) {
    String q = "SELECT a FROM Ambasador a WHERE a.screen.title = :ptitle AND a.id > 0";
    TypedQuery<Ambasador> query = em.createQuery(q, Ambasador.class);
    query.setParameter("ptitle", title);
    return query.getResultList();
  }
  
  
  @Override
  @Transactional
  public Ambasador getAmbasadorById(Long id) {
    String q = "SELECT a FROM Ambasador a WHERE a.id = :pid AND a.id > 0";
    TypedQuery<Ambasador> query = em.createQuery(q, Ambasador.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Ambasador createAmbasador(Ambasador jpa) {
    jpa.setCreated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public Ambasador updateAmbasador(Ambasador jpa) {
    jpa.setUpdated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public void deleteAmbasadorById(Long id) {
    Ambasador jpa = getAmbasadorById(id);
    em.remove(jpa);
  }


}
