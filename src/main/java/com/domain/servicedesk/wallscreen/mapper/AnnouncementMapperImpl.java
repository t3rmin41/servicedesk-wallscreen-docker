package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;
import com.domain.servicedesk.wallscreen.bean.AnnouncementTypeBean;
import com.domain.servicedesk.wallscreen.enums.AnnouncementType;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Announcement;
import com.domain.servicedesk.wallscreen.repository.AnnouncementRepository;
import com.domain.servicedesk.wallscreen.repository.ScreenRepository;
import com.domain.servicedesk.wallscreen.repository.UserRepository;

@Component
public class AnnouncementMapperImpl implements AnnouncementMapper, BeanValidator {

  @Autowired
  private AnnouncementRepository repo;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private ScreenRepository screenRepo;
  
  @Autowired
  private ScreenMapper screenMapper;
  
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
  
  @Override
  public AnnouncementBean getAnnouncementById(Long id) {
    return convertJpaToBean(repo.getAnnouncementById(id));
  }

  @Override
  public List<AnnouncementBean> getAllAnnouncements() {
    List<AnnouncementBean> beans = new LinkedList<AnnouncementBean>();
    repo.getAllAnnouncements().stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }

  @Override
  public List<AnnouncementBean> getAnnouncementsByScreenId(Long id) {
    List<AnnouncementBean> beans = new LinkedList<AnnouncementBean>();
    repo.getAnnouncementByScreenId(id).stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }

  @Override
  public List<AnnouncementBean> getAnnouncementsByScreenTitle(String title) {
    List<AnnouncementBean> beans = new LinkedList<AnnouncementBean>();
    repo.getAnnouncementByScreenTitle(title).stream().forEach(a -> {
      beans.add(convertJpaToBean(a));
    });
    return beans;
  }
  
  @Override
  public AnnouncementBean createAnnouncement(AnnouncementBean bean) {
    return convertJpaToBean(repo.createAnnouncement(convertNewBeanToJpa(bean)));
  }

  @Override
  public AnnouncementBean updateAnnouncement(AnnouncementBean bean) {
    return convertJpaToBean(repo.updateAnnouncement(convertExistingBeanToJpa(bean)));
  }

  @Override
  public void deleteAnnouncement(AnnouncementBean bean) {
    repo.deleteAnnouncementById(new Long(bean.getId()));
  }

  @Override
  public void deleteAnnouncementById(Long id) {
    repo.deleteAnnouncementById(id);
  }

  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    AnnouncementBean announcement = (AnnouncementBean) bean;
    if (null != announcement.getId() && !StringUtils.isNumeric(announcement.getId())) {
      errors.add(new ErrorField("id", "ID must be numeric"));
    }
    if (null != announcement.getPriority() && !"".equals(announcement.getPriority()) && !StringUtils.isNumeric(announcement.getPriority())) {
      errors.add(new ErrorField("priority", "Priority must be numeric"));
    }
    if (null == AnnouncementType.getTypeByCode(announcement.getType().getCode())) {
      errors.add(new ErrorField("type", "Invalid announcement type"));
    }
    if (null != AnnouncementType.getTypeByCode(announcement.getType().getCode()) 
        && (AnnouncementType.MAJOR_INCIDENT.equals(AnnouncementType.getTypeByCode(announcement.getType().getCode())))
        && (null == announcement.getPriority() || "".equals(announcement.getPriority()) )
       ) {
      errors.add(new ErrorField("priority", "Priority must be set for major incident"));
    }
    if (null != announcement.getTime()) {
        try {
          Date date = dateFormat.parse(announcement.getTime());
        } catch (ParseException e) {
          errors.add(new ErrorField("time", "Time format should be 'yyyy-MM-dd HH:mm:ss TIMEZONE'"));
        }
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong announcement bean", errors);
    }
    return errors;
  }
  
  @Override
  public AnnouncementTypeBean getTypeBeanByCode(String code) {
    for (AnnouncementType type : AnnouncementType.values()) {
      if (type.toString().equals(code)) {
        return new AnnouncementTypeBean().setCode(type.toString()).setTitle(type.getTitle());
      }
    }
    // TODO Auto-generated method stub
    return null;
  }
  
  private AnnouncementBean convertJpaToBean(Announcement jpa) {
    return new AnnouncementBean().setId(jpa.getId().toString())
                                 .setText(jpa.getText())
                                 .setStatus(jpa.getStatus())
                                 .setOwner(userMapper.convertUserToBeanByUserId(jpa.getOwner().getId()))
                                 .setPriority(jpa.getPriority())
                                 .setTime(jpa.getTime())
                                 .setType(getTypeBeanByCode(jpa.getType()))
                                 .setScreen(screenMapper.convertJpaToBean(jpa.getScreen()));
  }
  
  private Announcement convertNewBeanToJpa(AnnouncementBean bean) {
    validateBean(bean);
    Announcement jpa = new Announcement();
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    Long ownerId = null != bean.getOwner() && null != bean.getOwner().getId() ? new Long(bean.getOwner().getId()) : 0L;
    Long modifiedBy = null != bean.getModifiedBy() && null != bean.getModifiedBy().getId() ? new Long(bean.getModifiedBy().getId()) : 0L;
    jpa.setOwner(userRepo.getUserById(ownerId));
    jpa.setModifiedBy(userRepo.getUserById(modifiedBy));
    return jpa;
  }
  
  private Announcement convertExistingBeanToJpa(AnnouncementBean bean) {
    validateBean(bean);
    Announcement jpa = repo.getAnnouncementById(new Long(bean.getId()));
    jpa = setSimpleFieldsFromBean(jpa, bean);
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    Long modifiedBy = null != bean.getModifiedBy() && null != bean.getModifiedBy().getId() ? new Long(bean.getModifiedBy().getId()) : 0L;
    jpa.setModifiedBy(userRepo.getUserById(modifiedBy));
    return jpa;
  }
  
  private Announcement setSimpleFieldsFromBean(Announcement jpa, AnnouncementBean bean) {
    jpa.setText(bean.getText());
    jpa.setStatus(bean.getStatus());
    jpa.setType(bean.getType().getCode());
    jpa.setPriority(bean.getPriority());
    jpa.setTime(bean.getTime());
    return jpa;
  }



}
