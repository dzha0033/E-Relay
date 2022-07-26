package com.kkb.service.impl;

import com.kkb.bean.Courier;
import com.kkb.bean.Users;
import com.kkb.dao.BaseCourierDao;
import com.kkb.dao.BaseUserDao;
import com.kkb.dao.impl.BaseCourierDaoImpl;
import com.kkb.dao.impl.BaseUserDaoImpl;
import com.kkb.service.CourierService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CourierServiceImpl implements CourierService {
    private BaseCourierDao courierDao = new BaseCourierDaoImpl();
    @Override
    public List<Map<String, Integer>> console() {
        return courierDao.console();
    }

    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return courierDao.findAll(limit,offset,pageNumber);
    }

    @Override
    public Courier findByPhone(String phone) {
        return courierDao.findByPhone(phone);
    }

    @Override
    public Boolean insert(Courier u) throws SQLException {
        return courierDao.insert(u);
    }

    @Override
    public Boolean update(int id, Courier u) throws SQLException{
        return courierDao.update(id, u);
    }

    @Override
    public Boolean delete(int id) {
        return courierDao.delete(id);
    }
}
