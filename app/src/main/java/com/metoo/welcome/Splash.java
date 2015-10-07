package com.metoo.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.metoo.MainActivity;
import com.metoo.R;

/**
 * Splash为第一次启动的Activity
 * 判断是否加载引导页
 */
public class Splash extends AppCompatActivity {
    private boolean isFirstIn = false;//判断应用是否是第一次启动
    private static final long SPLASH_DISPLAY_LENGHT=3000;//引导页面停留时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         * @TODO 实现第一次加载应用
         */
        SharedPreferences preferences = getSharedPreferences("first_pref",
                MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isFirstIn) {
                    // 加载引导页
                    intent = new Intent(Splash.this, Welcome.class);
                } else {
                    // 加载MainActivity
                    intent = new Intent(Splash.this, MainActivity.class);
                }
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
        //---------结束：实现第一次加载应用-------
    }
}
