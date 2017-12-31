package com.notalk.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.notalk.model.DataBaseOperate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class test {
    public static void main(String[] args) throws SQLException {
//        Gson gson = new GsonBuilder().create();
//        gson.toJson("Hello", System.out);
//        gson.toJson(123, System.out);
//        Gson gson = new Gson();
//        int i = gson.fromJson("100",int.class);
//        String jsonNumber = gson.toJson(100);       // 100
//        String jsonBoolean = gson.toJson(false);    // false
//        String jsonString = gson.toJson("String"); //"String"
//        System.out.println(jsonBoolean+jsonNumber+jsonString);


        DataBaseOperate DB = new DataBaseOperate();
//        int res = DB.creatGroup(2016501308,"[201650130,201610918]","2017-07-07 11:11:11","Our");
//        System.out.println(res);
//        DB.deleteGroup(4);
//        DB.getGroupList(2016501308);
//        DB.getGroupMemberListDetail(5);
//        DB.joinGroup(2016501555,5);
//        System.out.println(DB.quitGroup(2016501555,5));
//        String res =  DB.getMsgRecord(2016501308,2016190918);
//        String res =  DB.getGroupMsgRecord(2016501308,5);
//        int res = DB.adviseUserInfo(2016501308,"admin","FHY",0,"2016-02-09","img_addr","签名l哇","testinfp");
//        ResultSet resultSet = DB.getUserInfo(2016501308);
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(""));
//        }
//        DB.addFriend(2016501308,2016501334,"GONGJJ",5);
//        System.out.println(res);

//        ResultSet res =  DB.getFriendsList(2016501308);
//        while (res.next()){
//            System.out.println(res.getString("friend_nickname"));
//
//        }
           String res =   DB.getFriendsList(2016501308);
           System.out.println(res);

//        Iterator<Map.Entry<String, List>> entries = res.entrySet().iterator();
//
//        while (entries.hasNext()) {
//            Map.Entry<String , List> entry = entries.next();
//            System.out.println("Key = " + entry.getKey() );
////            Map<String,Map> map= new HashMap<String,Map>();
//            List list = entry.getValue();
//            for(Map map : list){
//
//            }
//        }

    }
}