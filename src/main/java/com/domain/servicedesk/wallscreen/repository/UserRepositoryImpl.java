package com.domain.servicedesk.wallscreen.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.jpa.Role;
import com.domain.servicedesk.wallscreen.jpa.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private static Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
  
  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  @Transactional
  public User getUserByEmailAndPassword(String email, String password) {
      String q = "SELECT u FROM User u WHERE u.email = :pemail AND u.password = :ppassword";
      TypedQuery<User> query = em.createQuery(q, User.class);
      query.setParameter("pemail", email);
      query.setParameter("ppassword", password);
      List<User> users = query.getResultList();
      if (1 == users.size()) {
          return users.get(0);
      } else {
          return null;
      }
  }

  @Override
  @Transactional
  public User createUser(User jpa) {
      jpa.setPassword(passwordEncoder.encode(jpa.getPassword()));
      return em.merge(jpa);
  }

  @Override
  @Transactional
  public List<User> getAllUsers() {
      String q = "SELECT u FROM User u WHERE u.enabled = true";
      TypedQuery<User> query = em.createQuery(q, User.class);
      return query.getResultList();
  }

  @Override
  @Transactional
  public boolean deleteUser(User jpa) {
      boolean result = false;
      try {
          em.remove(jpa);
          result = true;
      } catch (IllegalArgumentException e) {
          log.error(e.getMessage());
      } catch (TransactionRequiredException e) {
          log.error(e.getMessage());
      }
      return result;
  }

  @Override
  @Transactional
  public User updateUser(User jpa, boolean isPasswordChanged) {
    if (isPasswordChanged) {
      jpa.setPassword(passwordEncoder.encode(jpa.getPassword()));
    } else {
      jpa.setPassword(jpa.getPassword());
    }
    User updated = em.merge(jpa);
    return getUserById(updated.getId());
  }

  @Override
  @Transactional
  public void createRoles(Set<Role> roles) {
      for (Role role : roles) {
          em.merge(role);
      }
  }
  
  @Override
  @Transactional
  public void removeRoles(Set<Role> roles) {
      for (Role role : roles) {
          String q = "DELETE FROM Role r WHERE r.role = :role AND r.user = :user AND r.active = 1";
          Query query = em.createQuery(q);
          query.setParameter("role", role.getRole());
          query.setParameter("user", role.getUser());
          query.executeUpdate();
      }
  }

  @Override
  @Transactional
  public User getUserById(Long id) {
      String q = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :pid";
      TypedQuery<User> query = em.createQuery(q, User.class);
      query.setParameter("pid", id);
      return query.getSingleResult();
  }

  @Override
  @Transactional
  public User getUserByEmail(String email) {
      String q = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :pemail";
      TypedQuery<User> query = em.createQuery(q, User.class);
      query.setParameter("pemail", email);
      return query.getSingleResult();
  }

  @Override
  @Transactional
  public Set<Role> getUserRolesByNames(User user, Set<String> roleNames) {
      String q = "SELECT r FROM Role r WHERE r.role IN :roles AND r.user = :user AND r.active = 1";
      TypedQuery<Role> query = em.createQuery(q, Role.class);
      query.setParameter("roles", roleNames);
      query.setParameter("user", user);
      List<Role> resultList = query.getResultList();
      Set<Role> roles = new HashSet<Role>();
      roles.addAll(resultList);
      return roles;
  }

  @Override
  @Transactional
  public Set<Role> getUserRolesByEmail(String email) {
    String q = "SELECT r FROM Role r WHERE r.user.email = :pemail AND r.active = 1";
    TypedQuery<Role> query = em.createQuery(q, Role.class);
    query.setParameter("pemail", email);
    List<Role> resultList = query.getResultList();
    Set<Role> roles = new HashSet<Role>();
    roles.addAll(resultList);
    return roles;
  }
}
