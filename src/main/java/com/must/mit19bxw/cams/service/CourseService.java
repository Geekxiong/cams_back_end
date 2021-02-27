package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Student;

import java.util.Date;
import java.util.List;

public interface CourseService {
    List<Course> getByTeacherId(Integer teacherId);
    List<Course> getByStudentId(Integer studentId);

    Course getById(Integer id) throws Exception;

    void deleteById(Integer id, Integer teacherId) throws Exception;

    Course edit(Integer id, String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception;

    Course add(String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception;

    List<Student> getStudentListById(Integer id, Integer teacherId) throws Exception;

    void deleteStudentById(Integer courseId, Integer studentId, Integer teacherId) throws Exception;

    Student addStudentById(Integer courseId, String studentNumber, Integer teacherId) throws Exception;

    Integer importStudentById(Integer courseId, List<String> studentNumberList, Integer teacherId) throws Exception;
}
