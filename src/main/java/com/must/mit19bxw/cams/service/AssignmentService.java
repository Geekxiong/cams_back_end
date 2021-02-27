package com.must.mit19bxw.cams.service;

import com.alibaba.fastjson.JSONArray;
import com.must.mit19bxw.cams.entity.Assignment;

import java.util.Date;
import java.util.List;

public interface AssignmentService {
    List<Assignment> getListByCourseId(Integer courseId);

    Assignment add(String name, String description, Date startTime, Date endTime, JSONArray annexList, Integer courseId, Integer teacherId) throws Exception;

    Assignment edit(Integer id, String name, String description, Date startTime, Date endTime, Integer teacherId) throws Exception;

    Assignment getById(Integer id) throws Exception;

    void deleteById(Integer id, Integer teacherId) throws Exception;
}
