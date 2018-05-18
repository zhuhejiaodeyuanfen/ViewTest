package com.vivian.bezierview.locatemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SpecialMap extends View {
    private Paint paint;
    private int width, height;
    private Region topRegion, leftRegion, rightRegion, bottomRegion, totalRegion, centerRegion;
    private Matrix matrix;
    private RectF rectFBig, rectFSmall;
    private Path leftPath, topPath, rightPath, bottomPath, centerPath;

    private float[] touchPoints = new float[2];

    public SpecialMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(10);

        topRegion = new Region();
        leftRegion = new Region();
        rightRegion = new Region();
        bottomRegion = new Region();
        totalRegion = new Region();
        centerRegion = new Region();

        rectFBig = new RectF(-400, -400, 400, 400);
        rectFSmall = new RectF(-250, -250, 250, 250);
        totalRegion = new Region(-width / 2, -height / 2, width / 2, height / 2);

        leftPath = new Path();
        topPath = new Path();
        rightPath = new Path();
        bottomPath = new Path();
        centerPath = new Path();

        int sweepAngle = 80;
        /**
         * startAngle是开始度数
         * sweepAngle指的是旋转的度数，也就是以startAngle开始，旋转多少度，如果sweepAngle是正数，那么就是按顺时针方向旋转，如果是负数就是按逆时针方向旋转。
         */
        rightPath.addArc(rectFBig, 5, sweepAngle);
        /**
         * arcTo是先画一个椭圆，然后再在这个椭圆上面截取一部分部形。这个图形自然就是一个弧线了
         */
        rightPath.arcTo(rectFSmall, 5 + sweepAngle, -sweepAngle);
        rightPath.close();

        bottomPath.addArc(rectFBig, 95, sweepAngle);
        bottomPath.arcTo(rectFSmall, 95 + sweepAngle, -sweepAngle);
        bottomPath.close();


        leftPath.addArc(rectFBig, 185, sweepAngle);
        leftPath.arcTo(rectFSmall, 185 + sweepAngle, -sweepAngle);
        leftPath.close();


        topPath.addArc(rectFBig, 275, sweepAngle);
        topPath.arcTo(rectFSmall, 275 + sweepAngle, -sweepAngle);
        topPath.close();

        centerPath.addCircle(0, 0, 120, Path.Direction.CW);
        matrix = new Matrix();

        leftRegion.setPath(leftPath, totalRegion);
        topRegion.setPath(topPath, totalRegion);
        rightRegion.setPath(rightPath, totalRegion);
        bottomRegion.setPath(bottomPath, totalRegion);
        centerRegion.setPath(centerPath, totalRegion);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        matrix.reset();
        if (matrix.isIdentity()) {
            canvas.getMatrix().invert(matrix);
        }
        canvas.drawPath(leftPath, paint);
        canvas.drawPath(bottomPath, paint);
        canvas.drawPath(rightPath, paint);
        canvas.drawPath(topPath, paint);
        canvas.drawPath(centerPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchPoints[0] = event.getRawX();
                touchPoints[1] = event.getRawY();

                matrix.mapPoints(touchPoints);
                if (topRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "点击了右上部", Toast.LENGTH_LONG).show();
                } else if (leftRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "点击了左上部", Toast.LENGTH_LONG).show();
                } else if (bottomRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "点击了左下部", Toast.LENGTH_LONG).show();
                } else if (rightRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "点击了右下部", Toast.LENGTH_LONG).show();
                } else if (centerRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "点击了中部", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;

    }
}
