package com.bwei.hzoukao032;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bwei.hzoukao032.activity.LoginActivity;
import com.bwei.hzoukao032.adapter.MineAdapter;
import com.bwei.hzoukao032.bean.UserBean;
import com.bwei.hzoukao032.persenter.IPersenterImpl;
import com.bwei.hzoukao032.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private IPersenterImpl iPersenter;
    private XRecyclerView recyclerView;
    private boolean isLiear=true;
    private int mPager=1;
    private MineAdapter adapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPersenter=new IPersenterImpl(this);
        recyclerView=findViewById(R.id.recycleView);
        button=findViewById(R.id.qiehuan);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsponse();
                loadData();
            }
        });

        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPager=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                mPager++;
                loadData();
            }


        });
        loadData();
        getsponse();
    }


    private void loadData() {
        Map<String,String> params=new HashMap<>();
        params.put("page",mPager+"");
        iPersenter.getRequest(Apis.TYPE_TITLE,params,UserBean.class);
    }

    public void getsponse(){
        if(isLiear){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }else{
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }
        adapter = new MineAdapter(isLiear, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListsner(new MineAdapter.OnClickListsner() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("name",position);
                startActivity(intent);
            }
        });
        isLiear=!isLiear;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof UserBean){
            UserBean userBean= (UserBean) data;
            if(mPager==1){
                adapter.setDatas(userBean.getData());
            }else{
                adapter.addDatas(userBean.getData());
            }
        }
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.deteach();
    }
}
