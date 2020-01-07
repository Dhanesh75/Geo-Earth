package com.example.android.quakereport;

/**
 * Created by hp on 04-10-2018.
 */




import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                setTheme(R.style.AppTheme);

                Intent i = new Intent(Splash.this, EarthquakeActivity.class);
                startActivity(i);
                finish();
            }
        }, 200);
    }
}

