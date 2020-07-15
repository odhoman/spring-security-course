package com.course.organizer.repository;

import com.course.organizer.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository
			extends CrudRepository<Course, Long> {

}
