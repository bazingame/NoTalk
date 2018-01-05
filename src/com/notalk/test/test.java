package com.notalk.test;


import com.notalk.model.DataBaseOperate;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class test {
    static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    /**
     *每隔一段时间打印系统时间，互不影响的<br/>
     * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；<br/>
     * 也就是将在 initialDelay 后开始执行，然后在initialDelay+period 后执行，<br/>
     * 接着在 initialDelay + 2 * period 后执行，依此类推。
     */

    public static void main(String[] args) throws SQLException {
//        exec.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                System.out.println(System.currentTimeMillis());
//            }
//        }, 1, 1, TimeUnit.MILLISECONDS);
//        Date date =  new Date();
//        System.out.println(date.getTime());
        DataBaseOperate db = new DataBaseOperate();
//        db.sendfriendMsg(20123122,5132,"123123","2017-02-03");
//        db.addNewUser(2016501333,"asdasdas","asdasd",1,"2017-2-20 12:20:20","asdas","asdas");
//        System.out.println(db.getFriendsSidList(2016501308));
        db.setOnline(2016501308);
//        db.setOffline(2016501308);
    }
}