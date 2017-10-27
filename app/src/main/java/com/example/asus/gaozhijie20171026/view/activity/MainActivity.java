package com.example.asus.gaozhijie20171026.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.asus.gaozhijie20171026.R;
import com.example.asus.gaozhijie20171026.mode.bean.GoodsDataBean;
import com.example.asus.gaozhijie20171026.presenter.GoodsDataPresenter;
import com.example.asus.gaozhijie20171026.view.IVew.Goodsbutterview;
import com.example.asus.gaozhijie20171026.view.adapter.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建时间  2017/10/26 15:24
 * 创建人
 * 类描述     主页面
 */

public class MainActivity extends AppCompatActivity implements Goodsbutterview {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    GoodsDataPresenter presenter;
    private List<GoodsDataBean.SongListBean> mlist = new ArrayList<>();
    private GoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //返回数据
        presenter = new GoodsDataPresenter(this);
        presenter.getData();
    }

    /**
     * 上下文
     * @return
     */
    @Override
    public Context context() {
        return this;
    }

    /**
     * 成功返回
     * @param bean
     */
    @Override
    public void onGetDataSucceed(GoodsDataBean bean) {
        mlist.addAll(bean.getSong_list());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initview();
            }
        });
    }

    /**
     * 失败返回
     * @param e
     */
    @Override
    public void onGetDataFail(String e) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化
     */
    private void initview() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodsAdapter(this,mlist);
        recyclerview.setAdapter(adapter);
        //item的点击事件
        adapter.setSetitemonclick(new GoodsAdapter.setitemonclick() {
            @Override
            public void setonitemhol(View view, int position) {
                Intent intent = new Intent(MainActivity.this,NextActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
