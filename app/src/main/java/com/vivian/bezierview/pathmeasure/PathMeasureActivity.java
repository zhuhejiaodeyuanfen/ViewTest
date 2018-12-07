package com.vivian.bezierview.pathmeasure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vivian.bezierview.R;

public class PathMeasureActivity extends AppCompatActivity {

    private TextView textView;
    private Handler handler = new Handler() {

        // 该方法运行在主线程中
        // 接收到handler发送的消息，对UI进行操作
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x123) {
                textView.setText("呵呵。。");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 在此执行耗时工作，执行完毕后调用handler发送消息
                try {
                    Thread.sleep(6000);//睡眠6秒 模拟执行耗时任务
                    handler.sendEmptyMessage(0x123);//发送消息
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
