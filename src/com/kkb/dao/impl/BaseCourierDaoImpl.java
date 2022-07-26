package com.kkb.dao.impl;

import com.kkb.bean.Courier;
import com.kkb.dao.BaseCourierDao;
import com.kkb.utils.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCourierDaoImpl implements BaseCourierDao {

    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(CREATETIME)=TO_DAYS(NOW()) OR NULL) data1_day from COURIERS;";
    public static final String SQL_FIND_ALL = "SELECT * FROM COURIERS";
    public static final String SQL_FIND_LIMIT = "SELECT * FROM COURIERS LIMIT ?,?";
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM COURIERS WHERE PHONE=?";
    public static final String SQL_INSERT = "INSERT INTO COURIERS (USERNAME,PHONE,PASSWORD,IDCARD,CREATETIME,LOGINTIME) VALUES(?,?,?,?,NOW(),null)";
    public static final String SQL_UPDATE = "UPDATE COURIERS SET USERNAME=?,PHONE=?,PASSWORD=?,IDCARD=? WHERE ID=?";
    public static final String SQL_DELETE = "DELETE FROM COURIERS WHERE ID=?";

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
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Courier> users = new ArrayList<>();
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
                int pieces = resultSet.getInt("pieces");
                Timestamp createTime = resultSet.getTimestamp("createtime");
                Timestamp loginTime = resultSet.getTimestamp("logintime");
                Courier user = new Courier(id, username, phone, password, idCard,pieces, createTime, loginTime);
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
    public Courier findByPhone(String phone) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Courier user = null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_PHONE);
            state.setString(1, phone);
            resultSet = state.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String idCard = resultSet.getString("IDCard");
                int pieces = resultSet.getInt("pieces");
                Timestamp createTime = resultSet.getTimestamp("createtime");
                Timestamp loginTime = resultSet.getTimestamp("logintime");
                user = new Courier(id, username, phone, password, idCard,pieces, createTime, loginTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, state, resultSet);
        }
        return user;
    }

    @Override
    public Boolean insert(Courier u) throws SQLException {
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
    public Boolean update(int id, Courier u) throws SQLException{
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
