package se.mah.ae2942.da569aproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Splash activity, displays an image view for 2.5 seconds and then starts Main activity.
 */
public class SplashActivity extends AppCompatActivity {

    Animation mFadeInAnim;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_DURATION = 2500;

        ImageView iv_logo = (ImageView) findViewById(R.id.splash_iv_logo);
        mFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        handler = new Handler();

        iv_logo.startAnimation(mFadeInAnim);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_DURATION);
    }
}
