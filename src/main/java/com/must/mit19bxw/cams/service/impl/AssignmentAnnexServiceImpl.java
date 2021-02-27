package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Assignment;
import com.must.mit19bxw.cams.entity.AssignmentAnnex;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.repository.AssignmentAnnexRepository;
import com.must.mit19bxw.cams.repository.AssignmentRepository;
import com.must.mit19bxw.cams.service.AssignmentAnnexService;
import com.must.mit19bxw.cams.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AssignmentAnnexServiceImpl implements AssignmentAnnexService {
    private AssignmentAnnexRepository assignmentAnnexRepository;
    private AssignmentRepository assignmentRepository;

    @Autowired
    public void setAssignmentAnnexRepository(AssignmentAnnexRepository assignmentAnnexRepository) {
        this.assignmentAnnexRepository = assignmentAnnexRepository;
    }

    @Autowired
    public void setAssignmentRepository(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public AssignmentAnnex add(String name, String savePath, Integer assignmentId, Integer teacherId) throws Exception {
        AssignmentAnnex assignmentAnnex = new AssignmentAnnex();
        assignmentAnnex.setName(name);
        assignmentAnnex.setSavePath(savePath);
        assignmentAnnex.setUploadTime(new Date());

        if(assignmentId!=null){
            Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
            if(assignment==null){
                throw new Exception("BACK.ASSIGNMENT.NOTFOUNF");
            }
            Course course = assignment.getCourse();
            if(!course.getTeacher().getId().equals(teacherId)){
                throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
            }
            assignmentAnnex.setAssignment(assignment);
        }
        assignmentAnnexRepository.save(assignmentAnnex);
        return assignmentAnnex;
    }

    @Override
    public void deleteById(Integer id, Integer teacherId) throws Exception {
        AssignmentAnnex assignmentAnnex = assignmentAnnexRepository.findById(id).orElse(null);
        if(assignmentAnnex!=null){
            Assignment assignment = assignmentAnnex.getAssignment();
            if(assignment!=null){
                Course course = assignment.getCourse();
                if(!course.getTeacher().getId().equals(teacherId)){
                    throw new Exception("BACK.COURSE.NOTBELONGTOYOU");
                }
            }
            assignmentAnnexRepository.delete(assignmentAnnex);
            OSSUtil.deleteFile(assignmentAnnex.getSavePath());
        }
    }
}
