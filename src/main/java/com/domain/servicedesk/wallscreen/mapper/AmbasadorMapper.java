package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.AmbasadorBean;

public interface AmbasadorMapper {

  AmbasadorBean getAmbasadorBeanById(Long id);

  List<AmbasadorBean> getAllAmbasadorBeans();
  
  List<AmbasadorBean> getAmbasadorsByScreenId(Long id);
  
  List<AmbasadorBean> getAmbasadorsByScreenTitle(String title);

  AmbasadorBean createAmbasador(AmbasadorBean bean);
  
  AmbasadorBean updateAmbasador(AmbasadorBean bean);
  
  void deleteAmbasador(AmbasadorBean bean);
  
  void deleteAmbasadorById(Long id);
}
