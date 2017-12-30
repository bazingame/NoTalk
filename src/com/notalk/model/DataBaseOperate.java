package com.notalk.model;

import javax.xml.transform.Result;
import java.sql.*;

public class DataBaseOperate {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/notalk";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    public DataBaseOperate(){
        try{
            // 加载JDBC驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
//            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        DataBaseOperate dataBaseOperate = new DataBaseOperate();
//        dataBaseOperate.addNewUser(2016501308,"password","Howard",1,"2016-6-6","HeadImg");
//        dataBaseOperate.getUserInfo(2016501308);
        int res = dataBaseOperate.adviseUserInfo(2016501308,"pas11sword","Howard",1,"2016-6-6","HeadImg","lala","asdasd","asdasd");
        System.out.println(res);
    }

    /*
    * 注册新用户
    * */
    public int addNewUser(int sid,String password,String nickname,int sex,String birthday,String head_img) throws SQLException {
        String sql = "INSERT INTO user (sid,password,nickname,sex,birthday,head_img) VALUES (?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,sid);
        pstmt.setString(2,password);
        pstmt.setString(3,nickname);
        pstmt.setInt(4,sex);
        pstmt.setString(5,birthday);
        pstmt.setString(6,head_img);
        int res = pstmt.executeUpdate();
        return res;
    }

    /*
    * 获取个人信息
    * */
    public ResultSet getUserInfo(int sid) throws SQLException {
        String sql = "SELECT * FROM user WHERE sid = "+sid;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        return res;
    }

    /*
    * 修改个人信息
    * */
    public int adviseUserInfo(int sid,String password,String nickname,int sex,String birthday,String head_img,String signature,String group_list,String set_info) throws SQLException {
        String sql = "UPDATE user  SET password =?,nickname =?,sex =?,birthday =?,head_img =?,signature =?,group_list =?,set_info =? WHERE sid = "+sid;
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,password);
        pstmt.setString(2,nickname);
        pstmt.setInt(3,sex);
        pstmt.setString(4,birthday);
        pstmt.setString(5,head_img);
        pstmt.setString(6,signature);
        pstmt.setString(7,group_list);
        pstmt.setString(8,set_info);
        int res = pstmt.executeUpdate();
        return res;
    }

    /*
    *
    *
    * */

}
