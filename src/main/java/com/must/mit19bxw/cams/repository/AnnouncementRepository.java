package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Announcement;
import com.must.mit19bxw.cams.entity.Student;
import com.must.mit19bxw.cams.entity.Teacher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @Description AnnouncementRepository
 * @Author xiong
 * @Date 2020/03/02 17:03
 * @Version 1.0
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByCourse_Teacher_id(Integer teacherId);
    List<Announcement> findByCourse_Students(Student student, Sort sort);
}
