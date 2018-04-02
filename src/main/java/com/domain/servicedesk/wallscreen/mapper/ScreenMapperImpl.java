package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Ambasador;
import com.domain.servicedesk.wallscreen.jpa.Announcement;
import com.domain.servicedesk.wallscreen.jpa.Client;
import com.domain.servicedesk.wallscreen.jpa.Clock;
import com.domain.servicedesk.wallscreen.jpa.Screen;
import com.domain.servicedesk.wallscreen.repository.AmbasadorRepository;
import com.domain.servicedesk.wallscreen.repository.AnnouncementRepository;
import com.domain.servicedesk.wallscreen.repository.ClientRepository;
import com.domain.servicedesk.wallscreen.repository.ClockRepository;
import com.domain.servicedesk.wallscreen.repository.ScreenRepository;

@Component
public class ScreenMapperImpl implements ScreenMapper, BeanValidator {

  @Autowired
  private ScreenRepository screenRepo;
  
  @Autowired
  private ClockRepository clockRepo;
  
  @Autowired
  private AmbasadorRepository ambasadorRepo;
  
  @Autowired
  private AnnouncementRepository announcementRepo;
  
  @Autowired
  private ClientRepository clientRepo;
  
  @Override
  public List<ScreenBean> getAllScreens() {
    List<ScreenBean> beans = new ArrayList<ScreenBean>();
    for (Screen jpa : screenRepo.getAllScreens()) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }

  @Override
  public List<ScreenBean> getEditableScreens() {
    List<ScreenBean> beans = new ArrayList<ScreenBean>();
    for (Screen jpa : screenRepo.getEditableScreens()) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }
  
  @Override
  public ScreenBean getScreenById(Long id) {
    return convertJpaToBean(screenRepo.getScreenById(id));
  }

  @Override
  public ScreenBean getScreenByTitle(String title) {
    return convertJpaToBean(screenRepo.getScreenByTitle(title));
  }

  @Override
  public ScreenBean createScreen(ScreenBean screen) {
    Screen created = screenRepo.createScreen(convertNewBeanToJpa(screen));
    return convertJpaToBean(created);
  }

  @Override
  public ScreenBean updateScreen(ScreenBean screen) {
    Screen updateable = convertExistingBeanToJpa(screen);
    updateable.setTitle(screen.getTitle());
    return convertJpaToBean(screenRepo.updateScreen(updateable));
  }

  @Override
  public void deleteScreenById(Long id) {
    Screen jpa = screenRepo.getScreenById(id);
    Screen none = screenRepo.getScreenById(0L);

    jpa.getClocks().stream().forEach(c -> {
      c.setScreen(none);
      clockRepo.updateClock(c);
    });
    jpa.setClocks(new LinkedList<Clock>());
    
    jpa.getClients().stream().forEach(c -> {
       c.setScreen(none);
       clientRepo.updateClient(c);
    });
    jpa.setClients(new LinkedList<Client>());
    
    jpa.getAnnouncements().stream().forEach(a -> {
      a.setScreen(none);
      announcementRepo.updateAnnouncement(a);
    });
    jpa.setAnnouncements(new LinkedList<Announcement>());
    
    jpa.getAmbasadors().stream().forEach( a -> {
      a.setScreen(none);
      ambasadorRepo.updateAmbasador(a);
    });
    jpa.setAmbasadors(new LinkedList<Ambasador>());

    screenRepo.deleteScreenById(jpa.getId());
  }

  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    ScreenBean screenBean = (ScreenBean) bean;
    Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9_]*$");
    boolean hasNonAlphanumericChars = !alphanumericPattern.matcher(screenBean.getTitle()).find();
    if (hasNonAlphanumericChars) {
      errors.add(new ErrorField("title", "Screen title cannot can contain only alphanumeric characters"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong screen fields", errors);
    }
    return errors;
  }

  @Override
  public ScreenBean convertJpaToBean(Screen jpa) {
    return new ScreenBean().setId(null != jpa.getId() ? jpa.getId().toString() : null)
                           .setTitle(jpa.getTitle());
  }

  private Screen convertNewBeanToJpa(ScreenBean bean) {
    validateBean(bean);
    Screen jpa = new Screen();
    jpa.setTitle(bean.getTitle());
    return jpa;
  }
  
  private Screen convertExistingBeanToJpa(ScreenBean bean) {
    validateBean(bean);
    Screen jpa = screenRepo.getScreenById(new Long(bean.getId()));
    if (null == jpa) {
      jpa = screenRepo.getScreenByTitle(bean.getTitle());
    }
    return jpa;
  }

}
