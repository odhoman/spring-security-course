package com.course.organizer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.apache.commons.lang3.CharEncoding;

@Configuration
public class ThymeleafConfiguration {

  @Bean
  @Description("Thymeleaf template resolver serving HTML 5")
  public ClassLoaderTemplateResolver emailTemplateResolver() {
    ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
    emailTemplateResolver.setPrefix("templates/");
    emailTemplateResolver.setSuffix(".html");
    emailTemplateResolver.setTemplateMode("HTML5");
    emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
    emailTemplateResolver.setOrder(1);
    return emailTemplateResolver;
  }
}
