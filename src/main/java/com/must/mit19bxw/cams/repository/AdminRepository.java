package com.must.mit19bxw.cams.repository;

import com.must.mit19bxw.cams.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description AdminRepository
 * @Author xiong
 * @Date 2020/02/27 19:28
 * @Version 1.0
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAccountAndPassword(String account, String password);
}
