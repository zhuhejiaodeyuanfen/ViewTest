package com.vivian.bezierview.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class BubbleView extends View {
    private Paint normalPaint, textPaint;

    private float radiusBig = 150, radiusSmall = 40;
    private Path bigCirclePath, smallCirclePath, bezierPath, linePath, textPath;
    private float screenWidth, screenHeight;
    private Region bigRegion, totalRegion;
    private Matrix matrix;
    private float[] userPointXY = new float[2],
            lastPointXY = new float[2],
            controlXY = new float[2],
            startSmall = new float[2],
            endSmall = new float[2],
            startBig = new float[2],
            endBig = new float[2];
    private int painTextSize = 65;
    private float limitLength = 480, sin, cos, length, varySet = 1;

    private Type type = Type.NORMAL;
    private ValueAnimator valueAnimatorGone, valueAnimatorBack;
    private PathMeasure pathMeasure;

    /**
     * 1.正常 状态
     * 2.拖拽状态--绘制大圆+小圆+贝塞尔曲线
     * 3.回退状态--复位
     * 4.超过状态--只显示大圆
     */
    private enum Type {
        NORMAL, DRAG, COMEBACK, BEYOND
    }


    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAnimation();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPaint.setStyle(Paint.Style.FILL);
        normalPaint.setStrokeWidth(5);
        normalPaint.setColor(Color.RED);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(5);
        textPaint.setTextSize(painTextSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        bigRegion = new Region();
        totalRegion = new Region();
        bigCirclePath = new Path();
        smallCirclePath = new Path();
        linePath = new Path();
        bezierPath = new Path();
        textPath = new Path();
        matrix = new Matrix();
        pathMeasure = new PathMeasure();
    }

    private void initAnimation() {
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                varySet = (float) animation.getAnimatedValue();
                invalidate();

            }
        };

        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                type = Type.NORMAL;
                varySet = 1;
            }
        };
        valueAnimatorGone = ValueAnimator.ofFloat(1.000f, 0.000f);
        valueAnimatorGone.setInterpolator(new LinearInterpolator());
        valueAnimatorGone.setRepeatCount(0);
        valueAnimatorGone.setDuration(1000);
        valueAnimatorGone.addUpdateListener(animatorUpdateListener);
        valueAnimatorGone.addListener(animatorListenerAdapter);

        valueAnimatorBack = ValueAnimator.ofFloat(1.000f, 0.000f);
        valueAnimatorBack.setInterpolator(new LinearInterpolator());
        valueAnimatorBack.setRepeatCount(0);
        valueAnimatorBack.setDuration(1000);
        valueAnimatorBack.addUpdateListener(animatorUpdateListener);
        valueAnimatorBack.addListener(animatorListenerAdapter);

    }


    /**
     * 更新所有轨迹和点的状态
     */
    private void initPath() {
        if (type == Type.NORMAL) {
            userPointXY[0] = 0;
            userPointXY[1] = 0;
        }
        controlXY[0] = userPointXY[0] / 2;
        controlXY[1] = userPointXY[1] / 2;

        sin = (userPointXY[1] - 0) / length;
        cos = (0 - userPointXY[0]) / length;

        startBig[0] = userPointXY[0] + radiusBig * sin;
        startBig[1] = userPointXY[1] + radiusBig * cos;

        endBig[0] = userPointXY[0] - radiusBig * sin;
        endBig[1] = userPointXY[1] - radiusBig * cos;

        startSmall[0] = 0 + radiusSmall * sin;
        startSmall[1] = 0 + radiusSmall * cos;

        endSmall[0] = 0 - radiusSmall * sin;
        endSmall[1] = 0 - radiusSmall * cos;
        linePath.reset();
        linePath.lineTo(userPointXY[0], userPointXY[1]);

        if (type == Type.COMEBACK) {
            pathMeasure.setPath(linePath, false);

            pathMeasure.getPosTan(pathMeasure.getLength() * varySet, userPointXY, null);
        }

        bigCirclePath.reset();
        textPath.reset();
        smallCirclePath.reset();
        bezierPath.reset();
        smallCirclePath.addCircle(0, 0, radiusSmall, Path.Direction.CW);
        if (type == Type.BEYOND) {
            bigCirclePath.addCircle(userPointXY[0], userPointXY[1], radiusBig * varySet, Path.Direction.CW);

            textPath.moveTo(userPointXY[0] - radiusBig, userPointXY[1] + 4 * varySet);
            textPath.lineTo(userPointXY[0] + radiusBig, userPointXY[1] + 4 * varySet);
            textPaint.setTextSize(painTextSize * varySet);
        } else {
            bigCirclePath.addCircle(userPointXY[0], userPointXY[1], radiusBig, Path.Direction.CW);
            textPath.moveTo(userPointXY[0] - radiusBig, userPointXY[1] + 4);
            textPath.lineTo(userPointXY[0] + radiusBig, userPointXY[1] + 4);
            textPaint.setTextSize(painTextSize);
        }
        bigRegion.setPath(bigCirclePath, totalRegion);

        bezierPath.moveTo(startSmall[0], startSmall[1]);
        bezierPath.quadTo(controlXY[0], controlXY[1], startBig[0], startBig[1]);
        bezierPath.lineTo(endBig[0], endBig[1]);
        bezierPath.quadTo(controlXY[0], controlXY[1], endSmall[0], endSmall[1]);
        bezierPath.close();


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        totalRegion.set(-w, -h, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
                drawBigCircle(canvas);
                drawSmallCircle(canvas);
                drawDragPath(canvas);
                break;
            case BEYOND:
                drawBigCircle(canvas);
                break;
            case COMEBACK:
                drawBigCircle(canvas);
        }
        drawText(canvas);
        super.onDraw(canvas);

    }

    /**
     * 绘制大圆
     */
    private void drawBigCircle(Canvas canvas) {
        canvas.drawPath(bigCirclePath, normalPaint);
    }

    private void drawSmallCircle(Canvas canvas) {
        canvas.drawPath(smallCirclePath, normalPaint);
    }

    /**
     * 绘制贝塞尔曲线
     *
     * @param canvas
     */
    private void drawDragPath(Canvas canvas) {
        canvas.drawPath(bezierPath, normalPaint);
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = (-fontMetrics.ascent - fontMetrics.descent) / 2;
        float textWidth = textPaint.measureText("1");
        canvas.drawTextOnPath("1", textPath, 0, 0, textPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (type == Type.NORMAL) {
                    matrix.mapPoints(userPointXY, new float[]{event.getRawX(), event.getRawY()});
                    if (bigRegion.contains((int) userPointXY[0], (int) userPointXY[1])) {
                        type = Type.DRAG;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                matrix.mapPoints(userPointXY, new float[]{event.getRawX(), event.getRawY()});
                length = (float) Math.hypot(userPointXY[0], userPointXY[1]);
                if (length <= limitLength) {
//                    type = Type.COMEBACK;
                    lastPointXY[0] = userPointXY[0];
                    lastPointXY[1] = userPointXY[1];
                } else {
                    type = Type.BEYOND;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (type == Type.BEYOND) {
                    //超过区域  需要慢慢消失
                    valueAnimatorGone.start();
                } else if (type == Type.DRAG) {
                    userPointXY[0] = lastPointXY[0];
                    userPointXY[1] = lastPointXY[1];
                    type = Type.COMEBACK;
                    valueAnimatorBack.start();

                }
                break;

        }
        return true;
    }
}
