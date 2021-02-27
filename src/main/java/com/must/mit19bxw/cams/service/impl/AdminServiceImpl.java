package com.must.mit19bxw.cams.service.impl;

import com.must.mit19bxw.cams.entity.Admin;
import com.must.mit19bxw.cams.repository.AdminRepository;
import com.must.mit19bxw.cams.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin login(String account, String password) throws Exception {
        Admin admin = adminRepository.findByAccountAndPassword(account, password);
        if(admin == null){
            throw new Exception("AccountOrPasswordWrong");
        }
        return admin;
    }
}
