package com.vivian.bezierview.praise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vivian.bezierview.R;

public class PraiseActivity extends AppCompatActivity {

    private Button btnPraise;

    private PraiseView praiseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise);
        btnPraise = findViewById(R.id.btnPraise);
        praiseView = findViewById(R.id.praiseView);

        btnPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praiseView.addPraise();
            }
        });
    }
}
