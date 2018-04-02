package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;
import java.util.Set;

import com.domain.servicedesk.wallscreen.bean.RoleBean;
import com.domain.servicedesk.wallscreen.bean.UserBean;
import com.domain.servicedesk.wallscreen.jpa.Role;

public interface UserMapper {

  UserBean getUserBeanByEmail(String email);
  
  UserBean getUserBeanByEmailAndPassword(String email, String password);

  UserBean convertUserToBeanByUserId(Long id);
  
  UserBean createUser(UserBean bean);
  
  List<UserBean> getAllUsers();
  
  boolean deleteUserById(Long id);
  
  UserBean updateUser(UserBean bean);

  List<RoleBean> convertUserRolesToRoleBeans(Set<Role> roles);

  void addRoles(Long userId, Set<String> roles);

  void removeRoles(Long userId, Set<String> roles);
  
  Set<String> getRolesByEmail(String email);
  
  Set<String> getNewRolesDifference(Set<String> oldRoles, Set<String> newRoles);
  
  Set<String> getOldRolesDifference(Set<String> oldRoles, Set<String> newRoles);
}
