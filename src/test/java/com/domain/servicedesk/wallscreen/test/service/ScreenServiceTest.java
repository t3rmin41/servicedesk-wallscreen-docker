package com.domain.servicedesk.wallscreen.test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.domain.servicedesk.wallscreen.bean.ScreenBean;
import com.domain.servicedesk.wallscreen.service.ScreenService;
import com.domain.servicedesk.wallscreen.test.config.ServiceTestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ServiceTestConfig.class, loader=AnnotationConfigContextLoader.class)
public class ScreenServiceTest {

  @Autowired
  private ScreenService screenService;

  @Test
  @Transactional
  @Rollback
  public void testScreenCreate() {
    ScreenBean bean = new ScreenBean().setId("1").setTitle("Screen1");
    screenService.createScreen(bean);
    List<ScreenBean> results = screenService.getEditableScreens();
    assertEquals(1, results.size());
    results = screenService.getAllScreens();
    assertEquals(2, results.size());
  }
  
  
}
