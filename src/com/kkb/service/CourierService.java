package com.kkb.service;

import com.kkb.bean.Courier;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CourierService {
    List<Map<String,Integer>> console();

    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    Courier findByPhone(String phone);
    Boolean insert(Courier u) throws SQLException;

    Boolean update(int id, Courier u) throws SQLException;
    Boolean delete(int id);
}
