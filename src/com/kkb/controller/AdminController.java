package com.kkb.controller;

import com.kkb.bean.Message;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.AdminService;
import com.kkb.service.impl.AdminServiceImpl;
import com.kkb.utils.JSONUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminController {
    AdminService adminService = new AdminServiceImpl();
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        int id = 0;
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        id = adminService.login(userName,password);
        Message msg = null;
        if(id==0){
            msg = new Message(-1,"登陆失败");
        }else {
            req.getSession().setAttribute("username",userName);
            Cookie cookie = new Cookie("username",userName);
            resp.addCookie(cookie);
            String ip = req.getRemoteAddr();
            adminService.updateLoginTime(id,ip);
            msg = new Message(0,"登录成功");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
