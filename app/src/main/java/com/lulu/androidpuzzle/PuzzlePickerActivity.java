package com.lulu.androidpuzzle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.lulu.androidpuzzle.widget.TopView;
import com.xiaopo.flying.poiphoto.GetAllPhotoTask;
import com.xiaopo.flying.poiphoto.PhotoManager;
import com.xiaopo.flying.poiphoto.datatype.Photo;
import com.xiaopo.flying.poiphoto.ui.adapter.PhotoAdapter;

import java.util.List;

public class PuzzlePickerActivity extends AppCompatActivity {

    private RecyclerView rvPicAll;
    private TextView tvChosePic;
    private TextView tvStart;
    private RecyclerView rvPicSelect;
    private PhotoAdapter mPhotoAdapter;
    private TopView topView;

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

//
//        RecyclerViewDivider.with(this)
//                .size(2)
//                .hideLastDivider()
//                .color(Color.BLACK)
//                .build()
//                .addTo(rvPicAll);

        topView.setTitle("选择图片");

        topView.setOnLeftClickListener(new TopView.OnLeftClickListener() {
            @Override
            public void leftClick() {
                finish();
            }
        });
    }

    private void initData() {

        mPhotoAdapter = new PhotoAdapter();
        mPhotoAdapter.setMaxCount(9);
        rvPicAll.setAdapter(mPhotoAdapter);
        rvPicAll.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void initListener() {
       
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPhoto() {
        new GetAllPhotoTask() {
            @Override
            protected void onPostExecute(List<Photo> photos) {
                super.onPostExecute(photos);
                mPhotoAdapter.refreshData(photos);
            }
        }.execute(new PhotoManager(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            loadPhoto();
        }
    }

}
