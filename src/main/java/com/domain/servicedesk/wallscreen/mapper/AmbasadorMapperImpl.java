package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.AmbasadorBean;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Ambasador;
import com.domain.servicedesk.wallscreen.repository.AmbasadorRepository;
import com.domain.servicedesk.wallscreen.repository.ScreenRepository;

@Component
public class AmbasadorMapperImpl implements AmbasadorMapper, BeanValidator {

  @Autowired
  private AmbasadorRepository repo;
  
  @Autowired
  private ScreenRepository screenRepo;
  
  @Autowired
  private ScreenMapper screenMapper;
  
  @Override
  public AmbasadorBean getAmbasadorBeanById(Long id) {
    return convertJpaToBean(repo.getAmbasadorById(id));
  }

  @Override
  public List<AmbasadorBean> getAllAmbasadorBeans() {
    List<AmbasadorBean> beans = new LinkedList<AmbasadorBean>();
    repo.getAllAmbasadors().stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }

  @Override
  public List<AmbasadorBean> getAmbasadorsByScreenId(Long id) {
    List<AmbasadorBean> beans = new LinkedList<AmbasadorBean>();
    repo.getAmbasadorsByScreenId(id).stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }

  @Override
  public List<AmbasadorBean> getAmbasadorsByScreenTitle(String title) {
    List<AmbasadorBean> beans = new LinkedList<AmbasadorBean>();
    repo.getAmbasadorsByScreenTitle(title).stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }
  
  
  @Override
  public AmbasadorBean createAmbasador(AmbasadorBean bean) {
    return convertJpaToBean(repo.createAmbasador(convertNewBeanToJpa(bean)));
  }

  @Override
  public AmbasadorBean updateAmbasador(AmbasadorBean bean) {
    return convertJpaToBean(repo.updateAmbasador(converExistingBeanToJpa(bean)));
  }

  @Override
  public void deleteAmbasador(AmbasadorBean bean) {
    repo.getAmbasadorById(new Long(bean.getId()));
  }

  @Override
  public void deleteAmbasadorById(Long id) {
    repo.deleteAmbasadorById(id);
  }

  @Override
  public List<ErrorField> validateBean(Serializable bean) {
    AmbasadorBean ambasadorBean = (AmbasadorBean) bean;
    List<ErrorField> errors = new LinkedList<ErrorField>();
    if (null != ambasadorBean.getId() && !StringUtils.isNumeric(ambasadorBean.getId())) {
      errors.add(new ErrorField("id", "ID must be numeric"));
    }
    if (null == ambasadorBean.getName()) {
      errors.add(new ErrorField("name", "Name cannot be empty"));
    }
    if (null != ambasadorBean.getAvayaID() && !StringUtils.isNumeric(ambasadorBean.getAvayaID())) {
      errors.add(new ErrorField("avayaID", "Avaya ID must be numeric"));
    }
    if (null != ambasadorBean.getPhoneID() && !StringUtils.isNumeric(ambasadorBean.getPhoneID())) {
      errors.add(new ErrorField("phoneID", "Phone ID must be numeric"));
    }
    if (null != ambasadorBean.getENSME() && !("0".equals(ambasadorBean.getENSME()) || "1".equals(ambasadorBean.getENSME()))) {
      errors.add(new ErrorField("ENSME", "ENSME must be 0 or 1"));
    }
    if (null != ambasadorBean.getENmember() && !("0".equals(ambasadorBean.getENmember()) || "1".equals(ambasadorBean.getENmember()))) {
      errors.add(new ErrorField("ENmember", "ENmember must be 0 or 1"));
    }
    if (null != ambasadorBean.getLowerskill() && !("0".equals(ambasadorBean.getLowerskill()) || "1".equals(ambasadorBean.getLowerskill()))) {
      errors.add(new ErrorField("Lowerskill", "Lowerskill must be 0 or 1"));
    }
    if (null != ambasadorBean.getHideAccount() && !("0".equals(ambasadorBean.getHideAccount()) || "1".equals(ambasadorBean.getHideAccount()))) {
      errors.add(new ErrorField("hideAccount", "hideAccount must be 0 or 1"));
    }
    if (null != ambasadorBean.getWatchlist() && !("0".equals(ambasadorBean.getWatchlist()) || "1".equals(ambasadorBean.getWatchlist()))) {
      errors.add(new ErrorField("watchlist", "watchlist must be 0 or 1"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong ambasador bean", errors);
    }
    return errors;
  }
  
  private AmbasadorBean convertJpaToBean(Ambasador jpa) {
    return new AmbasadorBean().setId(null != jpa.getId() ? jpa.getId().toString() : null)
                              .setAvayaID(null != jpa.getAvayaId() ? jpa.getAvayaId().toString() : null)
                              .setENmember(null != jpa.getEnMember() ? jpa.getEnMember().toString() : null)
                              .setPhoneID(null != jpa.getPhoneId() ? jpa.getPhoneId().toString() : null)
                              .setName(jpa.getName())
                              .setLowerskill(null != jpa.getLowerSkill() ? jpa.getLowerSkill().toString() : null)
                              .setENSME(null != jpa.getEnSme() ? jpa.getEnSme().toString() : null)
                              .setWatchlist(null != jpa.getWatchlist() ? jpa.getWatchlist().toString() : null)
                              .setHideAccount(null != jpa.getHideAccount() ? jpa.getHideAccount().toString() : null)
                              .setAux(jpa.getAux())
                              .setAuxCode(jpa.getAuxCode())
                              .setTimeins(jpa.getTimeins())
                              .setScreen(screenMapper.convertJpaToBean(jpa.getScreen()));
  }
  
  private Ambasador convertNewBeanToJpa(AmbasadorBean bean) {
    validateBean(bean);
    Ambasador jpa = new Ambasador();
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    return jpa;
  }
  
  private Ambasador converExistingBeanToJpa(AmbasadorBean bean) {
    validateBean(bean);
    Ambasador jpa = repo.getAmbasadorById(new Long(bean.getId()));
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    return jpa;
  }
  
  private Ambasador setSimpleFieldsFromBean(Ambasador jpa, AmbasadorBean bean) {
    jpa.setName(bean.getName());
    jpa.setAvayaId(new Integer(null != bean.getAvayaID() ? bean.getAvayaID() : "0"));
    jpa.setEnSme(new Integer(null != bean.getENSME() ? bean.getENSME() : "0"));
    jpa.setEnMember(new Integer(null != bean.getENmember() ? bean.getENmember() : "0"));
    jpa.setPhoneId(new Integer(null != bean.getPhoneID() ? bean.getPhoneID() : "0"));
    jpa.setLowerSkill(new Integer(null != bean.getLowerskill() ? bean.getLowerskill() : "0"));
    jpa.setAux(bean.getAux());
    jpa.setAuxCode(bean.getAuxCode());
    jpa.setHideAccount(new Integer(null != bean.getHideAccount() ? bean.getHideAccount() : "1"));
    jpa.setWatchlist(new Integer(null != bean.getWatchlist() ? bean.getWatchlist() : "0"));
    jpa.setTimeins(bean.getTimeins());
    return jpa;
  }



}
