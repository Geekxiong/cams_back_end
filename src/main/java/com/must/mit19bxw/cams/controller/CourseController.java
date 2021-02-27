package com.must.mit19bxw.cams.controller;

import com.alibaba.fastjson.JSONObject;
import com.must.mit19bxw.cams.bean.ResponseBean;
import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Course;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.service.CourseService;
import com.must.mit19bxw.cams.util.CommonUtil;
import com.must.mit19bxw.cams.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Description CourseController
 * @Author xiong
 * @Date 2020/02/27 19:56
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/course")
public class CourseController {

    private CourseService courseService;
    private String rootPath;
    private String tmpPath;


    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Value("${system.rootPath}")
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Value("${system.tmpPath}")
    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    /**
     * 获取当前教师所上的课程，
     * 权限010
     * @param request
     * @return
     */
    @GetMapping("/list/teacher")
    public ResponseBean teacher(HttpServletRequest request){
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        List<Course> courses = courseService.getByTeacherId(teacherId);
        for (Course course: courses) {
            course.getTeacher().setPassword("");
        }
        responseBean.setData(courses);
        responseBean.isSuccess();
        return responseBean;
    }

    /**
     * 获取当前学生所选修课程，
     * 权限100
     * @param request
     * @return
     */
    @GetMapping("/list/student")
    public ResponseBean student(HttpServletRequest request){
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("userId");
        List<Course> courses = courseService.getByStudentId(studentId);
        for (Course course: courses) {
            course.getTeacher().setPassword("");
        }
        responseBean.setData(courses);
        responseBean.isSuccess();
        return responseBean;
    }

    /**
     * 通过id获取公告信息
     * 权限 000
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public ResponseBean get(@RequestParam("id") Integer id) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        Course course = courseService.getById(id);
        responseBean.isSuccess();
        responseBean.setData(course);
        return responseBean;
    }

    /**
     * 新增课程
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
        String endTimeStr = requestData.getString("endTime");
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");

        Course course = courseService.add(name, description, CommonUtil.dateStr2DateYMD(startTimeStr), CommonUtil.dateStr2DateYMD(endTimeStr),teacherId);
        responseBean.isSuccess();
        responseBean.setData(course);
        return responseBean;
    }

    /**
     * 编辑课程
     * 权限010
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
        String endTimeStr = requestData.getString("endTime");
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Course course = courseService.edit(id, name, description, CommonUtil.dateStr2DateYMD(startTimeStr), CommonUtil.dateStr2DateYMD(endTimeStr),teacherId);
        responseBean.isSuccess();
        responseBean.setData(course);
        return responseBean;
    }

    /**
     * 通过id删除课程
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
        courseService.deleteById(id, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }

    /**
     * 获取选修课程的学生名单
     * 权限 010
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/students")
    public ResponseBean getStudentList(@RequestParam("id") Integer id,
                                       HttpServletRequest request) throws Exception{
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        List<Student> students = courseService.getStudentListById(id, teacherId);
        responseBean.isSuccess();
        responseBean.setData(students);
        return responseBean;
    }

    /**
     * 将学生从选修名单中移除
     * 权限 010
     * @param courseId
     * @param studentId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/student/remove")
    public ResponseBean removeStudent(@RequestParam("cid") Integer courseId,
                                       @RequestParam("sid") Integer studentId,
                                       HttpServletRequest request) throws Exception{
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        courseService.deleteStudentById(courseId, studentId, teacherId);
        responseBean.isSuccess();
        return responseBean;
    }

    /**
     * 通过学号给课程添加选修学生
     * 权限：010
     * @param courseId
     * @param studentNumber
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/student/add")
    public ResponseBean removeStudent(@RequestParam("cid") Integer courseId,
                                      @RequestParam("studentNumber") String studentNumber,
                                      HttpServletRequest request) throws Exception{
        ResponseBean responseBean = ResponseBean.newResponse();
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Student student = courseService.addStudentById(courseId, studentNumber, teacherId);
        responseBean.isSuccess();
        responseBean.setData(student);
        return responseBean;
    }

    @PostMapping("/student/import")
    public ResponseBean removeStudent(@RequestParam("file") MultipartFile file,
                                      @RequestParam("cid") Integer courseId,
                                      HttpServletRequest request) throws Exception {
        ResponseBean responseBean = ResponseBean.newResponse();
        String oldFilename = file.getOriginalFilename();
        String fileSuffix = oldFilename.substring(oldFilename.lastIndexOf("."));
        if(!fileSuffix.equals(".xlsx")){
            throw new Exception("BACK.UPLOAD.FILETYPEERROR");
        }
        String newFileName = UUID.randomUUID().toString() + fileSuffix;
        String savePath = rootPath + tmpPath + newFileName;
        File dest = new File(savePath);
        file.transferTo(dest);
        List<String> studentNumberList = ExcelUtil.getStudentNumberList(dest);
        dest.deleteOnExit();

        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("userId");
        Integer m = courseService.importStudentById(courseId, studentNumberList, teacherId);
        Integer n = studentNumberList.size();
        responseBean.isSuccess();
        responseBean.setData(new HashMap<String, Integer>(){{
            put("totalNumber", n);
            put("successNumber", m);
        }});
        return responseBean;
    }
}
