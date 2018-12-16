package com.bwei.zhoukaolianxi031;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.ImageView;

import com.bwei.zhoukaolianxi031.adapter.RecyclerAdapter;
import com.bwei.zhoukaolianxi031.bean.GoodsBean;
import com.bwei.zhoukaolianxi031.persenter.IPresenterImpl;
import com.bwei.zhoukaolianxi031.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private XRecyclerView xRecyclerView;
    private int mPage;
    private RecyclerAdapter adapter;
    private IPresenterImpl iPresenter;

    private ImageView image_xin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xRecyclerView=findViewById(R.id.x_recycle);
        image_xin=findViewById(R.id.image_xin);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        mPage=1;
        iPresenter=new IPresenterImpl(this);
        adapter = new RecyclerAdapter(this);
        xRecyclerView.setAdapter(adapter);


        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        adapter.setOnItemClickListener(new RecyclerAdapter.onItemClickListener() {
            @Override
            public void getPosition(int position) {
                adapter.delItem(position);
            }
        });
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
        image_xin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(image_xin, "translationX", 0, -480,0);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(image_xin, "translationY", 0, 800, 0);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(image_xin, "alpha", 1f, 0f, 1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(5000);
                animatorSet.playTogether(translationX,translationY,alpha);
                animatorSet.start();

                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        image_xin.setSelected(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            }
        });
    }

    private void loadData() {
        iPresenter.startRequest(String.format(Apis.TYPE_TITLE,mPage+""),new HashMap<String, String>(),GoodsBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
        if(data instanceof GoodsBean){
            GoodsBean goodsBean= (GoodsBean) data;
            if(mPage==1){
                adapter.setList(goodsBean.getData());
            }else{
                adapter.addList(goodsBean.getData());
            }
            mPage++;
        }
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }
}
