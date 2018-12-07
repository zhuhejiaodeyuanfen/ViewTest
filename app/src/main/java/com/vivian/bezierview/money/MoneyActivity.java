package com.vivian.bezierview.money;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.vivian.bezierview.R;

public class MoneyActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        Log .i(MoneyActivity.class.getName(),"onCreate");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log .i(MoneyActivity.class.getName(),"onResume");
    }
}
