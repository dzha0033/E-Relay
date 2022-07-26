package com.kkb.dao.impl;

import com.kkb.bean.Express;
import com.kkb.dao.BaseExpressDao;
import com.kkb.exception.DuplicateCodeException;
import com.kkb.utils.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseExpressDaoImpl implements BaseExpressDao {
    //用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day,COUNT(STATUS=0 OR NULL) data2_size,COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day FROM EXPRESS";
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    //通过取件码查询快递信息
    public static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE=?";
    //通过快递单号查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER=?";
    //通过录入人手机号查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM EXPRESS WHERE SYSPHONE=?";
    //通过用户手机号查询用户所有快递
    public static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM EXPRESS WHERE USERPHONE=?";
    //录入快递
    public static final String SQL_INSERT = "INSERT INTO EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    //快递修改
    public static final String SQL_UPDATE = "UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    //快递的状态码改变（取件）
    public static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE=?";
    //快递的删除
    public static final String SQL_DELETE = "DELETE FROM EXPRESS WHERE ID=?";
    @Override
    public List<Map<String, Integer>> console() {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Map<String, Integer>> result = new ArrayList<>();
        try {
            state = connection.prepareStatement(SQL_CONSOLE);
            resultSet = state.executeQuery();
            if (resultSet.next()){
                int data1_size = resultSet.getInt("data1_size");
                int data1_day = resultSet.getInt("data1_day");
                int data2_size = resultSet.getInt("data2_size");
                int data2_day = resultSet.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                result.add(data1);
                result.add(data2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }

        return result;
    }

    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Express> expresses = new ArrayList<>();
        try {
            if(limit){
                state = connection.prepareStatement(SQL_FIND_LIMIT);
                state.setInt(1,offset);
                state.setInt(2,pageNumber);

            }else{
                state = connection.prepareStatement(SQL_FIND_ALL);
            }
            resultSet = state.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                expresses.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }
        return expresses;
    }

    @Override
    public Express findByNumber(String number) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Express express= null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_NUMBER);
            state.setString(1,number);
            resultSet = state.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }
        return express;
    }

    @Override
    public Express findByCode(String code) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        Express express= null;
        try {
            state = connection.prepareStatement(SQL_FIND_BY_CODE);
            state.setString(1,code);
            resultSet = state.executeQuery();
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String number = resultSet.getString("number");
            String userPhone = resultSet.getString("userPhone");
            String company = resultSet.getString("company");
            Timestamp inTime = resultSet.getTimestamp("inTime");
            Timestamp outTime = resultSet.getTimestamp("outTime");
            int status = resultSet.getInt("status");
            String sysPhone = resultSet.getString("sysPhone");
            express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }
        return express;
    }

    @Override
    public List<Express> findByUserPhone(String userPhone) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Express> expresses= new ArrayList<>();
        try {
            state = connection.prepareStatement(SQL_FIND_BY_USERPHONE);
            state.setString(1,userPhone);
            resultSet = state.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                expresses.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }
        return expresses;
    }

    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet resultSet = null;
        List<Express> expresses= new ArrayList<>();
        try {
            state = connection.prepareStatement(SQL_FIND_BY_SYSPHONE);
            resultSet = state.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("inTime");
                Timestamp outTime = resultSet.getTimestamp("outTime");
                int status = resultSet.getInt("status");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                expresses.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection,state,resultSet);
        }
        return expresses;
    }

    @Override
    public Boolean insert(Express e) throws DuplicateCodeException{
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_INSERT);
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getUserPhone());
            state.setString(4,e.getCompany());
            state.setString(5,e.getCode());
            state.setString(6,e.getSysPhone());
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {

            if(e1.getMessage().endsWith("for key 'code'")){
                //是因为取件码重复,而出现了异常
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.    释放资源
            DruidUtil.close(connection,state,null);
        }
        return false;
    }

    @Override
    public Boolean update(int id, Express e) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(SQL_UPDATE);
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getCompany());
            state.setInt(4,e.getStatus());
            state.setInt(5,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,state,null);
        }
        return false;
    }

    @Override
    public Boolean updateStatus(String code) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            state.setString(1,code);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    @Override
    public Boolean delete(int id) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1,id);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
