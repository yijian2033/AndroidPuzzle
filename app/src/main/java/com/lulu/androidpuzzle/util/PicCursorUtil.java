package com.lulu.androidpuzzle.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by zyj
 * on 2019/5/24
 **/
public class PicCursorUtil {


    /**
     * 获取所有的png和JPEG JPG 格式的照片
     * 其实就是查询数据库
     *
     * @param context
     * @return
     */
    public static List<String> getAllPicture(Context context) {
        List<String> picPaths = new ArrayList<>();

        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            final String[] columns = {MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DATA};
            // 构建查询条件，且只查询jpeg和png以及JPG的图片
            StringBuilder selection = new StringBuilder();
            selection.append(MediaStore.Images.Media.MIME_TYPE).append("=?");
            selection.append(" or ");
            selection.append(MediaStore.Images.Media.MIME_TYPE).append("=?");
            selection.append(" or ");
            selection.append(MediaStore.Images.Media.MIME_TYPE).append("=?");
            //根据构建的日期
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, selection.toString(), new String[]{
                    "image/jpeg", "image/png", "image/jpg"
            }, orderBy);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File file = new File(path);
                    if (file.exists() && file.isFile()) {
                        picPaths.add(path);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        Collections.reverse(picPaths);
        return picPaths;
    }

}
