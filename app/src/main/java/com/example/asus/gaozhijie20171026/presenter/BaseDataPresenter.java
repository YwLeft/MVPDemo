package com.example.asus.gaozhijie20171026.presenter;

import android.content.Context;

import com.example.asus.gaozhijie20171026.view.IVew.IView;
import com.example.asus.gaozhijie20171026.view.MyApp;

/**
 * 创建时间  2017/10/26 15:01
 * 创建人
 * 类描述    present基类
 */
public class BaseDataPresenter<T extends IView> {

    protected T iView;


    public BaseDataPresenter(T iView) {
        this.iView = iView;
    }

    Context context(){
        if(iView != null && iView.context() != null){
            return iView.context();
        }
        return MyApp.getAppContext();
    }

}
