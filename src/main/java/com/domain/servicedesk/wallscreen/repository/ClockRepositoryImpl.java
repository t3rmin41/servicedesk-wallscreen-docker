package com.domain.servicedesk.wallscreen.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Clock;

@Repository
public class ClockRepositoryImpl implements ClockRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public Clock getClockById(Long id) {
    String q = "SELECT c FROM Clock c WHERE c.id = :pid";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Clock getClockByTitle(String title) {
    String q = "SELECT c FROM Clock c WHERE c.title = :ptitle";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("ptitle", title);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public List<Clock> getAllClocks() {
    String q = "SELECT c FROM Clock c";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Clock> getClocksByOrder(Long order) {
    String q = "SELECT c FROM Clock c WHERE c.order = :porder";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("porder", order);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Clock> getClocksByScreenId(Long id) {
    String q = "SELECT c FROM Clock c WHERE c.screen.id = :pid AND c.id > 0 ORDER BY c.id ASC";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("pid", id);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Clock> getClocksByScreenTitle(String title) {
    String q = "SELECT c FROM Clock c WHERE c.screen.title = :ptitle AND c.id > 0 ORDER BY c.id ASC";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("ptitle", title);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Clock> getClocksByOrderAndScreen(Long order, Long screenId) {
    String q = "SELECT c FROM Clock c WHERE c.screen.id = :screenId AND c.order = :order ORDER BY c.id ASC";
    TypedQuery<Clock> query = em.createQuery(q, Clock.class);
    query.setParameter("order", order);
    query.setParameter("screenId", screenId);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public Clock createClock(Clock clock) {
    clock.setCreated(new Date());
    return em.merge(clock);
  }

  @Override
  @Transactional
  public Clock updateClock(Clock clock) {
    clock.setUpdated(new Date());
    return em.merge(clock);
  }

  @Override
  @Transactional
  public void deleteClock(Clock clock) {
    em.remove(clock);
  }

  @Override
  @Transactional
  public void deleteClockById(Long id) {
    Clock jpaToDelete = getClockById(id);
    deleteClock(jpaToDelete);
  }


}
