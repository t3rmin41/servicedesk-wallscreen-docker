package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.AmbasadorBean;
import com.domain.servicedesk.wallscreen.mapper.AmbasadorMapper;

@Service
public class AmbasadorServiceImpl implements AmbasadorService {

  @Autowired
  private AmbasadorMapper mapper;
  
  @Override
  public List<AmbasadorBean> getAllAmbasadors() {
    return mapper.getAllAmbasadorBeans();
  }

  @Override
  public List<AmbasadorBean> getAmbasadorsByScreenId(Long id) {
    return mapper.getAmbasadorsByScreenId(id);
  }

  @Override
  public List<AmbasadorBean> getAmbasadorsByScreenTitle(String title) {
    return mapper.getAmbasadorsByScreenTitle(title);
  }
  
  @Override
  public AmbasadorBean getAmbasadorById(Long id) {
    return mapper.getAmbasadorBeanById(id);
  }

  @Override
  public AmbasadorBean createAmbasador(AmbasadorBean bean) {
    return mapper.createAmbasador(bean);
  }

  @Override
  public AmbasadorBean updateAmbasador(AmbasadorBean bean) {
    return mapper.updateAmbasador(bean);
  }

  @Override
  public void deleteScreenById(Long id) {
    mapper.deleteAmbasadorById(id);
  }



}
