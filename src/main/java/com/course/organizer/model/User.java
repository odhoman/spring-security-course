package com.course.organizer.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name="user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="name")
  private String name;

  @Column(name="email")
  private String email;

  @Column(name="password")
  private String password;

  @OneToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="role_id")
  private Role role;

  @ManyToMany(fetch= FetchType.LAZY)
  @JoinTable(
      name="COURSE_USER",
      joinColumns=@JoinColumn(name="USER_ID"),
      inverseJoinColumns=@JoinColumn(name="Course_id")
  )
  private List<Course> courses;

}
