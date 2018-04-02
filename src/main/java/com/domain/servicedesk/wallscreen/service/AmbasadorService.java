package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.AmbasadorBean;

public interface AmbasadorService {

  List<AmbasadorBean> getAllAmbasadors();
  
  List<AmbasadorBean> getAmbasadorsByScreenId(Long id);
  
  List<AmbasadorBean> getAmbasadorsByScreenTitle(String title);

  AmbasadorBean getAmbasadorById(Long id);
  
  AmbasadorBean createAmbasador(AmbasadorBean bean);
  
  AmbasadorBean updateAmbasador(AmbasadorBean bean);
  
  void deleteScreenById(Long id);
}
