package com.vivian.bezierview.wave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vivian.bezierview.R;

public class WaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wave);
        int name = getIntent().getIntExtra("name", 1);
        Log.i(WaveActivity.class.getName(), "获得的数据" + name);
    }
}
