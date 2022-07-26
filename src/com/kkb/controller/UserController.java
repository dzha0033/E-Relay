package com.kkb.controller;

import com.kkb.bean.*;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.mvc.ResponseBody;
import com.kkb.service.UserService;
import com.kkb.service.impl.UserServiceImpl;
import com.kkb.utils.DateFormatUtil;
import com.kkb.utils.JSONUtil;
import com.kkb.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    private final UserService userService = new UserServiceImpl();

    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Integer>> data = userService.console();
        Message msg = new Message();
        if (data.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/users/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        List<Users> list = userService.findAll(true, offset, pageNumber);
        List<BootStrapTableUsers> list2 = new ArrayList<>();
        for (Users u : list) {
            String createTime = DateFormatUtil.format(u.getCreateTime());
            String loginTime = u.getLoginTime() == null ? "未登入过" : DateFormatUtil.format(u.getLoginTime());
            BootStrapTableUsers u2 = new BootStrapTableUsers(u.getId(), u.getName(), u.getPhone(), u.getPassword(), u.getIdCard(), createTime, loginTime);
            list2.add(u2);
        }
        List<Map<String, Integer>> console = userService.console();
        Integer total = console.get(0).get("data1_size");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableUsers> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/users/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String IDCard = request.getParameter("IDCard");
        String password = request.getParameter("password");

        Users u = new Users(name, phone, password, IDCard);
        Message msg = new Message();
        boolean flag = false;
        try {
            flag = userService.insert(u);
        } catch (Exception e) {
            if (e.getMessage().endsWith("for key 'users.username'")) {
                msg.setResult("昵称重复");
            } else if (e.getMessage().endsWith("for key 'users.phone'")) {
                msg.setResult("电话号码重复");
            } else {
                msg.setResult("身份证重复");
            }
        } finally {
            if (flag) {
                //录入成功
                msg.setStatus(0);
                msg.setResult("录入成功!");
            } else {
                //录入失败
                msg.setStatus(-1);
            }
            String json = JSONUtil.toJSON(msg);
            return json;
        }
    }

    @ResponseBody("/users/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        Users u = userService.findByPhone(phone);
        Message msg = new Message();
        if (u == null) {
            msg.setStatus(-1);
            msg.setResult("用户不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/users/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String IDCard = request.getParameter("IDCard");
        String password = request.getParameter("password");
        Users newUsers = new Users();
        newUsers.setName(name);
        newUsers.setPhone(phone);
        newUsers.setIdCard(IDCard);
        newUsers.setPassword(password);
        Message msg = new Message();
        boolean flag = false;
        try {
            flag = userService.update(id, newUsers);
        } catch (Exception e) {
            if (e.getMessage().endsWith("for key 'users.username'")) {
                msg.setResult("昵称重复");
            } else if (e.getMessage().endsWith("for key 'users.phone'")) {
                msg.setResult("电话号码重复");
            } else {
                msg.setResult("身份证重复");
            }
        } finally {
            if (flag) {
                //录入成功
                msg.setStatus(0);
                msg.setResult("录入成功!");
            } else {
                //录入失败
                msg.setStatus(-1);
            }
        }
            String json = JSONUtil.toJSON(msg);
            return json;
    }

    @ResponseBody("/users/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        Message msg = new Message();
        boolean flag;
        flag = userService.delete(id);
        if (flag) {
            //录入成功
            msg.setStatus(0);
            msg.setResult("删除成功!");
        } else {
            //录入失败
            msg.setStatus(-1);
            msg.setResult("删除失败!");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
