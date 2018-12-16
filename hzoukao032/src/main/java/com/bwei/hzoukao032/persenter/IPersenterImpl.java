package com.bwei.hzoukao032.persenter;

import com.bwei.hzoukao032.callback.MyCallBack;
import com.bwei.hzoukao032.model.IModelImpl;
import com.bwei.hzoukao032.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {

    private IView iView;
    private IModelImpl iModel;
    public IPersenterImpl(IView mIView){
        iView=mIView;
        iModel=new IModelImpl();
    }
    @Override
    public void getRequest(String dataUrl, Map<String, String> params, Class clazz) {
        iModel.getRequest(dataUrl, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccess(data);
            }
        });
    }

    public void deteach(){
        iModel=null;
        iView=null;
    }
}
