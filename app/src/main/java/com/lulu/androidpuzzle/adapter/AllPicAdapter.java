package com.lulu.androidpuzzle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lulu.androidpuzzle.R;
import com.lulu.androidpuzzle.widget.PicItem;

import java.util.List;

/**
 * create by zyj
 * on 2019/5/24
 **/
public class AllPicAdapter extends RecyclerView.Adapter<AllPicAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mPicPaths;

    private OnRvItemClickListener listener;

    public void setListener(OnRvItemClickListener listener) {
        this.listener = listener;
    }

    public AllPicAdapter(Context context, List<String> paths) {
        this.mContext = context;
        this.mPicPaths = paths;
    }

    /**
     * 刷新数据
     *
     * @param paths
     */
    public void setData(List<String> paths) {
        mPicPaths = paths;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(R.layout.item_select_pic, viewGroup, false);
        return new ViewHolder(new PicItem(mContext));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String path = mPicPaths.get(position);
        Log.d("john_AllPicAdapter", "path : " + path);
        Glide.with(mContext)
                .load("file://" + path)
                .thumbnail(0.1f)
                .into(viewHolder.ivPicAll);


        viewHolder.ivPicAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRvItemClick(path);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPicPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicAll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicAll = ((PicItem) itemView).getmImageView();
        }
    }

    public interface OnRvItemClickListener {
        void onRvItemClick(String picPath);
    }

}
