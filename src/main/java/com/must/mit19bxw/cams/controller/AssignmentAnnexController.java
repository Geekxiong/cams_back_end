package com.must.mit19bxw.cams.controller;

import com.alibaba.fastjson.JSONObject;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.AssignmentAnnex;
import com.must.mit19bxw.cams.service.AssignmentAnnexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/assignmentAnnex")
public class AssignmentAnnexController {
    private AssignmentAnnexService assignmentAnnexService;

    @Autowired
    public void setAssignmentAnnexService(AssignmentAnnexService assignmentAnnexService) {
        this.assignmentAnnexService = assignmentAnnexService;
    }

    @PostMapping("/add")
    public ResponseBean add(@RequestBody JSONObject requestData,
                            HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        String name = requestData.getString("name");
        String savePath = requestData.getString("savePath");
        Integer assignmentId = requestData.getInteger("assignmentId");

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        AssignmentAnnex assignmentAnnex = assignmentAnnexService.add(name, savePath, assignmentId, teacherId);
        responseBean.isSuccess();
        responseBean.setData(assignmentAnnex);
        return responseBean;
    }

    @GetMapping("/delete")
    public ResponseBean delete(@RequestParam("id") Integer id,
                               HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        assignmentAnnexService.deleteById(id, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }



}
