package com.example.asus.gaozhijie20171026.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.gaozhijie20171026.R;
import com.example.asus.gaozhijie20171026.mode.bean.GoodsDataBean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建时间  2017/10/26 15:04
 * 创建人
 * 类描述    首页适配器
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {


    Context context;
    List<GoodsDataBean.SongListBean> mlist;
    ImageLoader imageloader;
    DisplayImageOptions options;


    public GoodsAdapter(Context context, List<GoodsDataBean.SongListBean> mlist) {
        this.mlist = mlist;
        this.context = context;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);

        //将configuration配置到imageloader中
        imageloader = ImageLoader.getInstance();
        imageloader.init(configuration);

        //自定义ImageLoader缓冲目录
        File flie = new File(Environment.getExternalStorageDirectory(), "bawei");
        if (!flie.exists()) {
            flie.mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(flie))
                .build();
        imageloader.init(config);

        options = new DisplayImageOptions.Builder()
                //设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                //设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                //设置图片的解码类型
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                //设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                //设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                //设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                //是否设置为圆角，弧度为多少
                .displayer(new RoundedBitmapDisplayer(20))
                //是否图片加载好后渐入的动画时间
                .displayer(new FadeInBitmapDisplayer(100))
                //构建完成
                .build();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //得到数据  初始化数据
        GoodsDataBean.SongListBean bean = mlist.get(position);
        holder.itemView.setTag(position);
        holder.goodsItemTitle.setText(bean.getTitle());
        holder.goodsItemName.setText(bean.getAuthor());
        holder.goodsItemStatic.setText(bean.getAlbum_title());

        //Imageloader加载图片
        ImageLoader.getInstance().displayImage(bean.getPic_big(), holder.goodsItemImage, options);
        //条目的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setitemonclick != null) {
                    setitemonclick.setonitemhol(v, (Integer) v.getTag());
                }
            }
        });

    }

    /**
     * 自定义的接口
     * 并暴露
     */
    public interface setitemonclick {
        void setonitemhol(View view, int position);
    }

    setitemonclick setitemonclick;


    public void setSetitemonclick(GoodsAdapter.setitemonclick setitemonclick) {
        this.setitemonclick = setitemonclick;
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_item_image)
        ImageView goodsItemImage;
        @BindView(R.id.goods_item_title)
        TextView goodsItemTitle;
        @BindView(R.id.goods_item_name)
        TextView goodsItemName;
        @BindView(R.id.goods_item_static)
        TextView goodsItemStatic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
