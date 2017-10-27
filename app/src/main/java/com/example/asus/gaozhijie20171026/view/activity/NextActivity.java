package com.example.asus.gaozhijie20171026.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.gaozhijie20171026.R;
import com.example.asus.gaozhijie20171026.mode.bean.ShoppingBean;
import com.example.asus.gaozhijie20171026.view.adapter.ShoppingCartAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建时间  2017/10/26 15:24
 * 创建人
 * 类描述     购物车
 */

public class NextActivity extends AppCompatActivity implements ShoppingCartAdapter.RefreshPriceInterface, View.OnClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.all_chekbox)
    CheckBox cb_check_all;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_go_to_pay)
    TextView tv_go_to_pay;

    private ShoppingCartAdapter adapter;

    private double totalPrice = 0.00;
    private int totalCount = 0;
    private List<HashMap<String, String>> goodsList = new ArrayList<>();
    private List<ShoppingBean> mlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);
        ButterKnife.bind(this);
        loadData();
        initView();

    }

    /**
     * 模拟5条数据
     */
    private void loadData() {

        for (int i = 1; i < 6; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", i+"");
            map.put("name", "第"+i+"件商品");
            map.put("type", (i + 20) + "码");
            map.put("price", 50+i+"");
            map.put("count", 1+"");
            goodsList.add(map);

        }
    }

    /**
     * 点击事件加适配器
     */
    private void initView() {
        tv_go_to_pay.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        cb_check_all.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingCartAdapter(this, goodsList);
        adapter.setRefreshPriceInterface(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    //控制价格展示
    private void priceControl(Map<String, Integer> pitchOnMap) {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < goodsList.size(); i++) {
            if (pitchOnMap.get(goodsList.get(i).get("id")) == 1) {
                totalCount = totalCount + Integer.valueOf(goodsList.get(i).get("count"));
                double goodsPrice = Integer.valueOf(goodsList.get(i).get("count")) * Double.valueOf(goodsList.get(i).get("price"));
                totalPrice = totalPrice + goodsPrice;
            }
        }
        tv_total_price.setText("￥ " + totalPrice);
        tv_go_to_pay.setText("付款(" + totalCount + ")");
    }


    public void refreshPrice(Map<String, Integer> pitchOnMap) {
        priceControl(pitchOnMap);
    }

    /***
     * 判断各种点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:
                AllTheSelected();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount <= 0) {
                    Toast.makeText(this, "请选择商品~", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "没钱~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                if (totalCount <= 0) {
                    Toast.makeText(this, "请选择删除的商品~", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkDelete(adapter.getPitchOnMap());
                break;
        }
    }

    //删除操作
    private void checkDelete(Map<String, Integer> map) {
        List<HashMap<String, String>> waitDeleteList = new ArrayList<>();
        Map<String, Integer> waitDeleteMap = new HashMap<>();
        for (int i = 0; i < goodsList.size(); i++) {
            if (map.get(goodsList.get(i).get("id")) == 1) {
                waitDeleteList.add(goodsList.get(i));
                waitDeleteMap.put(goodsList.get(i).get("id"), map.get(goodsList.get(i).get("id")));
            }
        }
        goodsList.removeAll(waitDeleteList);
        map.remove(waitDeleteMap);
        priceControl(map);
        adapter.notifyDataSetChanged();
    }

    //全选或反选
    private void AllTheSelected() {
        Map<String, Integer> map = adapter.getPitchOnMap();
        boolean isCheck = false;
        boolean isUnCheck = false;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (Integer.valueOf(entry.getValue().toString()) == 1) isCheck = true;
            else isUnCheck = true;
        }
        if (isCheck == true && isUnCheck == false) {//已经全选,做反选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 0);
            }
            cb_check_all.setChecked(false);
        } else if (isCheck == true && isUnCheck == true) {//部分选择,做全选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 1);
            }
            cb_check_all.setChecked(true);
        } else if (isCheck == false && isUnCheck == true) {//一个没选,做全选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 1);
            }
            cb_check_all.setChecked(true);
        }
        priceControl(map);
        adapter.setPitchOnMap(map);
        adapter.notifyDataSetChanged();
    }
}
