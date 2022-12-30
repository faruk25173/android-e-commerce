package com.naogaon.papas;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity
{
    ImageView img1,img2,img3;
    Animation top,bottom,left;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        img1=(ImageView)findViewById(R.id.logo);
        img2=(ImageView)findViewById(R.id.sublogo);
        img3=(ImageView)findViewById(R.id.sublogo2);

        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mainlogoanimation);
        bottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogoanimation);
        left= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogo2animation);

        img1.setAnimation(top);
        img2.setAnimation(bottom);
        img3.setAnimation(left);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }, 5000);
    }
}
