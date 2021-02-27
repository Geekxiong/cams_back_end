package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement add(String title, String description, Integer courseId, Integer teacherId) throws Exception;

    Announcement edit(Integer id, String title, String description, Integer courseId, Integer teacherId) throws Exception;

    Announcement getById(Integer id) throws Exception;

    List<Announcement> getListByTeacherId(Integer teacherId) throws Exception;

    List<Announcement> getListByStudentId(Integer studentId) throws Exception;

    void deleteById(Integer id, Integer teacherId) throws Exception;
}
