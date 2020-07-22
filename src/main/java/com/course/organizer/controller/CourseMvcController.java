package com.course.organizer.controller;

import com.course.organizer.exception.RecordNotFoundException;
import com.course.organizer.model.Course;
import com.course.organizer.model.User;
import com.course.organizer.model.UserCourseDto;
import com.course.organizer.service.CourseService;
import com.course.organizer.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/course")
public class CourseMvcController {

  @Autowired
  CourseService courseService;

  @Autowired
  UserService userService;

  @RequestMapping
  public String getAllCourses(Model model) {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<UserCourseDto> list;
    String userName = null;
    User user;

    if (principal instanceof UserDetails) {
      userName = ((UserDetails) principal).getUsername();
    }

    list = courseService.getUserCoursesByUserName(userName);
    user = userService.getUserByUserName(userName);
    model.addAttribute("userCourses", list);
    model.addAttribute("userId", user.getId());
    model.addAttribute("userName", user.getName());
    model.addAttribute("userRole", user.getRole().getName());

    return "list-courses";
  }

  @RequestMapping(path = "/edit/{id}")
  public String editCourseById(Model model, @PathVariable("id") Optional<Long> id)
      throws RecordNotFoundException {
    Course entity = courseService.getCourseById(id.get());
    model.addAttribute("course", entity);
    return "edit-course";
  }

  @RequestMapping(path = "/read/{id}")
  public String readCourseById(Model model, @PathVariable("id") Optional<Long> id)
      throws RecordNotFoundException {
    Course entity = courseService.getCourseById(id.get());
    model.addAttribute("course", entity);
    return "read-course";
  }

  @RequestMapping(path = {"/create"})
  public String createCourseForm(Model model) {
    model.addAttribute("course", new Course());
    return "add-course";
  }

  @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
  public String createOrUpdateCourse(Course course) {
    courseService.createOrUpdateCourse(course);
    return "redirect:/course";
  }

  @RequestMapping(path = "/disable", method = RequestMethod.POST)
  public String disableCourseById(@RequestParam("courseId") Long courseId)
      throws RecordNotFoundException {
    courseService.disableCourseById(courseId);
    return "redirect:/course";
  }

  @RequestMapping(path = "/enable", method = RequestMethod.POST)
  public String enableCourseById(Model model, @RequestParam("courseId") Long courseId)
      throws RecordNotFoundException {
    courseService.enableCourseById(courseId);
    return "redirect:/course";
  }

  @RequestMapping(path = "/student/attend", method = RequestMethod.POST)
  public String attendCourse(Model model, @RequestParam("userId") Long userId,
      @RequestParam("courseId") Long courseId) {
    courseService.userAttendCourse(userId, courseId);
    return "redirect:/course";
  }

  @RequestMapping(path = "/student/leave", method = RequestMethod.POST)
  public String leaveCourse(Model model, @RequestParam("userId") Long userId,
      @RequestParam("courseId") Long courseId) {
    courseService.userLeaveCourse(userId, courseId);
    return "redirect:/course";
  }

  @RequestMapping(path = "/teacher/give", method = RequestMethod.POST)
  public String giveCourse(Model model, @RequestParam("userId") Long userId,
      @RequestParam("courseId") Long courseId) {
    courseService.userGiveCourse(userId, courseId);
    return "redirect:/course";
  }

  @RequestMapping(path = "/teacher/stop", method = RequestMethod.POST)
  public String stopGivingCourse(Model model, @RequestParam("userId") Long userId,
      @RequestParam("courseId") Long courseId) {
    courseService.userStopGivingCourse(userId, courseId);
    return "redirect:/course";
  }

  private String getUserName() {
    return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
  }

}
