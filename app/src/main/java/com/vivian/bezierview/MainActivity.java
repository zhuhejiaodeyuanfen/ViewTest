package com.vivian.bezierview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vivian.bezierview.bubble.BubbleActivity;
import com.vivian.bezierview.locatemap.LocateMapActivity;
import com.vivian.bezierview.praise.PraiseActivity;
import com.vivian.bezierview.shop.ShopActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvWave, tvShop, tvPraise, tvBubble, tvLocationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvWave = findViewById(R.id.tvWave);
        tvShop = findViewById(R.id.tvShop);
        tvPraise = findViewById(R.id.tvPraise);
        tvBubble = findViewById(R.id.tvBubble);
        tvLocationMap = findViewById(R.id.tvLocationMap);
        tvWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WaveActivity.class);
                startActivity(intent);
            }
        });
        tvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);

            }
        });
        tvPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PraiseActivity.class);
                startActivity(intent);
            }
        });
        tvBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BubbleActivity.class);
                startActivity(intent);
            }
        });
        tvLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocateMapActivity.class);
                startActivity(intent);
            }
        });
    }
}
