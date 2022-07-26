package com.kkb.controller;

import com.kkb.mvc.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController {
    @ResponseBody("/admin/logout.do")
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //清除session
        req.getSession().invalidate();
        //跳转页面
        //resp.sendRedirect("/login.jsp");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("<script>alert('退出成功');top.location.href='/admin/login.html';</script>");
    }
}
