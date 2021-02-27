package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Teacher;

public interface TeacherService {
    Teacher login(String account, String password) throws Exception;
}
