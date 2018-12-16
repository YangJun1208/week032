package com.bwei.hzoukao032.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.bwei.hzoukao032.callback.MyCallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static OkHttpUtils instance;

    private Handler mHandler=new Handler(Looper.getMainLooper());
    private final OkHttpClient client;

    public static OkHttpUtils getInstance() {
        if(instance==null){
            synchronized (OkHttpUtils.class){
                if(null==instance){
                    instance=new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    public OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .readTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void getpoqueue(String dataUrl, Map<String,String> params, final Class clazz, final ICallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }


        RequestBody build = builder.build();

        Request builder1 = new Request.Builder()
                .url(dataUrl)
                .post(build)
                .build();

        Call call = client.newCall(builder1);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getFailes(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String string = response.body().string();
                final Object o = new Gson().fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getResponse(o);
                    }
                });

            }
        });
    }

}
