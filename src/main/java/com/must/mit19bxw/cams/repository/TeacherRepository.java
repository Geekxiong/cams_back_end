package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TeacherRepository
 * @Author xiong
 * @Date 2020/02/27 19:29
 * @Version 1.0
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByNumberAndPassword(String number, String password);
}
