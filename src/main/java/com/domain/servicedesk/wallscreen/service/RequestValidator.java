package com.domain.servicedesk.wallscreen.service;

import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.domain.servicedesk.wallscreen.security.UserNotAllowedException;

public interface RequestValidator {

  boolean validateRequestAgainstUserRoles(UsernamePasswordAuthenticationToken token, List<String> allowedRoles, String path)
  throws UserNotAllowedException;
}
