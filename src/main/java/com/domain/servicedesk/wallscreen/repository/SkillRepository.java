package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Skill;

public interface SkillRepository {

  List<Skill> getAllSkills();
  
  List<Skill> getSkillsByClientId(Long id);
  
  List<Skill> getSkillsByScreenId(Long id);
  
  Skill getSkillById(Long id);

  Skill createSkill(Skill jpa);
  
  Skill updateSkill(Skill jpa);
  
  void deleteSkillById(Long id);
}
