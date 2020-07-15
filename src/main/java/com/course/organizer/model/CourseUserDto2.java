package com.course.organizer.model;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public interface CourseUserDto2 extends Serializable {

  String getUserId();
  String getCourseId();
  String getCourseName();
  String getCourseTitle();
  String getStatus();

}

