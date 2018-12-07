package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RegionSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Paint paint;
    private SurfaceHolder surfaceHolder;

    private Rect rect;
    private Thread thread;
    private boolean flag,isCollsion;
    private int sleeptime = 100;
    private Region region;

    public RegionSurfaceView(Context context) {
        this(context, null);
    }

    public RegionSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect(0, 0, 300, 300);
        region=new Region(rect);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        thread = new Thread(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;
        if (!thread.isAlive()) {
            thread.start();
        }

    }

    /**
     * 触点事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(region.contains((int)event.getX(), (int)event.getY())){
            isCollsion=true;
        }
        else{
            isCollsion=false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = true;

    }

    private void paint(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(rect, paint);
        if(isCollsion){
            paint.setColor(Color.RED);
            canvas.drawText("点击碰撞了", 10, 10, paint);
        }
        else{
            paint.setColor(Color.BLACK);
            canvas.drawText("没有碰撞了", 10, 10, paint);
        }
    }

    @Override
    public void run() {

        Canvas canvas = null;
        while (flag) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (canvas) {
                    paint(canvas);
                }
            } catch (Exception e) {

            } finally {
                {
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
