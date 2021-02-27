package com.must.mit19bxw.cams.controller;

import com.alibaba.fastjson.JSONObject;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description AnnouncementController
 * @Author xiong
 * @Date 2020/03/02 17:04
 * @Version 1.0
 */

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    AnnouncementService announcementService;


    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    /**
     * 新增公告
     * 权限 010
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/list/teacher")
    public ResponseBean listByTeacher(HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        List<Announcement> announcements = announcementService.getListByTeacherId(teacherId);
        responseBean.isSuccess();
        responseBean.setData(announcements);
        return responseBean;
    }

    /**
     * 新增公告
     * 权限 010
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/list/student")
    public ResponseBean listByStudent(HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("userId");
        List<Announcement> announcements = announcementService.getListByStudentId(studentId);
        responseBean.isSuccess();
        responseBean.setData(announcements);
        return responseBean;
    }

    /**
     * 新增公告
     * 权限 010
     * @param requestData
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public ResponseBean add(@RequestBody JSONObject requestData,
                            HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        String title = requestData.getString("title");
        String description = requestData.getString("description");
        Integer courseId = requestData.getInteger("courseId");
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Announcement announcement = announcementService.add(title, description, courseId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(announcement);
        return responseBean;
    }

    /**
     * 新增公告
     * 权限 010
     * @param requestData
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public ResponseBean edit(@RequestBody JSONObject requestData,
                            HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        Integer id = requestData.getInteger("id");
        String title = requestData.getString("title");
        String description = requestData.getString("description");
        Integer courseId = requestData.getInteger("courseId");
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Announcement announcement = announcementService.edit(id, title, description, courseId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(announcement);
        return responseBean;
    }

    /**
     * 通过id获取公告信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public ResponseBean get(@RequestParam("id") Integer id) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        Announcement announcement = announcementService.getById(id);
        responseBean.isSuccess();
        responseBean.setData(announcement);
        return responseBean;
    }

    /**
     * 通过id删除公告
     * 权限010
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/delete")
    public ResponseBean delete(@RequestParam("id") Integer id,
                               HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        Announcement announcement = announcementService.getById(id);
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        announcementService.deleteById(id, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }




}
