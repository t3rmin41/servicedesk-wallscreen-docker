package com.domain.servicedesk.wallscreen.service;

import java.util.List;
import java.util.Set;

import com.domain.servicedesk.wallscreen.bean.UserBean;

public interface UserService {

  UserBean getUserByEmailAndPassword(String email, String password);
  
  UserBean getUserByEmail(String email);
  
  UserBean getUserById(Long id);
  
  UserBean createUser(UserBean bean);
  
  List<UserBean> getAllUsers();
  
  boolean deleteUserById(Long id);
  
  UserBean updateUser(UserBean bean);

  Set<String> getRolesByEmail(String email);
}