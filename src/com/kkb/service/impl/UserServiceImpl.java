package com.kkb.service.impl;

import com.kkb.bean.Users;
import com.kkb.dao.BaseUserDao;
import com.kkb.dao.impl.BaseUserDaoImpl;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private BaseUserDao userDao = new BaseUserDaoImpl();
    @Override
    public List<Map<String, Integer>> console() {
        return userDao.console();
    }

    @Override
    public List<Users> findAll(boolean limit, int offset, int pageNumber) {
        return userDao.findAll(limit,offset,pageNumber);
    }

    @Override
    public Users findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public Boolean insert(Users u) throws SQLException {
        return userDao.insert(u);
    }

    @Override
    public Boolean update(int id, Users u) throws SQLException{
        return userDao.update(id, u);
    }

    @Override
    public Boolean delete(int id) {
        return userDao.delete(id);
    }
}
