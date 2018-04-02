package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Screen;

@Repository
public class ScreenRepositoryImpl implements ScreenRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public List<Screen> getAllScreens() {
    String q = "SELECT s FROM Screen s ORDER BY s.id ASC";
    TypedQuery<Screen> query = em.createQuery(q, Screen.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<Screen> getEditableScreens() {
    String q = "SELECT s FROM Screen s WHERE s.id > 0 ORDER BY s.id ASC";
    TypedQuery<Screen> query = em.createQuery(q, Screen.class);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public Screen getScreenById(Long id) {
    String q = "SELECT s FROM Screen s WHERE s.id = :pid";
    TypedQuery<Screen> query = em.createQuery(q, Screen.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Screen getScreenByTitle(String title) {
    String q = "SELECT s FROM Screen s WHERE s.title = :ptitle";
    TypedQuery<Screen> query = em.createQuery(q, Screen.class);
    query.setParameter("ptitle", title);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Screen createScreen(Screen screen) {
    return em.merge(screen);
  }

  @Override
  @Transactional
  public Screen updateScreen(Screen screen) {
    return em.merge(screen);
  }

  @Override
  @Transactional
  public void deleteScreenById(Long id) {
    Screen screenToDelete =  getScreenById(id);
    em.remove(screenToDelete);
  }

}
