package com.bwei.zhoukaolianxi031.persenter;



import com.bwei.zhoukaolianxi031.callback.MyCallBack;
import com.bwei.zhoukaolianxi031.model.IModelmpl;
import com.bwei.zhoukaolianxi031.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelmpl iModelmpl;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModelmpl=new IModelmpl();
    }

    @Override
    public void startRequest(String path, Map<String, String> params, Class clazz) {
         iModelmpl.requestData(path, params, clazz, new MyCallBack() {
             @Override
             public void getData(Object data) {
                 iView.requestSuccess(data);
             }
         });
    }
    //解除绑定
    public void detchView(){
        if(iModelmpl!=null){
            iModelmpl=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}
