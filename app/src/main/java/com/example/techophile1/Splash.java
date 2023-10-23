package com.example.techophile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    Animation top , bottom ;
    TextView logo , slogan ;
    ImageView topR;
    ProgressBar pb ;
    ImageView draw ;

    int progress = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Hide action & status bar
        //getSupportActionBar().hide();
//        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this , R.color.startColor));
        top = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        logo = findViewById(R.id.appTxt);
        slogan = findViewById(R.id.mottoTxt);
        topR = findViewById(R.id.topRect);

        logo.setAnimation(bottom);
        slogan.setAnimation(bottom);
        topR.setAnimation(top);

        pb = findViewById(R.id.pb);
        pb.setAnimation(bottom);
        pbAnim();

        // Drawable animation
        draw = findViewById(R.id.logoAnim);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) draw.getDrawable();
        // slow the animation

        avd.start();

        // splash screen load
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.content.Intent intent = new android.content.Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    public void pbAnim(){
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                pb.setProgress(progress);
                progress++ ;
                if(progress == 100){
                    t.cancel();
                }
            }
        };
        t.schedule(tt , 0 , 40);
    }
}