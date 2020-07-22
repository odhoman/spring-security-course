package com.course.organizer.repository;

import com.course.organizer.model.UserCourse;
import com.course.organizer.model.UserCourseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends CrudRepository<UserCourse, Long> {

  public List<UserCourse> getAllUserCoursesByUserId(Long userId);

  @Query(value =
      "update COURSE_USER set status = :status "
          + "where user_Id = :userId and course_Id = :courseId", nativeQuery = true)
  void saveCourseUserStatus(@Param("userId") Long userId, @Param("courseId") Long course,
      @Param("status") String status);

//  @Query(value =
//      "select  c.id as courseId, c.desc as courseDescription, c.title as courseTitle, "
//          + "cu.status as userCourseStatus, c.status as courseStatus "+
//      " from course_user cu right join course c on cu.course_id = c.id "
//          + "where cu.user_id = :userId or cu.user_id is null", nativeQuery = true)

  @Query(value = "select  cse.id as courseId, cse.desc as courseDescription, "
                   + "cse.title as courseTitle, myc.userCourseStatus as userCourseStatus, "
                   + "cse.status as courseStatus "
                + "from "
                    + "(select  c.id as courseId, cu.status as userCourseStatus "
                        + "from course_user cu right join course c on cu.course_id = c.id "
                        + " where cu.user_id = :userId) myc "
                 + "right join "
                    + "course cse "
                + "on myc.courseId = cse.id order by cse.id asc", nativeQuery = true)
  List<UserCourseDto> getAllUserAndNotUserCoursesByUserId(@Param("userId") Long userId);

  Optional<UserCourse> findByCourseIdAndUserId(Long courseId, Long userId);

}
