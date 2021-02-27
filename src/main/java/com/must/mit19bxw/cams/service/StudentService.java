package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Student;

public interface StudentService {
    Student login(String account, String password) throws Exception;
}
