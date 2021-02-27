package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Courseware;
import com.must.mit19bxw.cams.repository.CourseRepository;
import com.must.mit19bxw.cams.repository.CoursewareRepository;
import com.must.mit19bxw.cams.service.CoursewareService;
import com.must.mit19bxw.cams.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CoursewareServiceImpl implements CoursewareService {
    private CoursewareRepository coursewareRepository;
    private CourseRepository courseRepository;

    @Autowired
    public void setCoursewareRepository(CoursewareRepository coursewareRepository) {
        this.coursewareRepository = coursewareRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void deleteById(Integer id, Integer teacherId) throws Exception {
        Courseware courseware = coursewareRepository.findById(id).orElse(null);
        if(courseware==null){
            throw new Exception("BACK.COURSEWARE.NotFound");
        }
        if(!courseware.getCourse().getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        coursewareRepository.delete(courseware);
        OSSUtil.deleteFile(courseware.getSavePath());
    }

    @Override
    public Courseware getById(Integer id) throws Exception {
        Courseware courseware = coursewareRepository.findById(id).orElse(null);
        if(courseware==null){
            throw new Exception("BACK.COURSEWARE.NOTFOUND");
        }
        return courseware;
    }

    @Override
    public Courseware edit(Integer id, String name, String description, String filePath, Integer courseId, Integer teacherId) throws Exception {
        Courseware courseware = coursewareRepository.findById(id).orElse(null);
        if(courseware==null){
            throw new Exception("BACK.COURSEWARE.NOTFOUND");
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        courseware.setName(name);
        courseware.setDescription(description);
        courseware.setSavePath(filePath);
        courseware.setCourse(course);
        // TODO 课件排序
        coursewareRepository.save(courseware);
        return courseware;
    }

    @Override
    public Courseware add(String name, String description, String filePath, Integer courseId, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Courseware courseware = new Courseware();
        courseware.setName(name);
        courseware.setDescription(description);
        courseware.setSavePath(filePath);
        courseware.setCourse(course);
        courseware.setUploadTime(new Date());
        // TODO 课件排序

        coursewareRepository.save(courseware);
        return courseware;
    }

    @Override
    public List<Courseware> getListByCourseId(Integer courseId) throws Exception {
        return coursewareRepository.findByCourse_Id(courseId);
    }
}
