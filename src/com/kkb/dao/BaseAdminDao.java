package com.kkb.dao;

import java.util.Date;

public interface BaseAdminDao {

    void updateLoginTime(int userid, String ip);

    int login(String username,String password);

}
