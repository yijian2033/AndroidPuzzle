package com.lulu.androidpuzzle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lulu.androidpuzzle.R;

import java.util.List;

/**
 * create by zyj
 * on 2019/5/24
 **/
public class SelectPicAdapter extends RecyclerView.Adapter<SelectPicAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mSelectPaths;
    private OnItemDeleteClickListener listener;

    public void setListener(OnItemDeleteClickListener listener) {
        this.listener = listener;
    }

    public SelectPicAdapter(Context context, List<String> selectPaths) {
        this.mContext = context;
        this.mSelectPaths = selectPaths;
    }

    public void setData(List<String> selectPaths) {
        this.mSelectPaths = selectPaths;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_select_pic, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String path = mSelectPaths.get(i);
        Glide.with(mContext)
                .load("file://" + path)
                .into(viewHolder.ivSelectPic);

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemDeleteClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSelectPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivSelectPic, ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSelectPic = itemView.findViewById(R.id.iv_select_pic);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(int position);
    }

}
