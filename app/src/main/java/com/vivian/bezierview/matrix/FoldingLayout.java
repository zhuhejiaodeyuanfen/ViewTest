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

public class FoldingLayout extends View {

    public static final int NUM_OF_POINT = 8;

    private int numberOfFolds = 8;

    private float scaleSize = 0.4f;

    private Bitmap bitmap;

    private int widthAfter;

    private int everyWidthBefore, everyWidthAfter;

    private Matrix[] matrices;

    public FoldingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wall_test);

        widthAfter = (int) (bitmap.getWidth() * scaleSize);

        everyWidthBefore = bitmap.getWidth() / NUM_OF_POINT;
        everyWidthAfter = widthAfter / NUM_OF_POINT;

        matrices = new Matrix[numberOfFolds];

        for (int i = 0; i < numberOfFolds; i++) {
            matrices[i] = new Matrix();
        }

        int depth = (int) Math.sqrt(everyWidthBefore * everyWidthBefore - everyWidthAfter * everyWidthAfter);

        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];

        for (int i = 0; i < numberOfFolds; i++) {
            src[0] = i * everyWidthBefore;
            src[1] = 0;
            src[2] = src[0] + everyWidthBefore;
            src[3] = 0;
            src[4] = src[2];
            src[5] = bitmap.getHeight();
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i % 2 == 0;
            dst[0] = i * everyWidthAfter;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + everyWidthAfter;
            dst[3] = isEven ? depth : 0;
            dst[4] = dst[2];
            dst[5] = isEven ? bitmap.getHeight() - depth : bitmap.getHeight();
            dst[6] = dst[0];
            dst[7] = isEven ? bitmap.getHeight() : bitmap.getHeight() - depth;

            matrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < numberOfFolds; i++) {
            canvas.save();
            canvas.concat(matrices[i]);
            canvas.clipRect(everyWidthBefore * i, 0, everyWidthBefore * i + everyWidthBefore, bitmap.getHeight());
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.translate(everyWidthBefore * i, 0);

            canvas.restore();
        }
    }
}
