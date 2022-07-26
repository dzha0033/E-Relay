package com.kkb.dao.impl;

import com.kkb.dao.BaseAdminDao;
import com.kkb.utils.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseAdminDaoImpl implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "update eadmin set logintime = now(),loginip = ? where id = ?";
    private static final String SQL_LOGIN = "Select id from eadmin where username = ?and password = ?";
    @Override
    public void updateLoginTime(int userid, String ip) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement statement = null;
        try {
           statement = connection.prepareStatement(SQL_UPDATE_LOGIN_TIME);
           statement.setString(1,ip);
           statement.setInt(2,userid);
           statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,statement,null);
        }
    }

    @Override
    public int login(String username, String password) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(SQL_LOGIN);
            statement.setString(1,username);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                result = resultSet.getInt("id");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,statement,null);
        }
        return result;
    }
}
