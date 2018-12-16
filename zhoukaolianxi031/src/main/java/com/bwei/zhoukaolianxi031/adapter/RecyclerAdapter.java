package com.bwei.zhoukaolianxi031.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bwei.zhoukaolianxi031.R;
import com.bwei.zhoukaolianxi031.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GoodsBean.DataBean> list;
    private Context context;
    private final int ONEIMAGE=0;
    private final int THREEIMAGE=ONEIMAGE+1;

    public RecyclerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<GoodsBean.DataBean> list) {
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<GoodsBean.DataBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder=null;
        if (i==ONEIMAGE){
            View view= LayoutInflater.from(context).inflate(R.layout.one_item,viewGroup,false);
            holder=new ViewHolderOneImage(view);
        }else {
            View view=LayoutInflater.from(context).inflate(R.layout.three_item,viewGroup,false);
            holder=new ViewHolderThreeImage(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case ONEIMAGE:
                final ViewHolderOneImage oneImage= (ViewHolderOneImage) viewHolder;
                oneImage.textView_title.setText(list.get(i).getTitle());
                Glide.with(context).load(list.get(i).getThumbnail_pic_s()).into(oneImage.imageView_pic_s);
                oneImage.textView_one_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(itemClickListener!=null){
                            itemClickListener.getPosition(i);

                        }
                    }
                });
                break;
            case THREEIMAGE:
                final ViewHolderThreeImage threeImage= (ViewHolderThreeImage) viewHolder;
                threeImage.textView_title.setText(list.get(i).getTitle());
                Glide.with(context).load(list.get(i).getThumbnail_pic_s()).into(threeImage.imageView_pic_s);
                Glide.with(context).load(list.get(i).getThumbnail_pic_s02()).into(threeImage.imageView_pic_s2);
                Glide.with(context).load(list.get(i).getThumbnail_pic_s03()).into(threeImage.imageView_pic_s3);
                threeImage.textView_three_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(itemClickListener!=null){
                            itemClickListener.getPosition(i);
                        }
                    }
                });
                break;
                default:
                    break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).hasImage()? ONEIMAGE : THREEIMAGE;
    }

    static class ViewHolderOneImage extends RecyclerView.ViewHolder {
        TextView textView_title,textView_one_del;
        ImageView imageView_pic_s;
        ConstraintLayout constraintLayout_one;
        public ViewHolderOneImage(@NonNull View itemView) {
            super(itemView);
            textView_title=itemView.findViewById(R.id.textView_one_title);
            imageView_pic_s=itemView.findViewById(R.id.imageView_one_pic_s);
            textView_one_del=itemView.findViewById(R.id.textView_one_del);
            constraintLayout_one=itemView.findViewById(R.id.content_one);
        }
    }

    static class ViewHolderThreeImage extends RecyclerView.ViewHolder {
        TextView textView_title,textView_three_del;
        ImageView imageView_pic_s,imageView_pic_s2,imageView_pic_s3;
        ConstraintLayout constraintLayout_three;
        public ViewHolderThreeImage(@NonNull View itemView) {
            super(itemView);
            textView_title=itemView.findViewById(R.id.textView_three_title);
            imageView_pic_s=itemView.findViewById(R.id.imageView_three_pic_s);
            imageView_pic_s2=itemView.findViewById(R.id.imageView_three_pic_s2);
            imageView_pic_s3=itemView.findViewById(R.id.imageView_three_pic_s3);
            textView_three_del=itemView.findViewById(R.id.textView_three_del);
            constraintLayout_three=itemView.findViewById(R.id.content_three);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //接口回调
    public onItemClickListener itemClickListener;
    public void setOnItemClickListener(onItemClickListener itemClick){
        itemClickListener=itemClick;
    }
    public interface onItemClickListener{
        void getPosition(int position);
    }
    //删除
    public void delItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


}
