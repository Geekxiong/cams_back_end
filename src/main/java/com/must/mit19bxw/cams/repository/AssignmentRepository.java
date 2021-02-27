package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description AssigenmentRepository
 * @Author xiong
 * @Date 2020/02/27 19:36
 * @Version 1.0
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Integer > {
    List<Assignment> findByCourse_Id(Integer courseId);
}
