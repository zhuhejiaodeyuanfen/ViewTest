package com.vivian.bezierview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.vivian.bezierview.bubble.BubbleActivity;
import com.vivian.bezierview.locatemap.LocateMapActivity;
import com.vivian.bezierview.matrix.MatrixActivity;
import com.vivian.bezierview.money.MoneyActivity;
import com.vivian.bezierview.pathmeasure.PathMeasureActivity;
import com.vivian.bezierview.reflection.GetRun;
import com.vivian.bezierview.reflection.Reflection;
import com.vivian.bezierview.reflection.RunTest;
import com.vivian.bezierview.scroller.ScrollerActivity;
import com.vivian.bezierview.systembar.SystemBarUtils;
import com.vivian.bezierview.viewmeasure.ViewMeasureActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class Main2Activity extends Activity {
    private TextView tvWave, tvShop, tvPraise,
            tvBubble, tvLocationMap, tvPathMeasure, tvViewMeasure, tvScroller, tvMoney, tvMatrix, tvHandler;
    private View window;

    public static final String TAG = Main2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.lightStatusBar(this, Color.TRANSPARENT);
        setContentView(R.layout.activity_test);

        Intent[] intents = new Intent[2];
        intents[0] =new  Intent(Main2Activity.this, MoneyActivity.class);
        intents[1] = new Intent(Main2Activity.this, MatrixActivity.class);

        startActivities(intents);


        finish();
        doConnect();
        tvWave = findViewById(R.id.tvWave);
        tvShop = findViewById(R.id.tvShop);
        tvPraise = findViewById(R.id.tvPraise);
        tvBubble = findViewById(R.id.tvBubble);
        tvLocationMap = findViewById(R.id.tvLocationMap);
        tvPathMeasure = findViewById(R.id.tvPathMeasure);
        tvViewMeasure = findViewById(R.id.tvViewMeasure);
        tvScroller = findViewById(R.id.tvScroller);
        tvMoney = findViewById(R.id.tvMoney);
        tvMatrix = findViewById(R.id.tvMatrix);
        tvHandler = findViewById(R.id.tvHandler);
        rlChildApp=findViewById(R.id.rlChildApp);
        rlChildAppBack=findViewById(R.id.rlChildAppBack);
        window=findViewById(R.id.window);
        Reflection.getSth();
        GetRun getRun = new GetRun(new RunTest() {
            @Override
            public int run() {
                return 1;

            }
        });
        getRun.getSth();

        new Runnable() {
            @Override
            public void run() {

            }
        };
        tvHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
//            showAnimTv.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
                tvHandler.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, location[0],
                        Animation.ABSOLUTE, tvHandler.getX(),

                        Animation.ABSOLUTE, location[1],
                        Animation.ABSOLUTE, tvHandler.getY());
                translateAnimation.setDuration(2000);//动画执行时间
                translateAnimation.setFillAfter(false);//动画执行完成后保持状态
                //执行动画
                tvMatrix.setX(tvWave.getPivotX());
                tvMatrix.setY(tvWave.getPivotY());
                tvMatrix.startAnimation(translateAnimation);

            }
        });
        tvWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showView(v);
            }
        });
        tvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Main2Activity.this, ShopActivity.class);
//                startActivity(intent);
                showView(v);

            }
        });
        tvPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this, PraiseActivity.class);
