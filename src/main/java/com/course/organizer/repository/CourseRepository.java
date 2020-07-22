package com.course.organizer.repository;

import com.course.organizer.model.Course;
import com.course.organizer.model.UserCourseDto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository
			extends CrudRepository<Course, Long> {

	@Query(value =
			"select c.id as courseId, c.desc as courseDescription, "
					+ "c.title as courseTitle, c.status as courseStatus from course c", nativeQuery = true)
	List<UserCourseDto> getAllCourses();

}
