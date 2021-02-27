package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.repository.StudentRepository;
import com.must.mit19bxw.cams.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student login(String account, String password) throws Exception {
        Student student = studentRepository.findByNumberAndPassword(account, password);
        if(student==null){
            throw new Exception("AccountOrPasswordWrong");
        }
        return student;
    }
}
