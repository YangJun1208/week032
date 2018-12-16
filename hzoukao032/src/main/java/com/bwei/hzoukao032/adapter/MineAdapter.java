package com.bwei.hzoukao032.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.hzoukao032.R;
import com.bwei.hzoukao032.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.ViewHolder> {

    public boolean isLiear=true;
    private List<UserBean.DataBean> mDatas;
    private Context context;

    public MineAdapter(boolean isLiear, Context context) {
        this.isLiear = isLiear;
        this.context = context;
        mDatas=new ArrayList<>();
    }

    public void setDatas(List<UserBean.DataBean> datas) {
       // this.mDatas = mDatas;
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<UserBean.DataBean> datas) {
        // this.mDatas = mDatas;
       // mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder=null;
        if(isLiear) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
            viewHolder=new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.gitem, viewGroup, false);
            viewHolder=new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).load(mDatas.get(i).getThumbnail_pic_s()).into(viewHolder.imageView);
        viewHolder.textView.setText(mDatas.get(i).getTitle());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListsner.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text_title);

        }

    }


    OnClickListsner onClickListsner;

    public void setOnClickListsner(OnClickListsner onClickListsner){
        this.onClickListsner=onClickListsner;
    }
    public interface OnClickListsner {
        void onClick(int position);
    }
}
