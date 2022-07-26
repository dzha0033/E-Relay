package com.kkb.service.impl;

import com.kkb.dao.BaseAdminDao;
import com.kkb.dao.impl.BaseAdminDaoImpl;
import com.kkb.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private static BaseAdminDao dao = new BaseAdminDaoImpl();
    @Override
    public void updateLoginTime(int userid, String ip) {
        dao.updateLoginTime(userid,ip);
    }

    @Override
    public int login(String username, String password) {
        return dao.login(username,password);
    }
}
