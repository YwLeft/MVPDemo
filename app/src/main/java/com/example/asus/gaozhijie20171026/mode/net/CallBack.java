package com.example.asus.gaozhijie20171026.mode.net;

/**
 * 创建时间  2017/10/26 14:48
 * 创建人
 * 类描述     ok泛型
 */
public interface CallBack<T> {
    void success(T t);
    void faild(int positon,String str);
}
