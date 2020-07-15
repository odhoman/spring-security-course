package com.course.organizer.repository;

import com.course.organizer.model.CourseUser;
import com.course.organizer.model.CourseUserDto;
import com.course.organizer.model.CourseUserDto2;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserRepository extends CrudRepository<CourseUser, Long> {

  public List<CourseUser> getAllUserCoursesByUserId(Long userId);

  @Query(value =
      "update COURSE_USER set status = :status "
          + "where user_Id = :userId and course_Id = :courseId", nativeQuery = true)
  public void saveCourseUserStatus(@Param("userId") Long userId, @Param("courseId") Long course,
      @Param("status") String status);

//  @Query(value =
//      "select * from COURSE_USER cu where cu.course_id IN :ids and :userId  "
//          + "where user_Id = :userId and course_Id = :courseId", nativeQuery = true)
//  public  List<CourseUser> getAllUserCoursesByUserIdOrCourseId(@Param("ids") List<Long> ids, @Param("userId")  Long userId);

    @Query(value =
      "select *  from course_user cu right join course c on cu.course_id = c.id "
          + "where cu.user_id = :userId or cu.user_id is null", nativeQuery = true)
  public  List<CourseUser> getAllUserNotUserCoursesByUserId(@Param("userId") Long userId);



  @Query(value =
      "select  cu.user_id as userId, cu.course_id as courseId,  c.desc as courseName, c.title as courseTitle, cu.status as status "+
      " from course_user cu right join course c on cu.course_id = c.id where cu.user_id = :userId or  cu.user_id is null", nativeQuery = true)
  public  List<CourseUserDto2> getAllUserNotUserCoursesByUserId2(@Param("userId") Long userId);



}
