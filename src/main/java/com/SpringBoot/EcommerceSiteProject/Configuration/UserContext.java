package com.SpringBoot.EcommerceSiteProject.Configuration;

import com.SpringBoot.EcommerceSiteProject.User.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserContext {

  private static final ThreadLocal<Long> CONTEXT = new ThreadLocal<>();

  public static void setUserId(Long userId) {
    CONTEXT.set(userId);
  }

  public static Long getUserId() {
    return CONTEXT.get();
  }

}
