package com.vivian.bezierview.matrix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vivian.bezierview.R;

public class MatrixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        Log.i(MatrixActivity.class.getName(),"onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MatrixActivity.class.getName(),"onResume");
    }
}
