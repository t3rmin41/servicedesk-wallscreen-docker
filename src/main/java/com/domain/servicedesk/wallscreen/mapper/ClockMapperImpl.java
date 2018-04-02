package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.servicedesk.wallscreen.bean.ClockBean;
import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;
import com.domain.servicedesk.wallscreen.jpa.Clock;
import com.domain.servicedesk.wallscreen.jpa.Screen;
import com.domain.servicedesk.wallscreen.repository.ClockRepository;
import com.domain.servicedesk.wallscreen.repository.ScreenRepository;

@Component
public class ClockMapperImpl implements ClockMapper, BeanValidator {

  @Autowired
  private ClockRepository clockRepo;
  
  @Autowired
  private ScreenRepository screenRepo;
  
  @Override
  public ClockBean getClockById(Long id) {
    return convertJpaToBean(clockRepo.getClockById(id));
  }

  @Override
  public ClockBean getClockByTitle(String title) {
    return convertJpaToBean(clockRepo.getClockByTitle(title));
  }

  @Override
  public List<ClockBean> getAllClocks() {
    List<Clock> jpas = clockRepo.getAllClocks();
    List<ClockBean> beans = new ArrayList<ClockBean>();
    for (Clock jpa : jpas) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }

  @Override
  public List<ClockBean> getClocksByScreenId(Long id) {
    List<Clock> jpas = clockRepo.getClocksByScreenId(id);
    List<ClockBean> beans = new ArrayList<ClockBean>();
    for (Clock jpa : jpas) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }

  @Override
  public List<ClockBean> getClocksByScreenTitle(String title) {
    List<Clock> jpas = clockRepo.getClocksByScreenTitle(title);
    List<ClockBean> beans = new ArrayList<ClockBean>();
    for (Clock jpa : jpas) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }
  
  @Override
  public List<ClockBean> getClocksByOrder(Long order) {
    List<Clock> jpas = clockRepo.getClocksByOrder(order);
    List<ClockBean> beans = new ArrayList<ClockBean>();
    for (Clock jpa : jpas) {
      beans.add(convertJpaToBean(jpa));
    }
    return beans;
  }

  @Override
  public ClockBean createClock(ClockBean bean) {
    validateBean(bean);
    return convertJpaToBean(clockRepo.createClock(convertNewBeanToJpa(bean)));
  }

  @Override
  public ClockBean updateClock(ClockBean bean) {
    validateBean(bean);
    Clock jpa = clockRepo.getClockById(new Long(bean.getId()));
    jpa.setTitle(bean.getClockName());
    jpa.setGMT(new Double(bean.getClockGMT()));
    jpa.setOrder(new Long(bean.getClockOrder()));
    Long screenId = null != bean.getScreen() && null != bean.getScreen().getId() ? new Long(bean.getScreen().getId()) : 0L;
    jpa.setScreen(screenRepo.getScreenById(screenId));
    return convertJpaToBean(clockRepo.updateClock(jpa));
  }

  @Override
  public void deleteClock(ClockBean bean) {
    clockRepo.deleteClock(clockRepo.getClockById(new Long(bean.getId())));
  }

  @Override
  public void deleteClockById(Long id) {
    clockRepo.deleteClockById(id);
  }

  @Override
  public List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    ClockBean clockBean = (ClockBean) bean;
    List<Clock> clocks = clockRepo.getClocksByOrderAndScreen(new Long(clockBean.getClockOrder()), new Long(clockBean.getScreen().getId()));
    if (null != clockBean.getId() && !NumberUtils.isNumber(clockBean.getId())) {
      errors.add(new ErrorField("id", "ID must be numeric"));
    }
    if (null != clockBean.getClockGMT() && !NumberUtils.isNumber(clockBean.getClockGMT())) {
      errors.add(new ErrorField("clockGMT", "GMT must be number"));
    }
    if (null != clockBean.getClockOrder() && !NumberUtils.isNumber(clockBean.getClockOrder())) {
      errors.add(new ErrorField("clockOrder", "Clock order must be number"));
    }
    if (null != clockBean.getClockOrder() && null != clockBean.getId() 
        && (clocks.size() > 0 && clocks.get(0).getId() == new Long(clockBean.getId()))) {
      errors.add(new ErrorField("clockOrder", "Clock order and screen combination must be unique"));
    }
    if (null != clockBean.getClockOrder() && null == clockBean.getId() && clocks.size() > 0) {
      errors.add(new ErrorField("clockOrder", "Clock order and screen combination must be unique"));
    }
    if (errors.size() > 0) {
      throw new WrongBeanFormatException("Wrong clock fields", errors);
    }
    return errors;
  }
  
  private ScreenBean convertScreenJpaToBean(Screen jpa) {
    return new ScreenBean().setId(jpa.getId().toString()).setTitle(jpa.getTitle());
  }
  
  private ClockBean convertJpaToBean(Clock jpa) {
    ClockBean bean = new ClockBean()
                         .setId(jpa.getId().toString())
                         .setClockName(jpa.getTitle())
                         .setClockGMT(jpa.getGMT().toString())
                         .setClockOrder(jpa.getOrder().toString());
    ScreenBean screenBean = convertScreenJpaToBean(screenRepo.getScreenById(jpa.getScreen().getId()));
    bean.setScreen(screenBean);
    return bean;
  }
  
  private Clock convertNewBeanToJpa(ClockBean bean) {
    validateBean(bean);
    Clock jpa = new Clock();
    jpa.setTitle(bean.getClockName());
    jpa.setGMT(new Double(bean.getClockGMT()));
    jpa.setOrder(new Long(bean.getClockOrder()));
    Screen screen = screenRepo.getScreenById(new Long(bean.getScreen().getId()));
    jpa.setScreen(screen);
    return jpa;
  }
  
  private Clock convertExistingBeanToJpa(ClockBean bean) {
    validateBean(bean);
    Clock jpa = clockRepo.getClockById(new Long(bean.getId()));
    return jpa;
  }

}
