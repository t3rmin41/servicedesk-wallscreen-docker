package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.SkillBean;

public interface SkillService {

  List<SkillBean> getAllSkills();
  
  List<SkillBean> getSkillsByClientId(Long id);

  List<SkillBean> getSkillsByScreenId(Long screenId);
  
  SkillBean getSkillById(Long id);
  
  SkillBean createSkill(SkillBean bean);
  
  SkillBean updateSkill(SkillBean bean);
  
  void deleteScreenById(Long id);
}
