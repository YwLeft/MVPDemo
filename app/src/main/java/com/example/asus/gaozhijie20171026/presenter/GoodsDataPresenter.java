package com.example.asus.gaozhijie20171026.presenter;

import com.example.asus.gaozhijie20171026.mode.bean.GoodsDataBean;
import com.example.asus.gaozhijie20171026.mode.bane.GoodsDataModes;
import com.example.asus.gaozhijie20171026.view.IVew.Goodsbutterview;

/**
 * 创建时间  2017/10/26 15:00
 * 创建人
 * 类描述     数据返回
 */
public class GoodsDataPresenter extends BaseDataPresenter<Goodsbutterview> {


    private final GoodsDataModes model;

    public GoodsDataPresenter(Goodsbutterview iView) {
        super(iView);

        model = new GoodsDataModes();
    }

    //    得到数据
    public void getData() {

        model.getData(new GoodsDataModes.Databack() {
            @Override
            public void setbutteck(GoodsDataBean bean) {
                iView.onGetDataSucceed(bean);
            }

            @Override
            public void setfile(String s) {
                iView.onGetDataFail(s.toString());
            }
        });



    }



}
