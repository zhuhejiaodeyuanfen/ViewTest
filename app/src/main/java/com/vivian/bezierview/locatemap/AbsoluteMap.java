package com.vivian.bezierview.locatemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class AbsoluteMap extends View {

    private Paint paint;
    private int width, height;
    private Path path;
    private Region circleRegion;

    public AbsoluteMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);


        circleRegion = new Region();
        path = new Path();
        //Path.Direction.CW---顺时针  Path.Direction.CCW逆时针
        path.addCircle(width / 2, height / 2, 250, Path.Direction.CW);

        Region region = new Region(0, 0, width, height);
        circleRegion.setPath(path, region);

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
        canvas.drawCircle(width / 2, height / 2, 250, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (circleRegion.contains((int) event.getX(), (int) event.getY())) {
                    Toast.makeText(getContext(), "触摸区域内", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;

    }
}
