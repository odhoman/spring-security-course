package com.course.organizer.security;

import static com.course.organizer.security.ApplicationUserPermission.COURSE_ATTEND;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_CREATE;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_DISABLE;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_LEAVE;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_READ;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_UPDATE;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_GIVE;
import static com.course.organizer.security.ApplicationUserPermission.COURSE_STOP;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
  STUDENT(Sets.newHashSet(COURSE_READ, COURSE_ATTEND, COURSE_LEAVE)),
  TEACHER(Sets.newHashSet(COURSE_READ, COURSE_GIVE, COURSE_STOP)),
  ADMIN(Sets.newHashSet(COURSE_READ, COURSE_CREATE, COURSE_DISABLE, COURSE_UPDATE));

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
