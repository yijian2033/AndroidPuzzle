package com.lulu.androidpuzzle.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.lulu.androidpuzzle.R;
import com.lulu.androidpuzzle.adapter.AllPicAdapter;
import com.lulu.androidpuzzle.adapter.SelectPicAdapter;
import com.lulu.androidpuzzle.constant.Constant;
import com.lulu.androidpuzzle.task.PhotoTask;
import com.lulu.androidpuzzle.widget.TopView;

import java.util.ArrayList;
import java.util.List;

public class PuzzlePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvPicAll;
    private TextView tvChosePic;
    private TextView tvStart;
    private RecyclerView rvPicSelect;
    private TopView topView;
    private List<String> mAllPicPaths;
    private AllPicAdapter mAllPicAdapter;
    private List<String> mSelectPaths;
    private SelectPicAdapter mSelectPicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_picker);
        initView();
        initData();
        initListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, 110);
        } else {
            loadPhoto();
        }
    }


    private void initView() {
        topView = (TopView) findViewById(R.id.top_view);
        rvPicAll = (RecyclerView) findViewById(R.id.rv_pic_all);
        tvChosePic = (TextView) findViewById(R.id.tv_chose_pic);
        tvStart = (TextView) findViewById(R.id.tv_start);
        rvPicSelect = (RecyclerView) findViewById(R.id.rv_pic_select);
        RecyclerViewDivider.with(this)
                .size(2)
//                .hideLastDivider()
                .color(Color.BLACK)
                .build()
                .addTo(rvPicAll);
        topView.setTitle("选择图片");
        topView.setOnLeftClickListener(new TopView.OnLeftClickListener() {
            @Override
            public void leftClick() {
                finish();
            }
        });
    }

    private void initData() {
        //所有的照片
        mAllPicPaths = new ArrayList<>();
        //选中照片
        mSelectPaths = new ArrayList<>();

        //所有照片
        mAllPicAdapter = new AllPicAdapter(this, mAllPicPaths);
        rvPicAll.setAdapter(mAllPicAdapter);
        rvPicAll.setLayoutManager(new GridLayoutManager(this, 4));

        //选中照片
        mSelectPicAdapter = new SelectPicAdapter(this, mSelectPaths);
        rvPicSelect.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPicSelect.setAdapter(mSelectPicAdapter);


    }

    private void initListener() {

        tvStart.setOnClickListener(this);

        //所有照片，选中一个就添加一个
        mAllPicAdapter.setListener(new AllPicAdapter.OnRvItemClickListener() {
            @Override
            public void onRvItemClick(String picPath) {
                if (mSelectPaths.size() > 8) {
                    Toast.makeText(PuzzlePickerActivity.this, "最多只能选择9张照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                mSelectPaths.add(picPath);
                tvChosePic.setText("当前已选中" + mSelectPaths.size() + "张（最多9张）");
                mSelectPicAdapter.setData(mSelectPaths);
            }
        });

        //选中照片的删除
        mSelectPicAdapter.setListener(new SelectPicAdapter.OnItemDeleteClickListener() {
            @Override
            public void onItemDeleteClick(int position) {
                mSelectPaths.remove(position);
                tvChosePic.setText("当前已选中" + mSelectPaths.size() + "张（最多9张）");
                mSelectPicAdapter.setData(mSelectPaths);
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                if (mSelectPaths.size() <= 0) {
                    Toast.makeText(this, "至少选中一张照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, PuzzleViewActivity.class);
                intent.putStringArrayListExtra(Constant.PHOTO_PATH, (ArrayList<String>) mSelectPaths);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPhoto() {
        new PhotoTask() {
            @Override
            protected void onPostExecute(List<String> stringList) {
                super.onPostExecute(stringList);
                mAllPicPaths = stringList;
                mAllPicAdapter.setData(mAllPicPaths);
            }
        }.execute(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            loadPhoto();
        }
    }

}
