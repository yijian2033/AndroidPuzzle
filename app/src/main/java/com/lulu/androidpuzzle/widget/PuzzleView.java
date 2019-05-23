package com.lulu.androidpuzzle.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.lulu.androidpuzzle.R;

/**
 * create by zyj
 * on 2019/5/21
 **/
public class PuzzleView extends View {

    private Bitmap bitmap;
    private Bitmap bitmap2;
    private Paint mBitmapPaint;

    public PuzzleView(Context context) {
        this(context, null);
    }

    public PuzzleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.demo9);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.demo3);
        mBitmapPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.save();
//        canvas.clipRect(0, 0, getWidth() / 2, getHeight());
//        // 缩放后两个bitmap需要绘制的参数
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, getWidth() / 2, getHeight(), true);
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, getWidth() / 2, getHeight(), true);
//
//        Rect leftRect = new Rect(0, 0,getWidth() / 2, getHeight());
//
//        canvas.drawBitmap(scaledBitmap, leftRect, leftRect, null);
//        canvas.restore();
//
//        canvas.save();
//        canvas.clipRect(getWidth() / 2, 0, getWidth(), getHeight());
//        Rect rightRect = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
//        canvas.drawBitmap(scaledBitmap, rightRect, rightRect, null);
//        canvas.restore();


//        canvas.save();
//        canvas.clipRect(0, 0, getWidth() / 2, getHeight());
        canvas.drawBitmap(scaledBitmap, 0, 0, null);
//        canvas.restore();

//        canvas.save();
//        canvas.clipRect(getWidth() / 2, 0, getWidth(), getHeight());
        canvas.drawBitmap(scaledBitmap2, getWidth() / 2, 0, null);
//        canvas.restore();


    }


    // 等比缩放图片
    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

}
