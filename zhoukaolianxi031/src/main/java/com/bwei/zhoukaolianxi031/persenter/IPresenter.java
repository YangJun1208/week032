package com.bwei.zhoukaolianxi031.persenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String path, Map<String, String> params, Class clazz);
}
