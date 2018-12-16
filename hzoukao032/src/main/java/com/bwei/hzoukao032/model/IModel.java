package com.bwei.hzoukao032.model;

import com.bwei.hzoukao032.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void getRequest(String dataUrl, Map<String,String> params, Class clazz,MyCallBack callBack);
}
