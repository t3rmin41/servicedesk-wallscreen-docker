package com.domain.servicedesk.wallscreen.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.UserBean;
import com.domain.servicedesk.wallscreen.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserBean getUserByEmailAndPassword(String userName, String password) {
        return userMapper.getUserBeanByEmailAndPassword(userName, password);
    }

    @Override
    public UserBean createUser(UserBean bean) {
      return userMapper.createUser(bean);
    }

    @Override
    public List<UserBean> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public boolean deleteUserById(Long id) {
        return userMapper.deleteUserById(id);
    }
    
    @Override
    public UserBean updateUser(UserBean bean) {
        return userMapper.updateUser(bean);
    }

    @Override
    public UserBean getUserById(Long id) {
        return userMapper.convertUserToBeanByUserId(id);
    }

    @Override
    public UserBean getUserByEmail(String email) {
        return userMapper.getUserBeanByEmail(email);
    }

    @Override
    public Set<String> getRolesByEmail(String email) {
      return userMapper.getRolesByEmail(email);
    }
}
