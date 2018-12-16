package com.bwei.zhoukaolianxi031.model;


import com.bwei.zhoukaolianxi031.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String path, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
