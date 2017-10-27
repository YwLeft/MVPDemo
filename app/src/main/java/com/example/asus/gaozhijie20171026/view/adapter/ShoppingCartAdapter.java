package com.example.asus.gaozhijie20171026.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.gaozhijie20171026.R;
import com.example.asus.gaozhijie20171026.view.AmountView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间  2017/10/26 15:34
 * 创建人
 * 类描述     购物车适配器
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {


    private Context context;
    private List<HashMap<String, String>> List;
    private Map<String, Integer> pitch;
    private RefreshPriceInterface Interface;

    public ShoppingCartAdapter(Context context, List<HashMap<String, String>> goodsList) {
        this.context = context;
        this.List = goodsList;
        //初始化map集合
        pitch = new HashMap<>();
        //遍历集合得到里面的唯一id
        for (int i = 0; i < List.size(); i++) {
            pitch.put(List.get(i).get("id"), 0);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (pitch.get(List.get(position).get("id")) == 0)
            holder.checkBox.setChecked(false);
        else holder.checkBox.setChecked(true);
        HashMap<String, String> map = List.get(position);
        holder.name.setText(map.get("name"));
        holder.num.setText(map.get("count"));
        holder.type.setText(map.get("type"));
        holder.price.setText("单价：￥ " + (Double.valueOf(map.get("price")) * Integer.valueOf(map.get("count"))));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index = position;
                if (((CheckBox) view).isChecked()) pitch.put(List.get(index).get("id"), 1);
                else pitch.put(List.get(index).get("id"), 0);
                Interface.refreshPrice(pitch);
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index = position;
                if (Integer.valueOf(List.get(index).get("count")) == 1) {
                    Toast.makeText(context, "用户最小数量为1", Toast.LENGTH_SHORT).show();
                } else if (Integer.valueOf(List.get(index).get("count")) <= 1) {
                    //可提示是否删除该商品,确定就remove,否则就保留1
                    String deID = List.get(index).get("id");
                    List.remove(index);
                    pitch.remove(deID);
                } else {
                    List.get(index).put("count", (Integer.valueOf(List.get(index).get("count")) - 1) + "");
                }
                notifyDataSetChanged();
                Interface.refreshPrice(pitch);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index = position;
                List.get(index).put("count", (Integer.valueOf(List.get(index).get("count")) + 1) + "");
                if (Integer.valueOf(List.get(index).get("count")) > 15) {
                    //15为库存可选择上限
                    Toast.makeText(context, "已达库存上限~", Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyDataSetChanged();
                Interface.refreshPrice(pitch);
            }
        });
    }

    public Map<String, Integer> getPitchOnMap() {
        return pitch;
    }

    public void setPitchOnMap(Map<String, Integer> pitchOnMap) {
        this.pitch = new HashMap<>();
        this.pitch.putAll(pitchOnMap);
    }

    public interface RefreshPriceInterface {
        void refreshPrice(Map<String, Integer> pitchOnMap);
    }

    public void setRefreshPriceInterface(RefreshPriceInterface refreshPriceInterface) {
        this.Interface = refreshPriceInterface;
    }


    @Override
    public int getItemCount() {
        if (List != null) {
            return List.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView name,price,type;
        AmountView amountView;
        private EditText num;
        private Button reduce;
        private Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.check_box);
            icon=(ImageView)itemView.findViewById(R.id.iv_adapter_list_pic);
            name=(TextView)itemView.findViewById(R.id.tv_goods_name);
            price=(TextView)itemView.findViewById(R.id.tv_goods_price);
            type=(TextView)itemView.findViewById(R.id.tv_type_size);
            amountView = (AmountView) itemView.findViewById(R.id.amount_view);
            num= (EditText) amountView.findViewById(R.id.etAmount);
            reduce= (Button) amountView.findViewById(R.id.btnDecrease);
            add= (Button) amountView.findViewById(R.id.btnIncrease);

        }
    }
}
