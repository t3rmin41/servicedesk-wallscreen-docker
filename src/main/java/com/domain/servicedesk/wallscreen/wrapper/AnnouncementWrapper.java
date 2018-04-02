package com.domain.servicedesk.wallscreen.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.domain.servicedesk.wallscreen.bean.AnnouncementBean;

public class AnnouncementWrapper {

  private List<AnnouncementBean> records = new ArrayList<AnnouncementBean>();

  public List<AnnouncementBean> getRecords() {
    return records;
  }
}
