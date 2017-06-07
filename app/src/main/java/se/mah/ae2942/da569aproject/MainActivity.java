package se.mah.ae2942.da569aproject;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Main activity, menu with 3 image buttons and a scrolling background.
 */
public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlay, btnInfo, btnAbout;
    private ImageView ivBack1, ivBack2, ivBack3;
    private Animation click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = AnimationUtils.loadAnimation(this, R.anim.click);

        initGui();
        initGUIOnClickListeners();

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f, 2.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = ivBack1.getWidth();
                final float translationX = width * progress;
                ivBack1.setTranslationX(translationX);
                ivBack2.setTranslationX(translationX - width);
                ivBack3.setTranslationX(translationX - (width * 2));
            }
        });
        animator.start();
    }

    private void initGui(){
        ivBack1 = (ImageView) findViewById(R.id.main_iv_back1);
        ivBack2 = (ImageView) findViewById(R.id.main_iv_back2);
        ivBack3 = (ImageView) findViewById(R.id.main_iv_back3);

        btnPlay = (ImageButton) findViewById(R.id.main_ib_play);
        btnInfo = (ImageButton) findViewById(R.id.main_ib_info);
        btnAbout = (ImageButton) findViewById(R.id.main_ib_about);
    }

    private void initGUIOnClickListeners(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.startAnimation(click);
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnInfo.startAnimation(click);
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAbout.startAnimation(click);
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
