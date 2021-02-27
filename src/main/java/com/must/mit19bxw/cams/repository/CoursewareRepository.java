package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Courseware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description Courseware
 * @Author xiong
 * @Date 2020/02/27 19:33
 * @Version 1.0
 */
public interface CoursewareRepository extends JpaRepository<Courseware, Integer> {
    List<Courseware> findByCourse_Id(Integer courseId);
}