//                startActivity(intent);
                showView(v);
            }
        });
        tvBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, BubbleActivity.class);
                startActivity(intent);
            }
        });
        tvLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, LocateMapActivity.class);
                startActivity(intent);
            }
        });
        tvPathMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, PathMeasureActivity.class);
                startActivity(intent);
            }
        });
        tvViewMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, ViewMeasureActivity.class);
                startActivity(intent);
            }
        });
        tvScroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, ScrollerActivity.class);
                startActivity(intent);
            }
        });
        tvMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MoneyActivity.class);
                startActivity(intent);
            }
        });
        tvMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //        获得TaskStackBuilder对象
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(Main2Activity.this);
//
//                Intent firstIntent = new Intent(Main2Activity.this, MoneyActivity.class);
//                Intent resultIntent = new Intent(Main2Activity.this, MatrixActivity.class);
//
//                //        addNextIntent()方法会添加Intent到任务的顶端，将当前app的Activity与另一app的Activity添加到一个由stackBuilder创建的新的任务中
//                stackBuilder.addNextIntent(firstIntent);
//                stackBuilder.addNextIntent(resultIntent);
////        获取一个PendingIntent去启动stackBuilder所创建的新任务
//                PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
////        发送PendingIntent
//                try {
//                    resultPendingIntent.send();
//                } catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }
            }
        });

        rlChildApp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rlChildApp.setVisibility(View.GONE);
                return true;
            }
        });
        rlChildApp.post(new Runnable() {
            @Override
            public void run() {
                rlChildAppBack.getLocationOnScreen(locationDest);
                rlChildApp.setVisibility(View.GONE);
            }
        });


    }

    private int[] locationDest=new int[2];


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private void doConnect() {


        new ConnectThread().start();


    }

    /**
     * 通过okhttpClient来设置证书
     *
     * @param clientBuilder OKhttpClient.builder
     * @param certificates  读取证书的InputStream
     */
    public void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                        .generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    private View rlChildApp,rlChildAppBack;

    private void showView(final View v) {

        int[] location = new int[2];
        v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标

        //设置动画属性


//        locations[0] = (float) (location[0] - location2[0]) / rlChildAppBackWidth;
//        locations[1] = (float) (location[1]) / window.getHeight();
        ScaleAnimation animation = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.ABSOLUTE,
                location[0]-locationDest[0]+v.getWidth()/2, Animation.ABSOLUTE, location[1]-locationDest[1]+v.getHeight()/2);


        animation.setDuration(3000);// 设置动画持续时间
        animation.setRepeatCount(0);// 设置重复次数
        rlChildApp.setVisibility(View.VISIBLE);

        rlChildAppBack.startAnimation(animation);
    }


        class ConnectThread extends Thread {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                InputStream is = null;
                try {
                    URL url = new URL("https://kyfw.12306.cn/otn/regist/init");
                    conn = (HttpURLConnection) url.openConnection();

//
//                //创建X.509格式的CertificateFactory
//                CertificateFactory cf = CertificateFactory.getInstance("X.509");
//                //从asserts中获取证书的流
//                InputStream cerInputStream = getAssets().open("test.cer");//SRCA.cer
//                //ca是java.security.cert.Certificate，不是java.security.Certificate，
//                //也不是javax.security.cert.Certificate
//                Certificate ca;
//                try {
//                    //证书工厂根据证书文件的流生成证书Certificate
//                    ca = cf.generateCertificate(cerInputStream);
//                    System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
//                } finally {
//                    cerInputStream.close();
//                }
//
//                // 创建一个默认类型的KeyStore，存储我们信任的证书
//                String keyStoreType = KeyStore.getDefaultType();
//                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//                keyStore.load(null, null);
//                //将证书ca作为信任的证书放入到keyStore中
//                keyStore.setCertificateEntry("ca", ca);
//
//                //TrustManagerFactory是用于生成TrustManager的，我们创建一个默认类型的TrustManagerFactory
//                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//                //用我们之前的keyStore实例初始化TrustManagerFactory，这样tmf就会信任keyStore中的证书
//                tmf.init(keyStore);
//                //通过tmf获取TrustManager数组，TrustManager也会信任keyStore中的证书
//                TrustManager[] trustManagers = tmf.getTrustManagers();
//
//                //创建TLS类型的SSLContext对象， that uses our TrustManager
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                //用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
//                sslContext.init(null, trustManagers, null);
//
//                //通过sslContext获取SSLSocketFactory对象
//                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//                //将sslSocketFactory通过setSSLSocketFactory方法作用于HttpsURLConnection对象
//                //这样conn对象就会信任我们之前得到的证书对象
//                conn.setSSLSocketFactory(sslSocketFactory);
//
//                conn.setDefaultHostnameVerifier( new HostnameVerifier(){
//                    public boolean verify(String string,SSLSession ssls) {
//                        return true;
//                    }
//                });

                    is = conn.getInputStream();
                    //将得到的InputStream转换为字符串
                    final String str = convertStreamToString(is);
                    Log.i(TAG, "收到的消息" + conn.getResponseCode()
                            + "code码" + str);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "收到的消息" + e.getMessage());

                }
            }
        }
    }
