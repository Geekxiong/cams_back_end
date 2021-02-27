package com.must.mit19bxw.cams.controller;

import com.must.mit19bxw.cams.bean.OSSTempLicense;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.Admin;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.entity.Teacher;
import com.must.mit19bxw.cams.service.AdminService;
import com.must.mit19bxw.cams.service.StudentService;
import com.must.mit19bxw.cams.service.TeacherService;
import com.must.mit19bxw.cams.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CommonController {
    private StudentService studentService;
    private TeacherService teacherService;
    private AdminService adminService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 登录接口，权限000
     * @param requestData
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseBean login(@RequestBody Map<String, String> requestData,
                              HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        String account = requestData.get("account");
        String password = requestData.get("password");
        String userType = requestData.get("userType");
        HttpSession session = request.getSession();
        if(userType.equals("student")){
            Student student = studentService.login(account, password);
            student.setPassword(null);
            session.setAttribute("userType", "student");
            session.setAttribute("userId", student.getId());
            responseBean.isSuccess();
            responseBean.setData(student);
        }else if (userType.equals("teacher")) {
            Teacher teacher = teacherService.login(account, password);
            teacher.setPassword(null);
            session.setAttribute("userType", "teacher");
            session.setAttribute("userId", teacher.getId());
            responseBean.isSuccess();
            responseBean.setData(teacher);
        }else if (userType.equals("admin")) {
            Admin admin = adminService.login(account, password);
            admin.setPassword(null);
            session.setAttribute("userType", "student");
            session.setAttribute("userId", admin.getId());
            responseBean.isSuccess();
            responseBean.setData(admin);
        }else {
            throw new Exception("User Type is Wrong!");
        }
        return responseBean;
    }

    /**
     * 获取OSS访问权限的临时TOKEN
     * 权限 110
     * @param authType
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/oss/auth")
    public ResponseBean getOSSAuth(@RequestParam("type")String authType,
                                   HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        String userType = (String)session.getAttribute("userType");
        Integer userId = (Integer)session.getAttribute("userId");
        String attrName;
        if(authType.equals("GET")){
            attrName = "getTempLicense";
        }else if(authType.equals("PUT")){
            attrName = "putTempLicense";
        }else {
            throw new Exception("Auth Type is Wrong!");
        }
        OSSTempLicense tempLicense = (OSSTempLicense) session.getAttribute(attrName);
        if(!OSSUtil.checkTempLicenseAlive(tempLicense)){
            tempLicense = OSSUtil.getTemporaryLicense(userType, authType, userId);
            session.setAttribute(attrName, tempLicense);
        }
        responseBean.setData(tempLicense);
        responseBean.isSuccess();
        return responseBean;
    }




}
