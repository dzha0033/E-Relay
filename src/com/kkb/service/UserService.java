package com.kkb.service;

import com.kkb.bean.Users;
import com.kkb.exception.DuplicateCodeException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<Map<String,Integer>> console();

    List<Users> findAll(boolean limit, int offset, int pageNumber);

    Users findByPhone(String phone);
    Boolean insert(Users u) throws SQLException;

    Boolean update(int id, Users u) throws SQLException;
    Boolean delete(int id);

}
