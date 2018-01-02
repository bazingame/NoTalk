package com.notalk.model;

import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SocketHolder {

    public static ConcurrentMap<String, SocketWrapper> listSocketWrap = new ConcurrentHashMap<String, SocketWrapper>();

    /*
    * 返回集合的键名
    * */
    public static Set<String> keySet() {
        return listSocketWrap.keySet();
    }

    /*
    * 获取线程记录
    * */
    public static SocketWrapper get(String key) {
        return listSocketWrap.get(key);
    }

    /*
    * 添加记录
    * */
    public static void put(String key, SocketWrapper value) {

    }

    /*
    * 移除记录
    * */
    public static SocketWrapper remove(String key) {
        //TODO
        return listSocketWrap.remove(key);
    }

    /*
    *清空记录
    * */
    public static void clear() {
        listSocketWrap.clear();
    }

    /*
    *刷新记录
    * */
    public static void flushClientStatus(String key, boolean flag) {

    }


}
