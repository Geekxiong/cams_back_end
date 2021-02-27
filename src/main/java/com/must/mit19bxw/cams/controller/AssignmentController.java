package com.must.mit19bxw.cams.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.Assignment;
import com.must.mit19bxw.cams.entity.Courseware;
import com.must.mit19bxw.cams.service.AssignmentService;
import com.must.mit19bxw.cams.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Description AssignmentController
 * @Author xiong
 * @Date 2020/02/27 19:57
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/assignment")
public class AssignmentController {
    AssignmentService assignmentService;

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/list")
    public ResponseBean listByCourseId(@RequestParam("courseId") Integer courseId) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        List<Assignment> assignments = assignmentService.getListByCourseId(courseId);
        responseBean.isSuccess();
        responseBean.setData(assignments);
        return responseBean;
    }

    /**
     * 新增作业
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

        String name = requestData.getString("name");
        String description = requestData.getString("description");
        String startTimeStr = requestData.getString("startTime");
        Date startTime = CommonUtil.dateStr2Date(startTimeStr);
        String endTimeStr = requestData.getString("endTime");
        Date endTime = CommonUtil.dateStr2Date(endTimeStr);
        Integer courseId = requestData.getInteger("courseId");
        JSONArray annexList = requestData.getJSONArray("annexList");

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Assignment assignment = assignmentService.add(name, description, startTime, endTime, annexList, courseId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(assignment);
        return responseBean;
    }

    /**
     * 编辑作业
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
        String name = requestData.getString("name");
        String description = requestData.getString("description");
        String startTimeStr = requestData.getString("startTime");
        Date startTime = CommonUtil.dateStr2Date(startTimeStr);
        String endTimeStr = requestData.getString("endTime");
        Date endTime = CommonUtil.dateStr2Date(endTimeStr);

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Assignment assignment = assignmentService.edit(id, name, description, startTime, endTime, teacherId);
        responseBean.isSuccess();
        responseBean.setData(assignment);
        return responseBean;
    }

    /**
     * 通过id获取课件信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public ResponseBean get(@RequestParam("id") Integer id) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        Assignment assignment = assignmentService.getById(id);
        responseBean.isSuccess();
        responseBean.setData(assignment);
        return responseBean;
    }

    /**
     * 通过id删除课件
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
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        assignmentService.deleteById(id, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }
}
