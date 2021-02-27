package com.must.mit19bxw.cams.util;

import com.must.mit19bxw.cams.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description excel操作工具类
 * @Author Geekxiong
 * @Date 2018-12-15 11:04
 */

public class ExcelUtil {
    /**
     * 判断文件是否为excel 2003类型（xls）
     * @param filePath
     * @return
     */
    private static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断文件是否为excel 2007类型（xlsx）
     * @param filePath
     * @return
     */
    private static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 从excel中导出TeacherList
     * @param file 文件路径
     * @return
     */
    public static List<String> getStudentNumberList(File file) {
        Workbook wb;
        List<String> studentNumberList = new ArrayList();
        try {
            if (isExcel2007(file.getPath())) {
                wb = new XSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(file));
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        //System.out.println(sheet.getLastRowNum());
        for(int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以1开始
            String studentNumber;
            try {
                studentNumber = row.getCell(0).getStringCellValue();
            }catch (Exception e){
                continue;
            }
            studentNumberList.add(studentNumber);
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentNumberList;
    }


    public static void main(String args[]){
//        File f = new File("C:\\Users\\Geekxiong\\Desktop\\教师账号导入模板.xlsx");
//        List<Teacher> teachersList = importUserData(f);
//        for(Teacher teacher: teachersList){
//            System.out.println(teacher.getName());
//        }

//        File f2 = new File("C:\\Users\\Geekxiong\\Desktop\\选修课项目\\课程信息导入模板.xlsx");
//        List<CoursesDetailEntity> coursesList =  importCoursesData(f2);
//        for(CoursesDetailEntity courses: coursesList){
//            System.out.println(courses.getName());
//            System.out.println(courses.getNameEn());
//            System.out.println(courses.getCode());
//            System.out.println(courses.getClassHour());
//            System.out.println(courses.getCredit());
//            System.out.println(courses.getTeachingObject());
//            System.out.println(courses.getTeachingMaterial());
//            System.out.println(courses.getIntroduction());
//            System.out.println(courses.getTeachingProgram());
//            System.out.println(courses.getOpen());
//
//            for(Teacher t : courses.getTeachers()){
//                System.out.print(t.getJobNum()+" ");
//            }
//            System.out.println();
//        }
    }
}