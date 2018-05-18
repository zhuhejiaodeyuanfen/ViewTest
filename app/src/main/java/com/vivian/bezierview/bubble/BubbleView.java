package com.vivian.bezierview.bubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BubbleView extends View {

    private int screenWidth, screenHeight;

    private Paint paint;

    enum TYPE {
        //四种状态：正常状态，拖拽状态，超出状态，恢复状态
        NORMAL, DRAG, BEYOND, COMEBACK
    }

    private float[] userPointXY = new float[2], controller = new float[2], endBig = new float[2], startSmall = new float[2], endSmall = new float[2], startBig = new float[2];

    private float radiusSmall = 100, radiusBig = 200;
    //矩阵
    private Matrix matrix;
    private float sin, cos, len;

    private TYPE type = TYPE.NORMAL;
    private Path circleBigPath, dragPath;
    private Region circleRegion;

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        circleBigPath = new Path();
        matrix = new Matrix();
        dragPath = new Path();


    }

    //顺时针方向
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;


        region = new Region(-screenWidth, -screenHeight, screenWidth, screenHeight);


        circleBigPath.addCircle(0, 0, radiusSmall, Path.Direction.CW);

        circleRegion = new Region();
        circleRegion.setPath(circleBigPath, region);
    }

    private Region region;

    public void initPath() {
        if (type == TYPE.NORMAL) {
            userPointXY[0] = 0;
            userPointXY[1] = 0;
        }
        controller[0] = userPointXY[0] / 2;
        controller[1] = userPointXY[1] / 2;

        sin = (0 - userPointXY[1]) / len;
        cos = (0 - userPointXY[0]) / len;

        startSmall[0] = 0 - radiusSmall * sin;
        startSmall[1] = 0 - radiusSmall * cos;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (type == TYPE.NORMAL) {

                }
                userPointXY[0] = event.getRawX();
                userPointXY[1] = event.getRawY();

                matrix.mapPoints(userPointXY);

                boolean contains = circleRegion.contains((int) userPointXY[0], (int) userPointXY[1]);
                if (contains) {
                    type = TYPE.DRAG;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(screenWidth / 2, screenHeight / 2);

        matrix.reset();
        if (matrix.isIdentity()) {
            canvas.getMatrix().invert(matrix);
        }

        initPath();
        switch (type) {
            case NORMAL:
                drawBigCircle(canvas);
                break;
            case DRAG:
                break;
        }


    }

    private void drawBigCircle(Canvas canvas) {
        canvas.drawPath(circleBigPath, paint);
    }


}
