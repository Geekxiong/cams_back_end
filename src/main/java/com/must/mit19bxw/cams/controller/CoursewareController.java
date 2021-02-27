package com.must.mit19bxw.cams.controller;

import com.alibaba.fastjson.JSONObject;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Courseware;
import com.must.mit19bxw.cams.service.CoursewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/api/courseware")
public class CoursewareController {
    private CoursewareService coursewareService;

    @Autowired
    public void setCoursewareService(CoursewareService coursewareService) {
        this.coursewareService = coursewareService;
    }


    @GetMapping("/list")
    public ResponseBean listByCourseId(@RequestParam("courseId") Integer courseId) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        List<Courseware> coursewares = coursewareService.getListByCourseId(courseId);
        responseBean.isSuccess();
        responseBean.setData(coursewares);
        return responseBean;
    }

    /**
     * 新增课件
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
        String filePath = requestData.getString("filePath");
        Integer courseId = requestData.getInteger("courseId");

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Courseware courseware = coursewareService.add(name, description, filePath, courseId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(courseware);
        return responseBean;
    }

    /**
     * 编辑课件
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
        String filePath = requestData.getString("filePath");
        Integer courseId = requestData.getInteger("courseId");

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Courseware courseware = coursewareService.edit(id, name, description, filePath, courseId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(courseware);
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
        Courseware courseware = coursewareService.getById(id);
        responseBean.isSuccess();
        responseBean.setData(courseware);
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
        coursewareService.deleteById(id, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }
}
