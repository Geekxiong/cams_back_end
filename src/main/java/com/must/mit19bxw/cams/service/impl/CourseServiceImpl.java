package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.entity.Teacher;
import com.must.mit19bxw.cams.repository.CourseRepository;
import com.must.mit19bxw.cams.repository.StudentRepository;
import com.must.mit19bxw.cams.repository.TeacherRepository;
import com.must.mit19bxw.cams.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Course> getByTeacherId(Integer teacherId) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        List<Course> courses = courseRepository.findByTeacher(teacher);
        return courses;
    }

    @Override
    public List<Course> getByStudentId(Integer studentId) {
        Student student = new Student();
        student.setId(studentId);
        List<Course> courses = courseRepository.findByStudents(student);
        return courses;
    }

    @Override
    public Course getById(Integer id) throws Exception {
        Course course = courseRepository.findById(id).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        return course;
    }

    @Override
    public void deleteById(Integer id, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(id).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        courseRepository.delete(course);
    }

    @Override
    public Course edit(Integer id, String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(id).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        course.setName(name);
        course.setDescription(description);
        course.setStartTime(startTime);
        course.setEndTime(endTime);
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course add(String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        Course course = new Course();
        course.setTeacher(teacher);
        course.setName(name);
        course.setDescription(description);
        course.setStartTime(startTime);
        course.setEndTime(endTime);
        courseRepository.save(course);
        return course;
    }

    @Override
    public List<Student> getStudentListById(Integer id, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(id).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        List<Student> students = new ArrayList<>();
        Set<Student> studentSet = course.getStudents();
        for (Student student: studentSet) {
            student.setPassword(null);
            students.add(student);
        }
        return students;
    }

    @Override
    public void deleteStudentById(Integer courseId, Integer studentId, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student==null){
            throw new Exception("BACK.STUDENT.NOTFOUND");
        }
        Set<Course> courses = student.getCourses();
        courses.remove(course);
        studentRepository.save(student);
    }

    @Override
    public Student addStudentById(Integer courseId, String studentNumber, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Student student = studentRepository.findByNumber(studentNumber);
        if(student==null){
            throw new Exception("BACK.STUDENT.NOTFOUND");
        }
        Set<Course> courses = student.getCourses();
        courses.add(course);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Integer importStudentById(Integer courseId, List<String> studentNumberList, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Integer successNum = 0;
        for (String studentNumber: studentNumberList) {
            Student student = studentRepository.findByNumber(studentNumber);
            if(student==null){
                continue;
            }
            Set<Course> courses = student.getCourses();
            courses.add(course);
            studentRepository.save(student);
            successNum++;
        }
        return successNum;
    }
}
