package com.course.organizer.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name="course_user")
public class CourseUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="user_id")
  public User user;

  @OneToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="course_id")
  public Course course;

  @Column(name="status")
  public String status;

}
