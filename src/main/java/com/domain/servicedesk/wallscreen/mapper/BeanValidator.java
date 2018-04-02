package com.domain.servicedesk.wallscreen.mapper;

import java.io.Serializable;
import java.util.List;

import com.domain.servicedesk.wallscreen.errorhandling.ErrorField;
import com.domain.servicedesk.wallscreen.errorhandling.WrongBeanFormatException;

public interface BeanValidator {

  List<ErrorField> validateBean(Serializable bean) throws WrongBeanFormatException;
}
