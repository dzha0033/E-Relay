package com.kkb.service;

public interface AdminService {
     void updateLoginTime(int userid,String ip);

     int login(String username, String password);

}
