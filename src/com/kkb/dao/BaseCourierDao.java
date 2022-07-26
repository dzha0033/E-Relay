package com.kkb.dao;

import com.kkb.bean.Courier;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BaseCourierDao {
    List<Map<String, Integer>> console();

    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    Courier findByPhone(String Phone);

    Boolean insert(Courier c) throws SQLException;

    Boolean update(int id, Courier u) throws SQLException;

    Boolean delete(int id);
}
