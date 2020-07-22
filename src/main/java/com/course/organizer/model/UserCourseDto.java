package com.course.organizer.model;

import java.io.Serializable;


public interface UserCourseDto extends Serializable {

  String getCourseId();
  String getCourseDescription();
  String getCourseTitle();
  String getCourseStatus();
  String getUserCourseStatus();

}

