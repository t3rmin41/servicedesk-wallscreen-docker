package com.domain.servicedesk.wallscreen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.servicedesk.wallscreen.bean.SkillBean;
import com.domain.servicedesk.wallscreen.mapper.SkillMapper;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  private SkillMapper mapper;
  
  @Override
  public List<SkillBean> getAllSkills() {
    return mapper.getAllSkills();
  }

  @Override
  public SkillBean getSkillById(Long id) {
    return mapper.getSkillById(id);
  }

  @Override
  public SkillBean createSkill(SkillBean bean) {
    return mapper.createSkill(bean);
  }

  @Override
  public SkillBean updateSkill(SkillBean bean) {
    return mapper.updateSkill(bean);
  }

  @Override
  public void deleteScreenById(Long id) {
    mapper.deleteSkillById(id);
  }

  @Override
  public List<SkillBean> getSkillsByClientId(Long id) {
    return mapper.getSkillsByClientId(id);
  }

  @Override
  public List<SkillBean> getSkillsByScreenId(Long screenId) {
    return mapper.getSkillByScreenId(screenId);
  }

}
