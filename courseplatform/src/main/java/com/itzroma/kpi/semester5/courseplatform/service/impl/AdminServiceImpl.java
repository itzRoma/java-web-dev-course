package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.model.Admin;
import com.itzroma.kpi.semester5.courseplatform.service.AdminService;

import java.util.logging.Logger;

public class AdminServiceImpl extends UserServiceImpl<Admin> implements AdminService {
    private static final Logger log = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl() {
        super(Admin.class);
    }
}
