package com.domain.servicedesk.wallscreen.mapper;

import java.util.List;

import com.domain.servicedesk.wallscreen.bean.SkillBean;
import com.domain.servicedesk.wallscreen.jpa.Skill;

public interface SkillMapper {

  SkillBean getSkillById(Long id);

  List<SkillBean> getAllSkills();
  
  List<SkillBean> getSkillsByClientId(Long id);
  
  List<SkillBean> getSkillByScreenId(Long id);

  SkillBean createSkill(SkillBean bean);
  
  SkillBean updateSkill(SkillBean bean);

  void deleteSkillById(Long id);
  
  SkillBean convertJpaToBean(Skill jpa);
}
