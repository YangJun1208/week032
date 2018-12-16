package com.bwei.hzoukao032.persenter;

import java.util.Map;

public interface IPersenter {
    void getRequest(String dataUrl, Map<String,String> params,Class clazz);
}
