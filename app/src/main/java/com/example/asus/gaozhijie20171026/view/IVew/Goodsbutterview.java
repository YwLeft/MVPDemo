package com.example.asus.gaozhijie20171026.view.IVew;

import com.example.asus.gaozhijie20171026.mode.bean.GoodsDataBean;

/**
 * 创建时间  2017/10/26 14:55
 * 创建人
 * 类描述     定义请求接口
 */
public interface Goodsbutterview extends IView {
    //    获取数据成功
    void onGetDataSucceed(GoodsDataBean bean);
    //    获取数据失败
    void onGetDataFail(String e);

}
