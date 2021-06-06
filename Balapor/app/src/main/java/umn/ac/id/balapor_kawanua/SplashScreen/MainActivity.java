package umn.ac.id.balapor_kawanua.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import umn.ac.id.balapor_kawanua.Login.LoginActivity;
import umn.ac.id.balapor_kawanua.R;

public class MainActivity extends AppCompatActivity {

//    Inisilisasi animation
    Animation splash_bottom_animation, splash_middle_animation, splash_middle_animation2;
//    Inisialisasi View
    View blue1, blue2, blue3, blue4, blue5, logo_sulut, logo_balapor;

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        splash_bottom_animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.splash_bottom_animation);
        splash_middle_animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.splash_middle_animation);
        splash_middle_animation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.splash_middle_animation2);

        blue1 = findViewById(R.id.blue1);
        blue2 = findViewById(R.id.blue2);
        blue3 = findViewById(R.id.blue3);
        blue4 = findViewById(R.id.blue4);
        blue5 = findViewById(R.id.blue5);
        logo_sulut = findViewById(R.id.logo_sulut);
        logo_balapor = findViewById(R.id.logo_balapor);

        blue1.setAnimation(splash_bottom_animation);
        blue2.setAnimation(splash_bottom_animation);
        blue3.setAnimation(splash_bottom_animation);
        blue4.setAnimation(splash_bottom_animation);
        blue5.setAnimation(splash_bottom_animation);

        logo_balapor.setAnimation(splash_middle_animation2);
        logo_sulut.setAnimation(splash_middle_animation);

        gotoLoginPage();
    }

    private void gotoLoginPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginPage);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}