package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description CourseRepository
 * @Author xiong
 * @Date 2020/02/27 19:32
 * @Version 1.0
 */
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByTeacher(Teacher teacher);
    List<Course> findByStudents(Student student);

}
