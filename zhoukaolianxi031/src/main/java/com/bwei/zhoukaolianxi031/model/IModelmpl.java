package com.bwei.zhoukaolianxi031.model;


import com.bwei.zhoukaolianxi031.callback.MyCallBack;
import com.bwei.zhoukaolianxi031.okhttp.MCallBack;
import com.bwei.zhoukaolianxi031.okhttp.OkHttpUtils;

import java.util.Map;

public class IModelmpl implements IModel {
    @Override
    public void requestData(String path, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
       OkHttpUtils.getInstance().getRequest(path, clazz, new MCallBack() {
           @Override
           public void onSuccess(Object object){
               myCallBack.getData(object);
           }

           @Override
           public void onFail(Exception e) {
              myCallBack.getData(e.getMessage());
           }
       });
    }
}
