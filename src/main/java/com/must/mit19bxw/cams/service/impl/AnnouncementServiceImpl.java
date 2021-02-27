package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.entity.Teacher;
import com.must.mit19bxw.cams.repository.AnnouncementRepository;
import com.must.mit19bxw.cams.repository.CourseRepository;
import com.must.mit19bxw.cams.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.alibaba.fastjson.JSONValidator.Type.Array;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private AnnouncementRepository announcementRepository;

    private CourseRepository courseRepository;

    @Autowired
    public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Announcement add(String title, String description, Integer courseId, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("CourseNotFound");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("CourseNotBelongToYou");
        }
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setCourse(course);
        announcement.setEditTime(new Date());
        announcementRepository.save(announcement);
        return announcement;
    }

    @Override
    public Announcement edit(Integer id, String title, String description, Integer courseId, Integer teacherId) throws Exception {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if(announcement==null){
            throw new Exception("AnnouncementNotFound");
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("CourseNotFound");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("CourseNotBelongToYou");
        }
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setCourse(course);
        announcement.setEditTime(new Date());
        announcementRepository.save(announcement);
        return announcement;
    }

    @Override
    public Announcement getById(Integer id) throws Exception {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if(announcement==null){
            throw new Exception("AnnouncementNotFound");
        }
        announcement.getCourse().getTeacher().setPassword(null);
        return announcement;
    }

    @Override
    public List<Announcement> getListByTeacherId(Integer teacherId) throws Exception {
        List<Announcement> announcements = announcementRepository.findByCourse_Teacher_id(teacherId);
        for (Announcement announcement: announcements) {
            announcement.getCourse().getTeacher().setPassword(null);
        }
        return announcements;
    }

    @Override
    public List<Announcement> getListByStudentId(Integer studentId) throws Exception {
        Student student = new Student();
        student.setId(studentId);
        List<Announcement> announcements = announcementRepository.findByCourse_Students(student, Sort.by("editTime").descending());
        for (Announcement announcement: announcements) {
            announcement.getCourse().getTeacher().setPassword(null);
        }
        return announcements;
    }

    @Override
    public void deleteById(Integer id, Integer teacherId) throws Exception {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if(announcement==null){
            throw new Exception("AnnouncementNotFound");
        }
        if(!announcement.getCourse().getTeacher().getId().equals(teacherId)){
            throw new Exception("CourseNotBelongToYou");
        }
        announcementRepository.delete(announcement);
    }
}
