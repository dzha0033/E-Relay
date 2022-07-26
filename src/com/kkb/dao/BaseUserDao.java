package com.kkb.dao;

import com.kkb.bean.Express;
import com.kkb.bean.Users;
import com.kkb.exception.DuplicateCodeException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BaseUserDao {
    List<Map<String,Integer>> console();

    List<Users> findAll(boolean limit, int offset, int pageNumber);

    Users findByPhone(String Phone);
    Boolean insert(Users u) throws SQLException;

    Boolean update(int id, Users u) throws SQLException;
    Boolean delete(int id);

}
