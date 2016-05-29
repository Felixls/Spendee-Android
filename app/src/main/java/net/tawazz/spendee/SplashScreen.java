package net.tawazz.spendee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        long SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Redirect class
                Class<?> className = LoginActivity.class;
                Intent intent = new Intent(SplashScreen.this, className);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
