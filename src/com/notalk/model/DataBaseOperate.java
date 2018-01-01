package com.notalk.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.xml.internal.bind.v2.TODO;
import sun.security.util.Resources_sv;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Gson gson = new Gson();
    public DataBaseOperate() {
        try {
            // 加载JDBC驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
//            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {

    }

    /*
    * 注册新用户
    * */
    public int addNewUser(int sid, String password, String nickname, int sex, String birthday, String head_img,String sinature) throws SQLException {
        String sql = "INSERT INTO user (sid,password,nickname,sex,birthday,head_img,signature) VALUES (?,?,?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, sid);
        pstmt.setString(2, password);
        pstmt.setString(3, nickname);
        pstmt.setInt(4, sex);
        pstmt.setString(5, birthday);
        pstmt.setString(6, head_img);
        pstmt.setString(7, sinature);
        int res = pstmt.executeUpdate();
        return res;
    }

    /*
    * 获取个人信息
    * */
    public ResultSet getUserInfo(int sid) throws SQLException {
        String sql = "SELECT * FROM user WHERE sid = " + sid;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        return res;
    }

    /*
    * 修改个人信息
    * */
    public int adviseUserInfo(int sid, String password, String nickname, int sex, String birthday, String head_img, String signature, String set_info) throws SQLException {
        String sql = "UPDATE user  SET password =?,nickname =?,sex =?,birthday =?,head_img =?,signature =?,set_info =? WHERE sid = " + sid;
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setString(2, nickname);
        pstmt.setInt(3, sex);
        pstmt.setString(4, birthday);
        pstmt.setString(5, head_img);
        pstmt.setString(6, signature);
        pstmt.setString(7, set_info);
        int res = pstmt.executeUpdate();
        return res;
    }

    /*
    * 上线
    * */
    public int setOnline(int sid) throws SQLException {
        String sql = "UPDATE user SET status = 1 WHERE sid = "+sid;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    * 下线
    * */
    public int setOffline(int sid) throws SQLException {
        String sql = "UPDATE user SET status = 0 WHERE sid = "+sid;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    *添加好友
    * */
    public int addFriend(int my_sid,int friend_sid,String friend_nickname,int group_id) throws SQLException {
        String sql = "INSERT INTO friends (my_sid,friend_sid,friend_nickname,group_id) VALUES ("+my_sid+","+friend_sid+",'"+friend_nickname+"',"+group_id+")";
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    * 删除好友
    * */
    public int deleteFriend(int my_sid,int friend_sid) throws SQLException {
        String sql = "DELETE FROM friends WHERE my_sid = "+my_sid+" AND friend_sid = "+friend_sid;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    *修改好友昵称
    * */
    public int reviseFriendNickname(int my_sid,int friend_sid,String nickname) throws SQLException {
        String sql = "UPDATE friends SET nickname = ? AND mysid = "+my_sid+" AND friend_sid"+friend_sid;
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,nickname);
        int res = pstmt.executeUpdate();
        return res;
    }

    /*
    * 获取好友列表(分组输出)
    * */
    public String getFriendsList(int my_sid) throws SQLException {
        String sql = "SELECT * FROM friends WHERE my_sid = "+my_sid+" ORDER BY group_id";
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        //获取好友分组列表(json格式，需要先解析)
        String groupList = this.getFriendsGroupList(my_sid);
        Map<Integer,String> groupListMap = gson.fromJson(groupList,new TypeToken<HashMap<Integer,String>>(){}.getType());
        List<Map<String,List>> friendsList = new ArrayList<>();
        for(int i = 1;i<=groupListMap.size();i++){
            Map<String,List> friendsListNameAndData = new HashMap<String,List>();
            //根据组别名去遍历分组好友
            List<Map> thisGroupList = new ArrayList<Map>();
            while (res.next()){
                if(res.getInt("group_id")==i){
                    Map<String,String> thisMan = new HashMap<String,String>();
                    thisMan.put("friend_sid",res.getInt("friend_sid")+"");
                    thisMan.put("friend_nickname",res.getString("friend_nickname"));
                    thisMan.put("group_id",res.getInt("group_id")+"");
                    thisGroupList.add(thisMan);
                }
            }
            res.first();
            List<String> groupName = new ArrayList<>();
            groupName.add(groupListMap.get(i));
            friendsListNameAndData.put("friend_list",thisGroupList);
            friendsListNameAndData.put("group_name",groupName);
            friendsList.add(friendsListNameAndData);
        }
        return gson.toJson(friendsList);
    }

    /*
    * 获取好友分组列表
    * */
    public String getFriendsGroupList(int sid) throws SQLException {
        String sql = "SELECT friends_group_list FROM user WHERE sid = "+sid;
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.next();
        String res = resultSet.getString("friends_group_list");
        return res;
    }

    /*
    * 获取好友信息
    * */
    public ResultSet getFriendInfo(int friend_sid) throws SQLException {
        String sql = "SELECT * FROM user WHERE sid = "+friend_sid;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        return res;
    }

    /*
    * 发送好友消息
    * */
    public int sendfriendMsg(int from_sid,int to_sid,String content,String time) throws SQLException {
        String sql = "INSERT INTO p2p_messages (from_sid,to_sid,content,time) VALUES (?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,from_sid);
        pstmt.setInt(2,to_sid);
        pstmt.setString(3,content);
        pstmt.setString(4,time);
        int res = pstmt.executeUpdate(sql);
        return res;
    }

    /*
    * 获取p2p聊天记录
    * */
    public String getMsgRecord(int from_sid,int to_sid) throws SQLException {
        String sql = "SELECT content,time FROM p2p_messages WHERE ( from_sid = "+from_sid+" AND to_sid = "+to_sid+" ) OR ( from_sid = "+to_sid+" AND to_sid = "+from_sid+") ORDER BY time";
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        List<HashMap<String,String>> recordList= new ArrayList<HashMap<String,String>>();
        while (resultSet.next()){
            HashMap<String,String> singleRecord = new HashMap<String,String>();
            singleRecord.put("content",resultSet.getString("content"));
            singleRecord.put("time",resultSet.getString("time"));
            recordList.add(singleRecord);
        }
        String recordListJson = gson.toJson(recordList);
        return recordListJson;
    }

    /*
    * 创建群组
    * */
    public int creatGroup(int creator,String users_list,String creat_time,String group_name) throws SQLException {
        String sql = "INSERT INTO group_list (creator,users_list,creat_time,group_name) VALUES ('"+creator+"','"+users_list+"','"+creat_time+"','"+group_name+"')";
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    * 删除群
    * */
    public int deleteGroup(int groupId) throws SQLException {
        String sql = "DELETE FROM group_list WHERE Id = "+groupId;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return res;
    }

    /*
    * 加入群
    * */
    public int joinGroup(int user_sid,int group_sid) throws SQLException {
        List<Integer> users_list_old = this.getGroupMemberList(group_sid);
        users_list_old.add(user_sid);
        String users_list_new = gson.toJson(users_list_old);
        String sql = "UPDATE group_list SET users_list = '"+users_list_new+"' WHERE Id = "+group_sid;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return  res;
    }

    /*
    * 退出群
    * */
    public int quitGroup(int user_sid,int group_sid) throws SQLException {
        List<Integer> users_list_old = this.getGroupMemberList(group_sid);
        for (Integer sid: users_list_old) {
            if(sid.equals(user_sid)){
                users_list_old.remove(sid);
                break;      //不break的话会报ConcurrentModificationException错误
            }
        }
        String users_list_new = gson.toJson(users_list_old);
        String sql = "UPDATE group_list SET users_list = '"+users_list_new+"' WHERE Id = "+group_sid;
        stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
        return  res;
    }

    /*
    * 获取群列表
    * */
    public ResultSet getGroupList(int sid) throws SQLException {
        String sql = "SELECT * FROM user WHERE sid = "+sid;
        stmt = conn.createStatement();
        ResultSet group_list = stmt.executeQuery(sql);
        group_list.next();
        String group_list_json = group_list.getString("group_list");
        return group_list;
    }

    /*
    * 获取群成员(仅账号)
    * */
    public List<Integer> getGroupMemberList(int groupId) throws SQLException {
        String sql = "SELECT users_list FROM group_list WHERE Id = "+groupId;
        stmt = conn.createStatement();
        ResultSet users_list = stmt.executeQuery(sql);
        users_list.next();
        String users_list_json = users_list.getString("users_list");
        List<Integer> user_list_list = gson.fromJson(users_list_json, new TypeToken<List<Integer>>() {}.getType());
        return user_list_list;
    }

    /*
    * 获取群成员(详细信息)
    * */
    public List<ResultSet> getGroupMemberListDetail(int groupId) throws SQLException {
        List<Integer> memberList = this.getGroupMemberList(groupId);
        List<ResultSet> friendInfo = new ArrayList<ResultSet>();
        for (Integer id:memberList) {
            friendInfo.add(this.getFriendInfo(id));
        }
        return friendInfo;
    }

    /*
    * 获取群信息
    * */
    public ResultSet getGroupInfo(int groupId) throws SQLException {
        String sql = "SELECT * FROM group_list WHERE Id = "+groupId;
        stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        return res;
    }


    /*
    * 发送群信息
    * */
    public int sendGroupMsg(int from_sid,int to_group_sid,String content,String time) throws SQLException{
        String sql = "INSERT INTO p2g_message (from_sid,to_group_sid,content,time) VALUES (?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,from_sid);
        pstmt.setInt(2,to_group_sid);
        pstmt.setString(3,content);
        pstmt.setString(4,time);
        int res = pstmt.executeUpdate();
        return 0;
    }

    /*
    * 获取p2g聊天记录
    * */
    public String getGroupMsgRecord(int from_sid,int group_sid) throws SQLException {
        String sql = "SELECT content,time FROM p2g_messages WHERE  from_sid = "+from_sid+" AND to_group_sid = "+group_sid+"  ORDER BY time";
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        List<HashMap<String,String>> recordList= new ArrayList<HashMap<String,String>>();
        while (resultSet.next()){
            HashMap<String,String> singleGroupRecord = new HashMap<String,String>();
            singleGroupRecord.put("content",resultSet.getString("content"));
            singleGroupRecord.put("time",resultSet.getString("time"));
            recordList.add(singleGroupRecord);
        }
        String recordListJson = gson.toJson(recordList);
        return recordListJson;
    }




}