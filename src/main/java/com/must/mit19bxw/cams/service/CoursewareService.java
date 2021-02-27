package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Courseware;

import java.util.List;

public interface CoursewareService {
    void deleteById(Integer id, Integer teacherId) throws Exception;

    Courseware getById(Integer id) throws Exception;

    Courseware edit(Integer id, String name, String description, String filePath, Integer courseId, Integer teacherId) throws Exception;

    Courseware add(String name, String description, String filePath, Integer courseId, Integer teacherId) throws Exception;

    List<Courseware> getListByCourseId(Integer courseId) throws Exception;
}
