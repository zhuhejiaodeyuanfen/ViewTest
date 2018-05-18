package com.vivian.bezierview.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class WaveView extends View {

    private Paint paint;
    private Path path;
    private int width, height;

    /**
     * 对画笔进行一些初始化操作
     * @param context
     * @param attrs
     */
    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        path = new Path();

        post(new Runnable() {
            @Override
            public void run() {
                initWave();
            }
        });
    }

    /**
     * 在onMeasure里测量宽度和高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void initWave() {
//        path.moveTo(0, height / 2);
//        path.quadTo(width / 4, height / 2 - 300, width / 2, height / 2);
//        path.quadTo(width / 4 * 3, height / 2 + 300, width, height / 2);
//
//
//        path.lineTo(width, height);
//        path.lineTo(0, height);
//        path.close();
        invalidate();
        initAnimator();
    }

    /**
     * 动画的循环周期是2秒,无限循环
     */
    private void initAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, width);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                initOut(animatedValue);
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
        valueAnimator.setTarget(this);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    /**
     * path的quadTo()方法运用的就是贝塞尔曲线
     * @param offset
     */
    private void initOut(int offset) {
        path.reset();

        path.moveTo(-width + offset, height / 2);
        path.quadTo(-(width / 4 * 3) + offset, height / 2 - 100, -(width / 2) + offset, height / 2);
        path.quadTo(-(width / 4) + offset, height / 2 + 100, 0 + offset, height / 2);
        path.lineTo(0 + offset, height);
        path.lineTo(-width + offset, height);


        path.moveTo(0 + offset, height / 2);
        path.quadTo(width / 4 + offset, height / 2 - 100, width / 2 + offset, height / 2);
        path.quadTo(width / 4 * 3 + offset, height / 2 + 100, width + offset, height / 2);


        path.lineTo(width + offset, height);
        path.lineTo(0 + offset, height);
        path.close();

    }

    /**
     * canvas画布对这些路径进行绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
