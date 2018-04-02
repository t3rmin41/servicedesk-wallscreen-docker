package com.domain.servicedesk.wallscreen.security;

public class UserNotFoundException extends Exception {
  private String message;
  
  public UserNotFoundException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
