package com.lulu.androidpuzzle.task;

import android.content.Context;
import android.os.AsyncTask;

import com.lulu.androidpuzzle.util.PicCursorUtil;

import java.util.List;

/**
 * create by zyj
 * on 2019/5/24
 **/
public class PhotoTask extends AsyncTask<Context, Integer, List<String>> {


    @Override
    protected List<String> doInBackground(Context... contexts) {
        Context context = contexts[0];
        List<String> allPicture = PicCursorUtil.getAllPicture(context);
        return allPicture;
    }
}
