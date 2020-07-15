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
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name="role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="name")
  private String name;

  @ManyToMany(fetch= FetchType.EAGER)
  @JoinTable(
      name="ROLE_PERMISSION",
      joinColumns=@JoinColumn(name="role_id"),
      inverseJoinColumns=@JoinColumn(name="permission_id")
  )
  private List<Permission> permissions;

}
