package com.course.organizer.service;

import com.course.organizer.model.Course;
import com.course.organizer.model.Permission;
import com.course.organizer.model.Role;
import com.course.organizer.model.User;
import com.course.organizer.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException {

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    Optional<User> userAuthOptional = userRepository.findByName(userName);
    User userAuthentication = userAuthOptional.get();
    Optional<Role> roleOptional = Optional.ofNullable(userAuthentication.getRole());

    String roleName = roleOptional.map(Role::getName).get();
    List<Permission> permissions = roleOptional.map(Role::getPermissions)
        .orElse(Collections.emptyList());

    if (!StringUtils.isEmpty(roleName)) {

      // Adding All Authorities
      authorities = permissions.stream()
          .map(permission -> new SimpleGrantedAuthority(permission.getName()))
          .collect(
              Collectors.toList());

      // Adding Role as Authority
      authorities.add(new SimpleGrantedAuthority("ROLE_"+roleName));

    }

    return org.springframework.security.core.userdetails.User
        .builder().username(userName).password(userAuthOptional.get().getPassword())
        .authorities(authorities).build();

  }

  public User getUserByUserName(String userName) {
    return userRepository.findByName(userName).map(user -> {
      user.setPassword(null);
      return user;
    }).get();
  }

  public List<Course> getUserCoursesByUserName(String userName) {
    return userRepository.findByName(userName).get().getCourses();
  }

}
