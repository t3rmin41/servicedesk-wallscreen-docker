package com.domain.servicedesk.wallscreen.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.domain.servicedesk.wallscreen.jpa.Skill;

@Repository
public class SkillRepositoryImpl implements SkillRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  @Transactional
  public List<Skill> getAllSkills() {
    String q = "SELECT s FROM Skill s WHERE s.id > 0";
    TypedQuery<Skill> query = em.createQuery(q, Skill.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Skill getSkillById(Long id) {
    String q = "SELECT s FROM Skill s WHERE s.id = :pid";
    TypedQuery<Skill> query = em.createQuery(q, Skill.class);
    query.setParameter("pid", id);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public List<Skill> getSkillsByScreenId(Long id) {
    String q = "SELECT s FROM Skill s WHERE s.id > 0 AND s.client.screen.id = :screenId";
    TypedQuery<Skill> query = em.createQuery(q, Skill.class);
    query.setParameter("screenId", id);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public List<Skill> getSkillsByClientId(Long id) {
    String q = "SELECT s FROM Skill s JOIN FETCH s.client WHERE s.client.id = :pid";
    TypedQuery<Skill> query = em.createQuery(q, Skill.class);
    query.setParameter("pid", id);
    return query.getResultList();
  }
  
  @Override
  @Transactional
  public Skill createSkill(Skill jpa) {
    jpa.setCreated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public Skill updateSkill(Skill jpa) {
    jpa.setUpdated(new Date());
    return em.merge(jpa);
  }

  @Override
  @Transactional
  public void deleteSkillById(Long id) {
    Skill jpa = getSkillById(id);
    em.remove(jpa);
  }

}
