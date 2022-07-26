package com.kkb.dao.impl;

import com.kkb.bean.Express;
import com.kkb.bean.Users;
import com.kkb.dao.BaseUserDao;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.utils.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseUserDaoImpl implements BaseUserDao {
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(CREATETIME)=TO_DAYS(NOW()) OR NULL) data1_day from Users;";
    public static final String SQL_FIND_ALL = "SELECT * FROM USERS";
    public static final String SQL_FIND_LIMIT = "SELECT * FROM USERS LIMIT ?,?";
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM USERS WHERE PHONE=?";
    public static final String SQL_INSERT = "INSERT INTO USERS (USERNAME,PHONE,PASSWORD,IDCARD,CREATETIME,LOGINTIME) VALUES(?,?,?,?,NOW(),null)";
    public static final String SQL_UPDATE = "UPDATE USERS SET USERNAME=?,PHONE=?,PASSWORD=?,IDCARD=? WHERE ID=?";
    public static final String SQL_DELETE = "DELETE FROM USERS WHERE ID=?";

    @Override
    public List<Map<String, Integer>> console() {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Map<String, Integer>> result = new ArrayList<>();
        try {
            state = connection.prepareStatement(SQL_CONSOLE);
            resultSet = state.executeQuery();
            if (resultSet.next()) {
                int data1_size = resultSet.getInt("data1_size");
                int data1_day = resultSet.getInt("data1_day");
                Map data1 = new HashMap();
                data1.put("data1_size", data1_size);
                data1.put("data1_day", data1_day);
                result.add(data1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, state, resultSet);
        }
        return result;
    }

    @Override
    public List<Users> findAll(boolean limit, int offset, int pageNumber) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Users> users = new ArrayList<>();
        try {
            if (limit) {
                state = connection.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1, offset);
                state.setInt(2, pageNumber);

            } else {
                state = connection.prepareStatement(SQL_FIND_ALL);
            }
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                String idCard = resultSet.getString("IDCard");
                Timestamp createTime = resultSet.getTimestamp("createtime");
                Timestamp loginTime = resultSet.getTimestamp("logintime");
                Users user = new Users(id, username, phone, password, idCard, createTime, loginTime);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, state, resultSet);
        }
        return users;
    }

    @Override
    public Users findByPhone(String phone) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Users user = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_PHONE);
            state.setString(1, phone);
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String idCard = resultSet.getString("IDCard");
                Timestamp createTime = resultSet.getTimestamp("createtime");
                Timestamp loginTime = resultSet.getTimestamp("logintime");
                user = new Users(id, username, phone, password, idCard, createTime, loginTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, state, resultSet);
        }
        return user;
    }

    @Override
    public Boolean insert(Users u) throws SQLException {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_INSERT);
            state.setString(1, u.getName());
            state.setString(2, u.getPhone());
            state.setString(3, u.getPassword());
            state.setString(4, u.getIdCard());
            return state.executeUpdate() > 0 ? true : false;
        } finally {
            //5.    释放资源
            DruidUtil.close(connection, state, null);
        }
    }

    @Override
    public Boolean update(int id, Users u) throws SQLException{
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_UPDATE);
            state.setString(1, u.getName());
            state.setString(2, u.getPhone());
            state.setString(3, u.getPassword());
            state.setString(4, u.getIdCard());
            state.setInt(5, id);
            return state.executeUpdate() > 0 ? true : false;
        } finally {
            DruidUtil.close(connection, state, null);
        }
    }

    @Override
    public Boolean delete(int id) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_DELETE);
            state.setInt(1, id);
            return state.executeUpdate() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, state, null);
        }

        return false;
    }
}
