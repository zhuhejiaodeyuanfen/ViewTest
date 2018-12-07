package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyViewCircle extends View {

    private Paint paint, pointPaint;
    private int width, height;
    private List<PointF> pointDataList, pointControlList;
    private int circleRadius;
    private int mDuration = 1000; //动画总时间
    private int mCurrTime = 0;  //当前已进行时间
    private int mCount = 100;//将总时间划分多少块
    private float mPiece = mDuration / mCount; //每一块的时间 ；


    public MyViewCircle(Context context) {
        this(context, null);
    }

    public MyViewCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyViewCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);


        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.RED);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.STROKE);

        circleRadius = 250;

        pointDataList = new ArrayList<>();
        pointControlList = new ArrayList<>();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();

        pointDataList.add(new PointF(circleRadius, 0));
        pointDataList.add(new PointF(0, circleRadius));
        pointDataList.add(new PointF(-circleRadius, 0));
        pointDataList.add(new PointF(0, -circleRadius));


        pointControlList.add(new PointF(circleRadius, circleRadius / 2));
        pointControlList.add(new PointF(circleRadius / 2, circleRadius));
        pointControlList.add(new PointF(-circleRadius / 2, circleRadius));
        pointControlList.add(new PointF(-circleRadius, circleRadius / 2));
        pointControlList.add(new PointF(-circleRadius, -circleRadius / 2));
        pointControlList.add(new PointF(-circleRadius / 2, -circleRadius));
        pointControlList.add(new PointF(circleRadius / 2, -circleRadius));
        pointControlList.add(new PointF(circleRadius, -circleRadius / 2));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        pointDataList.get(3).y += 230;
//
//        pointControlList.get(0).x -= 20.0 ;
//
//        pointControlList.get(1).y -= 80.0 ;
//        pointControlList.get(2).y -= 80.0 ;
//        pointControlList.get(3).x += 20.0;

        canvas.translate(width / 2, height / 2);
        canvas.drawLine(-width / 2, 0, width / 2, 0, paint);
        canvas.drawLine(0, -height / 2, 0, height / 2, paint);

        //绘制数据点
        for (int i = 0; i < pointDataList.size(); i++) {
            canvas.drawPoint(pointDataList.get(i).x, pointDataList.get(i).y, pointPaint);
        }

        for (int i = 0; i < pointControlList.size(); i++) {
            canvas.drawPoint(pointControlList.get(i).x, pointControlList.get(i).y, pointPaint);
        }

        //利用三阶贝塞尔曲线实现画圆
        Path path = new Path();
        path.moveTo(pointDataList.get(0).x, pointDataList.get(0).y);
        for (int i = 0; i < pointDataList.size(); i++) {
            if (i == pointDataList.size() - 1) {
                path.cubicTo(pointControlList.get(2 * i).x, pointControlList.get(2 * i).y,
                        pointControlList.get(2 * i + 1).x, pointControlList.get(2 * i + 1).y,
                        pointDataList.get(0).x, pointDataList.get(0).y);

            } else {
                path.cubicTo(pointControlList.get(2 * i).x, pointControlList.get(2 * i).y,
                        pointControlList.get(2 * i + 1).x, pointControlList.get(2 * i + 1).y,
                        pointDataList.get(i + 1).x, pointDataList.get(i + 1).y);
            }

        }
        canvas.drawPath(path, pointPaint);

        /***
         * 一个Y正轴上数据点改变，四个Y负轴控制点改变了
         */
        mCurrTime += mPiece;
        if (mCurrTime < mDuration) {
            System.out.println("重新绘制"+mCurrTime+"延时时间"+mPiece);
            pointDataList.get(3).y += 230 / mCount;

            pointControlList.get(0).x -= 20.0 / mCount;

            pointControlList.get(1).y -= 80.0 / mCount;
            pointControlList.get(2).y -= 80.0 / mCount;
            pointControlList.get(3).x += 20.0 / mCount;

            postInvalidateDelayed((long) mPiece);
        }




    }
}
