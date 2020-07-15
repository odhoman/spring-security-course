package com.course.organizer.web;

import com.course.organizer.exception.RecordNotFoundException;
import com.course.organizer.model.Course;
import com.course.organizer.model.CourseUserDto;
import com.course.organizer.service.CourseService;
import com.course.organizer.service.UserService;
import java.util.Collections;
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
    List<CourseUserDto> list = Collections.emptyList();
    String userName = null;

    if (principal instanceof UserDetails) {
      userName = ((UserDetails) principal).getUsername();
    }

    list = courseService.getUserCoursesByUserName(userName);

    model.addAttribute("userCourses", list);
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
  public String createCourseForm(Model model)
      throws RecordNotFoundException {
    model.addAttribute("course", new Course());
    return "add-course";
  }

  @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
  public String createOrUpdateCourse(Course course) {
    courseService.createOrUpdateCourse(course);
    return "redirect:/course";
  }

  @RequestMapping(path = "/delete/{id}")
  public String deleteCourseById(Model model, @PathVariable("id") Long id)
      throws RecordNotFoundException {
    courseService.deleteCourseById(id);
    return "redirect:/course";
  }

  private String getUserName() {
    return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
  }

}
