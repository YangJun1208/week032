package com.bwei.hzoukao032.model;

import com.bwei.hzoukao032.callback.MyCallBack;
import com.bwei.hzoukao032.okhttp.ICallBack;
import com.bwei.hzoukao032.okhttp.OkHttpUtils;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getRequest(String dataUrl, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtils.getInstance().getpoqueue(dataUrl, params, clazz, new ICallBack() {
            @Override
            public void getResponse(Object obj) {
                callBack.onSuccess(obj);
            }

            @Override
            public void getFailes(Exception e) {
                callBack.onSuccess(e);
            }
        });
    }
}
