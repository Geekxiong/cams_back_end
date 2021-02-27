package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Teacher;
import com.must.mit19bxw.cams.repository.TeacherRepository;
import com.must.mit19bxw.cams.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher login(String account, String password) throws Exception {
        Teacher teacher = teacherRepository.findByNumberAndPassword(account, password);
        if (teacher==null){
            throw new Exception("AccountOrPasswordWrong");
        }
        return teacher;
    }
}
