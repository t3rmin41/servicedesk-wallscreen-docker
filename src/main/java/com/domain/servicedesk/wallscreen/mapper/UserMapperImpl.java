package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.RoleBean;
import com.domain.servicedesk.wallscreen.bean.UserBean;
import com.domain.servicedesk.wallscreen.enums.RoleType;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Role;
import com.domain.servicedesk.wallscreen.jpa.User;
import com.domain.servicedesk.wallscreen.repository.UserRepository;

@Component
public class UserMapperImpl implements UserMapper, BeanValidator {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public UserBean getUserBeanByEmailAndPassword(String username, String password) {
      User jpa = userRepo.getUserByEmailAndPassword(username, password);
      if (null != jpa) {
          return convertJpaToBean(jpa);
      } else {
          return null;
      }
  }

  @Override
  public UserBean createUser(UserBean bean) {
      User jpa = new User();
      validateBean(bean);
      setSimpleFieldsFromBean(jpa, bean);
      jpa.setEnabled(true);
      User created = userRepo.createUser(jpa);
      Set<String> roleNames = new HashSet<String>();
      bean.getRoles().stream().forEach(r -> {
        roleNames.add(r.getCode());
      });
      Set<Role> roles = convertUserNewRoleStringToRoles(created, roleNames);
      userRepo.createRoles(roles);
      return convertUserToBeanByUserId(created.getId());
  }

  @Override
  public List<UserBean> getAllUsers() {
      List<UserBean> beans = new ArrayList<UserBean>();
      for (User jpa : userRepo.getAllUsers()) {
          beans.add(convertJpaToBean(jpa));
      }
      return beans;
  }

  @Override
  public boolean deleteUserById(Long id) {
      User jpa = userRepo.getUserById(id);
      return userRepo.deleteUser(jpa);
  }

  @Override
  public UserBean updateUser(UserBean bean) {
      User jpa = userRepo.getUserById(new Long(bean.getId()));
      validateBean(bean);
      setSimpleFieldsFromBean(jpa, bean);
      UserBean oldBean = convertUserToBeanByUserId(new Long(bean.getId()));
      if (checkValidRoles(bean.getRolesAsStrings())) {
        Set<String> oldRoles = new HashSet<String>(oldBean.getRolesAsStrings());
        Set<String> newRoles = new HashSet<String>(bean.getRolesAsStrings());
        removeRoles(new Long(bean.getId()), getOldRolesDifference(oldRoles, newRoles));
        addRoles(new Long(bean.getId()), getNewRolesDifference(oldRoles, newRoles));
      }
      return convertJpaToBean(userRepo.updateUser(jpa, checkIfPasswordChanged(bean)));
  }

  @Override
  public List<RoleBean> convertUserRolesToRoleBeans(Set<Role> roles) {
      List<RoleBean> roleBeans = new LinkedList<RoleBean>();
      for (Role role : roles) {
        roleBeans.add(new RoleBean().setCode(role.getRole()).setTitle(RoleType.getRoleTitleByCode(role.getRole())));
      }
      return roleBeans;
  }

  @Override
  public UserBean convertUserToBeanByUserId(Long id) {
      User jpa = userRepo.getUserById(id);
      if (null != jpa) {
          return convertJpaToBean(jpa);
      } else {
          return null;
      }
  }
  
  @Override
  public UserBean getUserBeanByEmail(String email) {
      return convertJpaToBean(userRepo.getUserByEmail(email));
  }
 
  @Override
  public void addRoles(Long userId, Set<String> roles) {
    User jpa = userRepo.getUserById(userId);
    userRepo.createRoles(convertUserNewRoleStringToRoles(jpa, roles));
  }

  @Override
  public void removeRoles(Long userId, Set<String> roles) {
    User jpa = userRepo.getUserById(userId);
    userRepo.removeRoles(userRepo.getUserRolesByNames(jpa, roles));
  }
  
  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    UserBean userBean = (UserBean) bean;
    if (userBean.getRoles().size() < 1) {
      errors.add(new ErrorField("roles", "User must have at least 1 valid role"));
    }
    if (userBean.getRoles().size() > 0 && !checkValidRoles(userBean.getRolesAsStrings())) {
      errors.add(new ErrorField("roles", "Wrong one or more roles"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong user fields", errors);
    }
    return errors;
  }
  
  private Set<Role> convertUserNewRoleStringToRoles(User jpa, Set<String> roleNames) {
      Set<Role> roles = new HashSet<Role>();
      for (String rolename : roleNames) {
          Role jpaRole = new Role();
          jpaRole.setUser(jpa);
          jpaRole.setRole(rolename);
          roles.add(jpaRole);
      }
      return roles;
  }
  
  private UserBean convertJpaToBean(User jpa) {
      return new UserBean()//.setPassword(jpa.getPassword())
              .setFirstName(jpa.getFirstName())
              .setLastName(jpa.getLastName())
              .setEmail(jpa.getEmail())
              .setId(null != jpa.getId() ? jpa.getId().toString() : null)
              .setRoles(convertUserRolesToRoleBeans(jpa.getRoles())).setEnabled(jpa.getEnabled());
  }

  private Set<String> convertExistingUserRolesToStrings(Set<Role> roles) {
    Set<String> roleNames = new HashSet<String>();
    for (Role role : roles) {
      roleNames.add(role.getRole());
    }
    return roleNames;
  }

  @Override
  public Set<String> getRolesByEmail(String email) {
    Set<String> roles = new HashSet<String>();
    userRepo.getUserRolesByEmail(email).stream().forEach(r -> {
      roles.add(r.getRole());
    });
    return roles;
  }

  @Override
  public Set<String> getNewRolesDifference(Set<String> oldRoles, Set<String> newRoles) {
    Set<String> commonRoles = new HashSet<String>();
    commonRoles.addAll(newRoles);
    Set<String> newRoleSetDiff = new HashSet<String>();
    newRoleSetDiff.addAll(newRoles);
    commonRoles.retainAll(oldRoles);
    newRoleSetDiff.removeAll(commonRoles);
    return newRoleSetDiff;
  }

  @Override
  public Set<String> getOldRolesDifference(Set<String> oldRoles, Set<String> newRoles) {
    Set<String> commonRoles = new HashSet<String>();
    commonRoles.addAll(oldRoles);
    Set<String> oldRoleSetDiff = new HashSet<String>();
    oldRoleSetDiff.addAll(oldRoles);
    commonRoles.retainAll(newRoles);
    oldRoleSetDiff.removeAll(commonRoles);
    return oldRoleSetDiff;
  }
  
  private User setSimpleFieldsFromBean(User jpa, UserBean bean) {
    jpa.setEmail(bean.getEmail());
    if (null != bean.getPassword() && !bean.getPassword().isEmpty() && !passwordEncoder.encode(bean.getPassword()).equals(jpa.getPassword())) {
      jpa.setPassword(bean.getPassword());
    }
    jpa.setFirstName(bean.getFirstName());
    jpa.setLastName(bean.getLastName());
    return jpa;
  }
  
  private boolean checkValidRoles(List<String> roles) {
    if (null == roles || 0 == roles.size()) {
      return false;
    }
    for (String role : roles) {
      if (!Arrays.asList(RoleType.values()).contains(RoleType.getRoleTypeByCode(role))) {
        return false;
      }
    }
    return true;
  }
  
  private boolean checkIfPasswordChanged(UserBean bean) {
    return null != bean.getPassword();
  }
  
}