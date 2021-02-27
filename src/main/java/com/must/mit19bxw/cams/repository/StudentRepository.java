package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description StudentRepository
 * @Author xiong
 * @Date 2020/02/27 19:26
 * @Version 1.0
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByNumberAndPassword(String number, String password);
    Student findByNumber(String number);
}
