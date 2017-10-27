package com.example.asus.gaozhijie20171026.mode.bane;

import com.example.asus.gaozhijie20171026.mode.bean.GoodsDataBean;
import com.example.asus.gaozhijie20171026.mode.net.CallBack;
import com.example.asus.gaozhijie20171026.mode.net.MyOkhttps;
import com.example.asus.gaozhijie20171026.view.MyApp;

/**
 * 创建时间  2017/10/26 14:52
 * 创建人
 * 类描述    请求数据
 */
public class GoodsDataModes {
    //数据接口
    private String path = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";

    private MyOkhttps okhttps;

    public GoodsDataModes() {
        okhttps = MyApp.getOkhttp();
    }
    public void getData(final Databack databack) {
        okhttps.getdata(path, new CallBack() {
            @Override
            public void success(Object o) {
                GoodsDataBean bean = (GoodsDataBean) o;
                databack.setbutteck(bean);
            }

            @Override
            public void faild(int positon, String str) {
                databack.setfile(str.toString());
            }
        }, GoodsDataBean.class);


    }

    /**
     * 定义两个接口，一个成功，一个失败
     */
    public interface Databack{
        void setbutteck(GoodsDataBean bean);
        void setfile(String s);
    }
}
