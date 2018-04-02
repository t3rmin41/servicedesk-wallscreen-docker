package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.ClientBean;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Client;
import com.domain.servicedesk.wallscreen.repository.ClientRepository;
import com.domain.servicedesk.wallscreen.repository.ScreenRepository;

@Component
public class ClientMapperImpl implements ClientMapper, BeanValidator {

  @Autowired
  private ClientRepository clientRepo;
  
  @Autowired
  private ScreenRepository screenRepo;
  
  @Autowired
  private ScreenMapper screenMapper;
  
  @Autowired
  private SkillMapper skillMapper;
  
  @Override
  public ClientBean getClientById(Long id) {
    return convertJpaToBean(clientRepo.getClientById(id));
  }

  @Override
  public List<ClientBean> getAllClients() {
    List<ClientBean> beans = new LinkedList<ClientBean>();
    clientRepo.getAllClients().stream().forEach(c -> {
      beans.add(convertJpaToBean(c));
    });
    return beans;
  }
  
  @Override
  public List<ClientBean> getEditableClients() {
    List<ClientBean> beans = new LinkedList<ClientBean>();
    clientRepo.getEditableClients().stream().forEach(c -> {
      beans.add(convertJpaToBean(c));
    });
    return beans;
  }

  @Override
  public List<ClientBean> getEditableClientsByScreenId(Long id) {
    List<ClientBean> beans = new LinkedList<ClientBean>();
    clientRepo.getEditableClientsByScreenId(id).stream().forEach(c -> {
      beans.add(convertJpaToBean(c));
    });
    return beans;
  }

  @Override
  public List<ClientBean> getEditableClientsByScreenTitle(String title) {
    List<ClientBean> beans = new LinkedList<ClientBean>();
    clientRepo.getEditableClientsByScreenTitle(title).stream().forEach(c -> {
      beans.add(convertJpaToBean(c));
    });
    return beans;
  }
  
  @Override
  public ClientBean createClient(ClientBean bean) {
    validateBean(bean);
    return convertJpaToBean(clientRepo.createClient(convertNewBeanToJpa(bean)));
  }

  @Override
  public ClientBean updateClient(ClientBean bean) {
    validateBean(bean);
    return convertJpaToBean(clientRepo.updateClient(convertExistingBeanToJpa(bean)));
  }

  @Override
  public void deleteClientById(Long id) {
    clientRepo.deleteClientById(id);
  }

  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    ClientBean clientBean = (ClientBean) bean;
    if (null != clientBean.getID() && !NumberUtils.isNumber(clientBean.getID())) {
      errors.add(new ErrorField("ID", "ID must be numeric"));
    }
    if (null == clientBean.getClientName()) {
      errors.add(new ErrorField("clientName", "Client title cannot be empty"));
    }
    if (null != clientBean.getAbnSla() && !NumberUtils.isNumber(clientBean.getAbnSla())) {
      errors.add(new ErrorField("abnSla", "abnSla must be number"));
    }
    if (null != clientBean.getAftSla() && !NumberUtils.isNumber(clientBean.getAftSla())) {
      errors.add(new ErrorField("aftSla", "aftSla must be number"));
    }
    if (null != clientBean.getAsaSla() && !NumberUtils.isNumber(clientBean.getAsaSla())) {
      errors.add(new ErrorField("asaSla", "asaSla must be number"));
    }
    if (null != clientBean.getTtaSla() && !NumberUtils.isNumber(clientBean.getTtaSla())) {
      errors.add(new ErrorField("ttaSla", "asaSla must be number"));
    }
    if (null != clientBean.getNoPanel() && !("0".equals(clientBean.getNoPanel()) || "1".equals(clientBean.getNoPanel()))) {
      errors.add(new ErrorField("noPanel", "noPanel must be 0 or 1"));
    }
    if (null != clientBean.getNoSLA() && !("0".equals(clientBean.getNoSLA()) || "1".equals(clientBean.getNoSLA()))) {
      errors.add(new ErrorField("noSLA", "noSLA must be 0 or 1"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong client fields", errors);
    }
    return errors;
  }
  
  @Override
  public ClientBean convertJpaToBean(Client jpa) {
    ClientBean bean = new ClientBean().setID(jpa.getId().toString())
                                      .setClientName(jpa.getTitle())
                                      .setNoPanel(null !=  jpa.getNoPanel() ? jpa.getNoPanel().toString() : null)
                                      .setAbnSla(null != jpa.getAbnSla() ? jpa.getAbnSla().toString() : null)
                                      .setAftSla(null != jpa.getAftSla() ? jpa.getAftSla().toString() : null)
                                      .setAsaSla(null != jpa.getAsaSla() ? jpa.getAsaSla().toString() : null)
                                      .setTtaSla(null != jpa.getTtaSla() ? jpa.getTtaSla().toString() : null)
                                      .setNoSLA(null != jpa.getNoSla() ? jpa.getNoSla().toString() : null)
                                      .setScreen(screenMapper.convertJpaToBean(jpa.getScreen()));
    jpa.getSkills().stream().forEach(s -> {
      bean.getSkills().add(skillMapper.convertJpaToBean(s));
    });
    return bean;
  }
  
  private Client convertNewBeanToJpa(ClientBean bean) {
    validateBean(bean);
    Client jpa = new Client();
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    return jpa;
  }
  
  private Client convertExistingBeanToJpa(ClientBean bean) {
    validateBean(bean);
    Client jpa = clientRepo.getClientById(new Long(bean.getID()));
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    return jpa;
  }
  
  private Client setSimpleFieldsFromBean(Client jpa, ClientBean bean) {
    jpa.setTitle(bean.getClientName());
    jpa.setNoPanel(new Integer(null != bean.getNoPanel() ? bean.getNoPanel() : "0"));
    jpa.setNoSla(new Integer(null != bean.getNoSLA() ? bean.getNoSLA() : "0"));
    jpa.setAbnSla(new Double(null != bean.getAbnSla() ? bean.getAbnSla() : "0"));
    jpa.setAftSla(new Double(null != bean.getAftSla() ? bean.getAftSla() : "0"));
    jpa.setAsaSla(new Double(null != bean.getAsaSla() ? bean.getAsaSla() : "0"));
    jpa.setTtaSla(new Double(null != bean.getTtaSla() ? bean.getTtaSla() : "0"));
    return jpa;
  }



}
