package com.must.mit19bxw.cams.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.must.mit19bxw.cams.entity.Assignment;
import com.must.mit19bxw.cams.entity.AssignmentAnnex;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.repository.AssignmentAnnexRepository;
import com.must.mit19bxw.cams.repository.AssignmentRepository;
import com.must.mit19bxw.cams.repository.CourseRepository;
import com.must.mit19bxw.cams.service.AssignmentService;
import com.must.mit19bxw.cams.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private AssignmentRepository assignmentRepository;
    private CourseRepository courseRepository;
    private AssignmentAnnexRepository assignmentAnnexRepository;

    @Autowired
    public void setAssignmentRepository(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setAssignmentAnnexRepository(AssignmentAnnexRepository assignmentAnnexRepository) {
        this.assignmentAnnexRepository = assignmentAnnexRepository;
    }

    @Override
    public List<Assignment> getListByCourseId(Integer courseId) {
        return assignmentRepository.findByCourse_Id(courseId);
    }

    @Override
    @Transactional
    public Assignment add(String name, String description, Date startTime, Date endTime, JSONArray annexList, Integer courseId, Integer teacherId) throws Exception {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course==null){
            throw new Exception("BACK.COURSE.NOTFOUND");
        }
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Assignment assignment = new Assignment();

        assignment.setName(name);
        assignment.setDescription(description);
        assignment.setStartTime(startTime);
        assignment.setEndTime(endTime);
        assignment.setCourse(course);
        // TODO 作业排序
//        assignment.setIndex(1);
        assignmentRepository.save(assignment);
        for (int i = 0; i < annexList.size(); i++) {
            Integer annexId = annexList.getInteger(i);
            AssignmentAnnex annex = assignmentAnnexRepository.findById(annexId).orElse(null);
            if(annex!=null){
                annex.setAssignment(assignment);
                assignmentAnnexRepository.save(annex);
            }
        }
        return assignment;
    }

    @Override
    public Assignment edit(Integer id, String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if(assignment==null){
            throw new Exception("BACK.ASSIGNMENT.NOTFOUND");
        }
        Course course = assignment.getCourse();
        if(!course.getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }

        assignment.setName(name);
        assignment.setDescription(description);
        assignment.setStartTime(startTime);
        assignment.setEndTime(endTime);
        // TODO 作业排序
//        assignment.setIndex(1);
        assignmentRepository.save(assignment);

        return assignment;
    }

    @Override
    public Assignment getById(Integer id) throws Exception {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if(assignment==null){
            throw new Exception("BACK.ASSIGNMENT.NOTFOUND");
        }
        return assignment;
    }

    @Override
    public void deleteById(Integer id, Integer teacherId) throws Exception {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if(assignment==null){
            throw new Exception("BACK.ASSIGNMENT.NotFound");
        }
        if(!assignment.getCourse().getTeacher().getId().equals(teacherId)){
            throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
        }
        Set<AssignmentAnnex> assignmentSet = assignment.getAssignmentAnnexes();
        // 删作业附件
        for (AssignmentAnnex annex: assignmentSet) {
            OSSUtil.deleteFile(annex.getSavePath());
            assignmentAnnexRepository.delete(annex);
        }
        // 再删作业
        assignmentRepository.delete(assignment);
    }
}
