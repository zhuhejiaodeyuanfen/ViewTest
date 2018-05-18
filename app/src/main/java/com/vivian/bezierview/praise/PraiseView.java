package com.vivian.bezierview.praise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vivian.bezierview.R;

import java.util.Random;

public class PraiseView extends RelativeLayout {


    private Drawable[] drawables;
    private LayoutParams layoutParams;
    private Context context;
    private Paint paint;
    private Path path;
    private int totalWidth, totalHeight, praiseWidth, praiseHeight;

    public PraiseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init() {
        setWillNotDraw(false);
        Drawable a = getResources().getDrawable(R.mipmap.praise01);
        Drawable b = getResources().getDrawable(R.mipmap.praise02);
        Drawable c = getResources().getDrawable(R.mipmap.praise03);
        Drawable d = getResources().getDrawable(R.mipmap.praise04);

        praiseWidth = a.getIntrinsicWidth();
        praiseHeight = a.getIntrinsicHeight();

        drawables = new Drawable[]{a, b, c, d};
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLUE);

        path = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalHeight = getMeasuredHeight();
        totalWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

    public void addPraise() {
        final ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawables[new Random().nextInt(4)]);
        addView(imageView, layoutParams);


        PointF startF = new PointF(totalWidth / 2, totalHeight);
        PointF pointF1 = new PointF(new Random().nextInt(totalWidth), new Random().nextInt(totalHeight / 2) + totalHeight / 2);
        PointF pointF2 = new PointF(new Random().nextInt(totalWidth), new Random().nextInt(totalHeight / 2));
        PointF endF = new PointF(new Random().nextInt(totalWidth), 0);

//        path.moveTo(startF.x, startF.y);
//        path.cubicTo(pointF1.x, pointF1.y, pointF2.x, pointF2.y, endF.x, endF.y);
//        invalidate();

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluate(pointF1, pointF2), startF, endF);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF animatedValue = (PointF) animation.getAnimatedValue();
                imageView.setX(animatedValue.x);
                imageView.setY(animatedValue.y);
                imageView.setAlpha(1 - animation.getAnimatedFraction());

            }
        });

//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0, 1);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0, 1);
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0,1);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0,1);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0,1);

        ValueAnimator valueAnimator1 = ObjectAnimator.ofPropertyValuesHolder(scaleX, alpha, scaleY);

//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(scaleX, scaleY, alpha);
//        animatorSet.setDuration(500);




        AnimatorSet totalSet = new AnimatorSet();
        totalSet.playTogether(valueAnimator1, valueAnimator);
        totalSet.start();
        totalSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);
            }
        });
    }
}
