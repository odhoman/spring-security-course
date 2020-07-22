package com.course.organizer.security;

public enum ApplicationUserPermission {

  COURSE_READ   ("course:read"),

  COURSE_ATTEND ("course:attend"),
  COURSE_LEAVE  ("course:leave"),

  COURSE_GIVE("course:give"),
  COURSE_STOP   ("course:stop"),

  COURSE_CREATE ("course:create"),
  COURSE_DISABLE("course:disable"),
  COURSE_ENABLE("course:enable"),
  COURSE_UPDATE ("course:update");


  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
  }
