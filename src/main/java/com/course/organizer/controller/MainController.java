package com.course.organizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {


  @GetMapping
  public String getHome() {
    return "redirect:/course";
  }

  @GetMapping("login")
  public String getLogin() {
    return "login";
  }

  @GetMapping("login-error")
  public String loginError(Model model) {
    model.addAttribute("errorMessage", "Wrong username or password");
    return "login";
  }

  @PostMapping("accessDenied")
  public String postAccessDenied() {
    return "accessDenied";
  }

  @GetMapping("accessDenied")
  public String accessDenied() {
    return "accessDenied";
  }

  @GetMapping("pageNotFound")
  public String pageNotFound() {
    return "accessDenied";
  }

  @GetMapping("methodNotAllowedPage")
  public String methodNotAllowedPage() {
    return "methodNotAllowedPage";
  }

  @GetMapping("main")
  public String getMain() {
    return "main";
  }
}
