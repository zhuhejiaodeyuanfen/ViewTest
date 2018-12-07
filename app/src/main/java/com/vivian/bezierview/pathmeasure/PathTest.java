package com.vivian.bezierview.pathmeasure;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathTest extends View {

    private Paint paint, pointPaint;


    private Path pathSearch, pathCircle;

    private int width, height;
    private float currentValue = 0;

    private float[] pos;
    private PathMeasure pathMeasure;
    private ValueAnimator searchValueAnimator;
    private SEARCH_STATE search_state = SEARCH_STATE.END;

    public PathTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initAnimation();
        startSearch();


    }

    private void initPaint() {
        paint = new Paint();
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void initPath() {
        pathSearch = new Path();
        pathCircle = new Path();

        pathMeasure = new PathMeasure();
        RectF smallRect = new RectF(-100, -100, 100, 100);
        pathSearch.addArc(smallRect, 45, 359.9f);

        RectF bigRect = new RectF(-200, -200, 200, 200);
        pathCircle.addArc(bigRect, 45, -359.9f);

        float[] pos = new float[2];
        pathMeasure.setPath(pathCircle, false);
        pathMeasure.getPosTan(0, pos, null);
        pathSearch.lineTo(pos[0], pos[1]);
    }

    private void initAnimation() {
        searchValueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        searchValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                invalidate();

            }
        });

        searchValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (search_state == SEARCH_STATE.START) {
                    search_state = SEARCH_STATE.SEARCHING;
                    startSearch();
                }
            }
        });
    }

    private void startSearch() {
        switch (search_state) {
            case START:
                searchValueAnimator.setRepeatCount(0);
                break;

            case SEARCHING:
                searchValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                searchValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                break;
            case END:
                searchValueAnimator.setRepeatCount(0);
                break;
        }
        searchValueAnimator.start();
    }

    private void drawPath(Canvas canvas) {
        switch (search_state) {
            case NONE:
                canvas.drawPath(pathSearch, paint);
                break;
            case START:
                pathMeasure.setPath(pathSearch, true);
                Path path = new Path();
                pathMeasure.getSegment(pathMeasure.getLength() * currentValue, pathMeasure.getLength(), path, true);

                canvas.drawPath(path, paint);
                break;
            case SEARCHING:
                pathMeasure.setPath(pathSearch, true);
                pathMeasure.getSegment(pathMeasure.getLength() * currentValue - 30, pathMeasure.getLength() * currentValue, pathSearch, true);
                canvas.drawPath(pathSearch, paint);
                break;
            case END:
                pathMeasure.setPath(pathSearch, true);
                Path path_view = new Path();

                pathMeasure.getSegment(0, pathMeasure.getLength() * currentValue, path_view, true);
                canvas.drawPath(path_view, paint);
                break;

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(width / 2, height / 2);
        drawPath(canvas);


    }

    public enum SEARCH_STATE {
        START, END, NONE, SEARCHING
    }


}
