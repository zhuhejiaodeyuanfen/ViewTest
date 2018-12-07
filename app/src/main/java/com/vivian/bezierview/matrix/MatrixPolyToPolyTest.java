package com.vivian.bezierview.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vivian.bezierview.R;

public class MatrixPolyToPolyTest extends View {
    private Bitmap bitmap;
    private Matrix matrix;
    private int width, height;

    public MatrixPolyToPolyTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        init();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wall_test);
        matrix = new Matrix();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        float[] src = {0, 0,
                width, 0,
                width, height,
                0, height};

        float[] dst = {0, 0,
                width, 400,
                width, height - 200,
                0, height};

        matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

//        matrix.postScale(0.56f, 0.56f);
//        matrix.postTranslate(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
