package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.SkillBean;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Client;
import com.domain.servicedesk.wallscreen.jpa.Skill;
import com.domain.servicedesk.wallscreen.repository.ClientRepository;
import com.domain.servicedesk.wallscreen.repository.SkillRepository;

@Component
public class SkillMapperImpl implements SkillMapper, BeanValidator {

  @Autowired
  private SkillRepository skillRepo;

  @Autowired
  private ClientRepository clientRepo;
  
  @Override
  public SkillBean getSkillById(Long id) {
    return convertJpaToBean(skillRepo.getSkillById(id));
  }

  @Override
  public List<SkillBean> getAllSkills() {
    List<SkillBean> beans = new LinkedList<SkillBean>();
    skillRepo.getAllSkills().stream().forEach(s -> {
      beans.add(convertJpaToBean(s));
    });
    return beans;
  }

  @Override
  public SkillBean createSkill(SkillBean bean) {
    return convertJpaToBean(skillRepo.createSkill(convertNewBeanToJpa(bean)));
  }

  @Override
  public SkillBean updateSkill(SkillBean bean) {
    return convertJpaToBean(skillRepo.updateSkill(convertExistingBeanToJpa(bean)));
  }

  @Override
  public void deleteSkillById(Long id) {
    skillRepo.deleteSkillById(id);
  }

  @Override
  public List<SkillBean> getSkillByScreenId(Long id) {
    List<SkillBean> beans = new LinkedList<SkillBean>();
    skillRepo.getSkillsByScreenId(id).stream().forEach(s -> {
      beans.add(convertJpaToBean(s));
    });
    return beans;
  }
  
  @Override
  public SkillBean convertJpaToBean(Skill jpa) {
    SkillBean bean = new SkillBean().setId(null != jpa.getId() ? jpa.getId().toString() : null)
                           .setSkillName(jpa.getTitle())
                           .setIgnoreStaffing(null != jpa.getIgnoreStaffing() ? jpa.getIgnoreStaffing().toString() : null)
                           .setSplitSkill(null != jpa.getSplitSkill() ? jpa.getSplitSkill().toString() : null)
                           .setVdn(null != jpa.getVdn() ? jpa.getVdn().toString() : null)
                           .setClientID(null != jpa.getClient().getId() ? jpa.getClient().getId().toString() : null);
    Client client = clientRepo.getClientById(jpa.getClient().getId());
    bean.setClientName(client.getTitle());
    return bean;
  }
  
  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    SkillBean skillBean = (SkillBean) bean;
    if (null != skillBean.getId() && !NumberUtils.isNumber(skillBean.getId())) {
      errors.add(new ErrorField("id", "ID must be numeric"));
    }
    if (null != skillBean.getIgnoreStaffing() && !("0".equals(skillBean.getIgnoreStaffing()) || "1".equals(skillBean.getIgnoreStaffing()))) {
      errors.add(new ErrorField("ignoreStaffing", "ignoreStaffing must be 0 or 1"));
    }
    if (null != skillBean.getSplitSkill() && !NumberUtils.isNumber(skillBean.getSplitSkill())) {
      errors.add(new ErrorField("splitSkill", "splitSkill must be numeric"));
    }
    if (null != skillBean.getVdn() && !NumberUtils.isNumber(skillBean.getVdn())) {
      errors.add(new ErrorField("vdn", "vdn must be numeric"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong skill fields", errors);
    }
    return errors;
  }
  
  private Skill convertNewBeanToJpa(SkillBean bean) {
    validateBean(bean);
    Skill jpa = new Skill();
    jpa = setSimpleFieldsFromBean(jpa, bean);
    
    Long clientId = null != bean.getClientID() ? new Long(bean.getClientID()) : 0L;
    jpa.setClient(clientRepo.getClientById(clientId));
    
    return jpa;
  }
  
  private Skill convertExistingBeanToJpa(SkillBean bean) {
    validateBean(bean);
    Skill jpa = skillRepo.getSkillById(new Long(bean.getId()));
    jpa = setSimpleFieldsFromBean(jpa, bean);
    
    Long clientId = null != bean.getClientID() ? new Long(bean.getClientID()) : 0L;
    jpa.setClient(clientRepo.getClientById(clientId));
    
    return jpa;
  }
  
  private Skill setSimpleFieldsFromBean(Skill jpa, SkillBean bean) {
    jpa.setTitle(bean.getSkillName());
    jpa.setIgnoreStaffing(new Integer(null != bean.getIgnoreStaffing() ? bean.getIgnoreStaffing() : "0"));
    jpa.setSplitSkill(new Long(null != bean.getSplitSkill() ? bean.getSplitSkill() : "0"));
    jpa.setVdn(new Long(null != bean.getVdn() ? bean.getVdn() : "0"));
    return jpa;
  }

  @Override
  public List<SkillBean> getSkillsByClientId(Long id) {
    List<SkillBean> beans = new LinkedList<SkillBean>();
    skillRepo.getSkillsByClientId(id).stream().forEach(s -> {
      beans.add(convertJpaToBean(s));
    });
    return beans;
  }

}
