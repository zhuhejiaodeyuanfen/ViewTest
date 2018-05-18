package com.vivian.bezierview.shop;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vivian.bezierview.R;

public class ShopActivity extends AppCompatActivity {
    private ImageView ivCar, ivRemove, ivAdd;
    private int[] addView = new int[2];
    private int[] shopView = new int[2];
    private int[] parentView = new int[2];
    private RelativeLayout rlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ivCar = findViewById(R.id.ivCar);
        rlMain = findViewById(R.id.rlMain);
        ivRemove = findViewById(R.id.ivRemove);
        ivAdd = findViewById(R.id.ivAdd);


        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAdd.getLocationInWindow(addView);
                rlMain.getLocationInWindow(parentView);
                ivCar.getLocationInWindow(shopView);

                final PointF startF = new PointF(addView[0] - parentView[0], addView[1] - parentView[1]);
                final PointF endF = new PointF(shopView[0] - parentView[0], shopView[1] - parentView[1]);
                final PointF controllerF = new PointF(endF.x, startF.y);
                final ImageView imageView = new ImageView(ShopActivity.this);
                imageView.setImageResource(R.mipmap.ic_car);
                imageView.setScaleX(0);
                imageView.setScaleY(0);
                rlMain.addView(imageView);
                imageView.setX(startF.x);
                imageView.setY(startF.y);

                ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluate(controllerF), startF, endF);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF = (PointF) animation.getAnimatedValue();
                        imageView.setX(pointF.x);
                        imageView.setY(pointF.y);
                        imageView.setScaleY(animation.getAnimatedFraction());
                        imageView.setScaleX(animation.getAnimatedFraction());
                    }
                });
                valueAnimator.setDuration(1000);
                valueAnimator.setTarget(imageView);
                valueAnimator.start();

            }
        });
    }
}
