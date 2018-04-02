package com.domain.servicedesk.wallscreen.repository;

import java.util.List;
import java.util.Set;

import com.domain.servicedesk.wallscreen.jpa.Role;
import com.domain.servicedesk.wallscreen.jpa.User;

public interface UserRepository {

  User getUserByEmail(String email);
  
  User getUserByEmailAndPassword(String username, String password);
  
  User getUserById(Long id);
  
  User createUser(User jpa);
  
  void createRoles(Set<Role> roles);
  
  void removeRoles(Set<Role> roles);
  
  List<User> getAllUsers();
  
  boolean deleteUser(User jpa);
  
  User updateUser(User jpa, boolean isPasswordChanged);
  
  Set<Role> getUserRolesByNames(User jpa, Set<String> rolesNames);
  
  Set<Role> getUserRolesByEmail(String email);
}
