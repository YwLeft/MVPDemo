package com.example.asus.gaozhijie20171026.mode.net;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.string.ok;
import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;

/**
 * 创建时间  2017/10/26 14:48
 * 创建人
 * 类描述      ok的封装
 */
public class MyOkhttps {
    protected CallBack netDataCallback;
    private Handler mhand = new Handler();

    public <T> void getdata(String url, final CallBack netDataCallback, final Class<T> tclass) {
        //初始化一个 OkHttpClient 并且设置连接和读取超时时间
        OkHttpClient okhttp = new OkHttpClient.Builder()
                //设置网络拦截器，并打印log
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        //构造一个Request对象
        Request request = new Request.Builder().url(url).build();
        //通过request的对象去构造得到一个Call对象
        Call call = okhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                T t = gson.fromJson(response.body().string(), tclass);
                final Message msg = Message.obtain();
                msg.what = ok;
                msg.obj = t;
                mhand.post(new Runnable() {
                    @Override
                    public void run() {
                        netDataCallback.success(msg.obj);
                    }
                });

            }
        });

    }


    //post请求
    public <T> void getLoadDataPost(String url, final CallBack netDataCallback, final Class<T> tClass, RequestBody body) {
        this.netDataCallback = netDataCallback;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置网络拦截器，并打印log
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        final Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                T t = new Gson().fromJson(response.body().string(), tClass);
                final Message msg = Message.obtain();
                msg.what = OK;
                msg.obj = t;
                mhand.post(new Runnable() {
                    @Override
                    public void run() {
                        netDataCallback.success(msg.obj);

                    }
                });
            }
        });
    }
}
