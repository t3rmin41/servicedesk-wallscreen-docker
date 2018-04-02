package com.domain.servicedesk.wallscreen.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Announcement;

@Repository
public class AnnouncementRepositoryImpl implements AnnouncementRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public List<Announcement> getAllAnnouncements() {
    String q = "SELECT a FROM Announcement a WHERE a.id > 0";
    TypedQuery<Announcement> query = em.createQuery(q, Announcement.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Announcement getAnnouncementById(Long id) {
    String q = "SELECT a FROM Announcement a WHERE a.id = :pid AND a.id > 0";
    TypedQuery<Announcement> query = em.createQuery(q, Announcement.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public List<Announcement> getAnnouncementByScreenId(Long id) {
    String q = "SELECT a FROM Announcement a WHERE a.screen.id = :pid AND a.id > 0";
    TypedQuery<Announcement> query = em.createQuery(q, Announcement.class);
    query.setParameter("pid", id);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Announcement> getAnnouncementByScreenTitle(String title) {
    String q = "SELECT a FROM Announcement a WHERE a.screen.title = :ptitle AND a.id > 0";
    TypedQuery<Announcement> query = em.createQuery(q, Announcement.class);
    query.setParameter("ptitle", title);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public Announcement createAnnouncement(Announcement jpa) {
    jpa.setCreated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public Announcement updateAnnouncement(Announcement jpa) {
    jpa.setUpdated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public void deleteAnnouncementById(Long id) {
    Announcement jpa = getAnnouncementById(id);
    em.remove(jpa);
  }

}
