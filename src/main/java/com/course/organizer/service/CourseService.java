package com.course.organizer.service;

import com.course.organizer.exception.RecordNotFoundException;
import com.course.organizer.model.Course;
import com.course.organizer.model.CourseUserDto;
import com.course.organizer.model.CourseUserDto2;
import com.course.organizer.model.User;
import com.course.organizer.repository.CourseRepository;
import com.course.organizer.repository.CourseUserRepository;
import com.course.organizer.security.ApplicationUserRole;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  @Autowired
  CourseRepository repository;

  @Autowired
  UserService userService;

  @Autowired
  CourseUserRepository courseUserRepository;

  public List<Course> getAllCourses() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public Course getCourseById(Long id) throws RecordNotFoundException {
    Optional<Course> courseEntity = repository.findById(id);

    if (courseEntity.isPresent()) {
      return courseEntity.get();
    } else {
      throw new RecordNotFoundException("No course record exist for given id");
    }
  }

  public Course createOrUpdateCourse(Course entity) {
    if (entity.getId() == null) {
      entity = repository.save(entity);

      return entity;
    } else {
      Optional<Course> courseEntity = repository.findById(entity.getId());

      if (courseEntity.isPresent()) {
        Course newEntity = courseEntity.get();
        newEntity.setTitle(entity.getTitle());
        newEntity.setDesc(entity.getDesc());

        newEntity = repository.save(newEntity);

        return newEntity;
      } else {
        entity = repository.save(entity);

        return entity;
      }
    }
  }

  public void deleteCourseById(Long id) throws RecordNotFoundException {
    Optional<Course> courseEntity = repository.findById(id);

    if (courseEntity.isPresent()) {
      repository.deleteById(id);
    } else {
      throw new RecordNotFoundException("No course record exist for given id");
    }
  }

  public List<CourseUserDto> getUserCoursesByUserName(String userName) {
    User user = userService.getUserByUserName(userName);

    List<CourseUserDto> courseUserList = Collections.emptyList();

    if (user.getRole().getName().equals(ApplicationUserRole.ADMIN.name())) {

      return courseUserList = getAllCourses().stream().map(course -> {
        CourseUserDto courseUserDto = new CourseUserDto();
        courseUserDto.setCourse(course);
        courseUserDto.setUser(user);
        return courseUserDto;
      }).collect(Collectors.toList());

    } else {

//      List<Course> courses = getAllCourses();

      courseUserList = courseUserRepository
          .getAllUserNotUserCoursesByUserId(user.getId()).stream().map(
              courseUser -> new CourseUserDto(user, courseUser.getCourse(), courseUser.getStatus()))
          .collect(Collectors.toList());

      final List<CourseUserDto2> allUserNotUserCoursesByUserId2 = courseUserRepository
          .getAllUserNotUserCoursesByUserId2(user.getId());

      System.out.println(allUserNotUserCoursesByUserId2);

      allUserNotUserCoursesByUserId2.forEach(a->{
        System.out.println("-------------");
        System.out.println(a.getUserId());
        System.out.println(a.getCourseId());
        System.out.println(a.getCourseTitle());
        System.out.println(a.getCourseName());
        System.out.println(a.getStatus());
        System.out.println("-------------");
      });

//      List<CourseUserDto> courseUserNoAdminList = courseUserRepository
//          .getAllUserCoursesByUserId(user.getId()).stream().map(
//              courseUser -> new CourseUserDto(user, courseUser.getCourse(), courseUser.getStatus()))
//          .collect(Collectors.toList());
//
//      if (courseUserNoAdminList.isEmpty()) {
//        courseUserList = courses.stream().map(course -> new CourseUserDto(user, course, null))
//            .collect(
//                Collectors.toList());
//      } else {
//
//        List<Course> userSimpleCourses = courseUserNoAdminList.stream()
//            .map(CourseUserDto::getCourse).collect(
//                Collectors.toList());
//
//      }

//
//      courseUserList.addAll(courseUserNoAdminList);
//
//      courses.stream().filter()

    }
    return courseUserList;
  }
}