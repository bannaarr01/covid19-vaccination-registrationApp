package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;//3000; //3secs
    Animation topAnim, bottomAnim;
    ImageView img;
    TextView splashTextV, bottomSplashT;
    DatabaseManager mDatabase;
    private String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Adding the default admin values to the database
        mEmail = "bannarr2021@gmail.com";
        mPassword = "1234567890";
        mDatabase = new DatabaseManager(this);
        if (!mDatabase.checkLogin(mEmail, mPassword)) {
            mDatabase.addUser(" ", "bannarr2021@gmail.com", "1234567890", "Adedigba Joshua",
                    0, "Male", "C0523189", "01111693190", "Mobile", "Negeri Sembilan", "INTI International University, Nilai, 71800", "00-00-0000", "00:00", 1);
        }

        //Changing statusBarColor and Hiding NavBar at Startup for FullScreen Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.appColor, this.getTheme()));

            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.activity_main);

        //Hooks
        img = findViewById(R.id.logo);
        splashTextV = findViewById(R.id.splashtextV);
        bottomSplashT = findViewById(R.id.bottomsplashtext);

        //animation, Resource.top and bottom animation XML files
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Assigning the animation to the image and text
        img.setAnimation(topAnim);
        splashTextV.setAnimation(bottomAnim);
        bottomSplashT.setAnimation(bottomAnim);

        //Manage d delay and using intent to open new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish(); //REMOVE / End DS ACTIVITY FROM ACTIVITY LIST when done else will return to ds activity
            }
        }, SPLASH_SCREEN);//The time-base is SystemClock.uptimeMillis(). Time spent in deep sleep will add an additional delay to execution.
    }
}