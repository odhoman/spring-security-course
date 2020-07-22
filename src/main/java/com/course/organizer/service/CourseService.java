package com.course.organizer.service;

import static com.course.organizer.model.CourseState.AVAILABLE;
import static com.course.organizer.model.CourseState.DISABLED;

import com.course.organizer.exception.RecordNotFoundException;
import com.course.organizer.model.Course;
import com.course.organizer.model.CourseState;
import com.course.organizer.model.User;
import com.course.organizer.model.UserCourse;
import com.course.organizer.model.UserCourseDto;
import com.course.organizer.model.UserCourseState;
import com.course.organizer.repository.CourseRepository;
import com.course.organizer.repository.UserCourseRepository;
import com.course.organizer.security.ApplicationUserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  @Autowired
  UserService userService;

  @Autowired
  UserCourseRepository userCourseRepository;

  @Autowired
  CourseRepository courseRepository;

  public List<UserCourseDto> getAllCourses() {
    List<UserCourseDto> courseUserList = courseRepository.getAllCourses();
    courseUserList.forEach(a -> {
      System.out.println("-------------");
      System.out.println(a.getCourseId());
      System.out.println(a.getCourseTitle());
      System.out.println(a.getCourseDescription());
      System.out.println(a.getCourseStatus());
      System.out.println(a.getUserCourseStatus());
      System.out.println("-------------");
    });
    return courseUserList;
  }

  public Course getCourseById(Long id) throws RecordNotFoundException {
    Optional<Course> courseEntity = courseRepository.findById(id);

    if (courseEntity.isPresent()) {
      return courseEntity.get();
    } else {
      throw new RecordNotFoundException("No course record exist for given id");
    }
  }

  public void createOrUpdateCourse(Course course) {
    if (course.getId() == null) {
      course.setStatus(AVAILABLE.name());
      courseRepository.save(course);
    } else {
      Optional<Course> courseEntity = courseRepository.findById(course.getId());

      if (courseEntity.isPresent()) {
        Course newEntity = courseEntity.get();
        newEntity.setTitle(course.getTitle());
        newEntity.setDesc(course.getDesc());
      } else {
        course.setStatus(AVAILABLE.name());
      }
    }
  }

  public void enableCourseById(Long id) throws RecordNotFoundException {
    changeStatusCourse(id, AVAILABLE);
  }

  public void disableCourseById(Long id) throws RecordNotFoundException {
    changeStatusCourse(id, DISABLED);
  }

  private void changeStatusCourse(Long id, CourseState courseState) throws RecordNotFoundException {
    Optional<Course> courseOptional = courseRepository.findById(id);

    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();
      courseOptional.get().setStatus(courseState.name());
      courseRepository.save(course);
    } else {
      throw new RecordNotFoundException("No course record exist for given id");
    }
  }

  public List<UserCourseDto> getUserCoursesByUserName(String userName) {
    List<UserCourseDto> courseUserList;

    User user = userService.getUserByUserName(userName);
    if (user.getRole().getName().equals(ApplicationUserRole.ADMIN.name())) {
      courseUserList = getAllCourses();
    } else {

      courseUserList = userCourseRepository
          .getAllUserAndNotUserCoursesByUserId(user.getId());

      System.out.println(courseUserList);

      courseUserList.forEach(a -> {
        System.out.println("-------------");
        System.out.println(a.getCourseId());
        System.out.println(a.getCourseTitle());
        System.out.println(a.getCourseDescription());
        System.out.println(a.getCourseStatus());
        System.out.println(a.getUserCourseStatus());
        System.out.println("-------------");
      });

    }
    return courseUserList;
  }

  public void userAttendCourse(Long userId, Long courseId) {
    changeUserCourseStatus(userId, courseId, UserCourseState.ATTENDING);
  }

  public void userLeaveCourse(Long userId, Long courseId) {
    changeUserCourseStatus(userId, courseId, UserCourseState.LEAVED);
  }

  public void userGiveCourse(Long userId, Long courseId) {
    changeUserCourseStatus(userId, courseId, UserCourseState.GIVING_COURSE);
  }

  public void userStopGivingCourse(Long userId, Long courseId) {
    changeUserCourseStatus(userId, courseId, UserCourseState.GIVING_COURSE_STOPPED);
  }

  private void changeUserCourseStatus(Long userId, Long courseId, UserCourseState userCourseState) {

    Optional<UserCourse> userCourseOptional = userCourseRepository
        .findByCourseIdAndUserId(courseId, userId);

    UserCourse userCourse = userCourseOptional.orElseGet(() -> {
      User user = new User();
      user.setId(userId);
      Course course = new Course();
      course.setId(courseId);
      UserCourse newUserCourse = new UserCourse();
      newUserCourse.setUser(user);
      newUserCourse.setCourse(course);
      return newUserCourse;
    });

    userCourse.setStatus(userCourseState.name());

    userCourseRepository.save(userCourse);
  }
}