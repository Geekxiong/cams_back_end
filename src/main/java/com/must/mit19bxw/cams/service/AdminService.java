package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.Admin;

public interface AdminService {
    Admin login(String account, String password) throws Exception;
}
