package se.mah.ae2942.da569aproject;

import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * GameActivity - clicker game with 4 different farmers that auto-increment the click counter.
 */
public class GameActivity extends AppCompatActivity {

    private ImageButton ibKid1, ibKid2, ibKid3, ibKid4, ibClicker, ibSound;
    private TextView tvScore;
    private ConstraintLayout mCL;
    private MediaPlayer mMediaPlayer;
    private int amountKid1, amountKid2, amountKid3, amountKid4, score = 0;
    private boolean runningState, runningState2, runningState3, runningState4, level2, level3, level4 = false;
    private boolean musicState, level5 = true;
    private Animation shake, click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initGUI();
        initOnClickListeners();

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.theme_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        click = AnimationUtils.loadAnimation(this, R.anim.click);

        new Thread(new LevelChange()).start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCL.setBackgroundColor(getResources().getColor(R.color.colorGame1));
                Toast.makeText(getApplicationContext(), "Level 1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        runningState = false;
        runningState2 = false;
        runningState3 = false;
        runningState4 = false;
        level5 = false;
    }

    /**
     * Init GUI components
     */
    private void initGUI() {
        mCL = (ConstraintLayout) findViewById(R.id.game_layout);
        ibKid1 = (ImageButton) findViewById(R.id.game_ib_kid1);
        ibKid2 = (ImageButton) findViewById(R.id.game_ib_kid2);
        ibKid3 = (ImageButton) findViewById(R.id.game_ib_kid3);
        ibKid4 = (ImageButton) findViewById(R.id.game_ib_kid4);
        ibClicker = (ImageButton) findViewById(R.id.game_ib_clicker);
        ibSound = (ImageButton) findViewById(R.id.game_ib_sound);
        tvScore = (TextView) findViewById(R.id.game_tv_score);
    }

    /**
     * Init GUI onClickListeners
     */
    private void initOnClickListeners() {
        ibClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibClicker.startAnimation(shake);
                updateScore(1);
            }
        });

        ibSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicState) {
                    ibSound.setBackgroundResource(R.drawable.btn_sound_off);
                    mMediaPlayer.pause();
                    musicState = false;
                } else {
                    ibSound.setBackgroundResource(R.drawable.btn_sound_on);
                    mMediaPlayer.start();
                    musicState = true;
                }
            }
        });

        ibKid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibKid1.startAnimation(click);
                if (score >= 100) {
                    updateScore(-100);
                    amountKid1++;
                    if (!runningState) {
                        runningState = true;
                        Toast.makeText(getApplicationContext(), "1+ click every second!", Toast.LENGTH_LONG).show();
                        new Thread(new KidFarmer1()).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "+" + amountKid1, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibKid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibKid2.startAnimation(click);
                if (score >= 1000) {
                    updateScore(-1000);
                    amountKid2++;
                    if (!runningState2) {
                        runningState2 = true;
                        Toast.makeText(getApplicationContext(), "50+ click every five seconds!", Toast.LENGTH_LONG).show();
                        new Thread(new KidFarmer2()).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "+" + (amountKid2 * 50), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibKid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibKid3.startAnimation(click);
                if (score >= 10000) {
                    updateScore(-10000);
                    amountKid3++;
                    if (!runningState3) {
                        runningState3 = true;
                        Toast.makeText(getApplicationContext(), "1000+ click every tenth seconds!", Toast.LENGTH_LONG).show();
                        new Thread(new KidFarmer3()).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "+" + (amountKid3 * 1000), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibKid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ibKid4.startAnimation(click);
                if (score >= 100000) {
                    updateScore(-100000);
                    amountKid4++;
                    if (!runningState4) {
                        runningState4 = true;
                        Toast.makeText(getApplicationContext(), "6000+ click every thirty seconds!", Toast.LENGTH_LONG).show();
                        new Thread(new KidFarmer4()).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "+" + (amountKid4 * 6000), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Kid farmer 1 - increments the counter with +1 every second.
     */
    private class KidFarmer1 implements Runnable {

        @Override
        public void run() {
            while (runningState) {
                System.out.println("KidFarmer1 - on");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(amountKid1);
                    }
                });
            }
        }
    }

    /**
     * Kid farmer 2 - increments the counter with +50 every five seconds.
     */
    private class KidFarmer2 implements Runnable {

        @Override
        public void run() {
            while (runningState2) {
                System.out.println("KidFarmer2 - on");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(amountKid2 * 50);
                    }
                });
            }
        }
    }

    /**
     * Kid farmer 3 - increments the counter with +1000 every tenth seconds.
     */
    private class KidFarmer3 implements Runnable {

        @Override
        public void run() {
            while (runningState3) {
                System.out.println("KidFarmer3 - on");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(amountKid3 * 1000);
                    }
                });
            }
        }
    }

    /**
     * Kid farmer 4 - increments the counter with +6000 every thirty seconds.
     */
    private class KidFarmer4 implements Runnable {

        @Override
        public void run() {
            while (runningState4) {
                System.out.println("KidFarmer4 - on");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(amountKid4 * 6000);
                    }
                });
            }
        }
    }

    /**
     * Runnable class, increases the game level when the score is high enough.
     */
    private class LevelChange implements Runnable {

        @Override
        public void run() {

            while (level5) {
                System.out.println("LevelChange - on");
                if (score >= 100 && score < 1000) {
                    if (!level2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCL.setBackgroundColor(getResources().getColor(R.color.colorGame2));
                                ibClicker.setImageResource(R.drawable.gelato_512_1);
                                Toast.makeText(getApplicationContext(), "Level 2", Toast.LENGTH_SHORT).show();
                            }
                        });
                        level2 = true;
                    }
                } else if (score >= 1000 && score < 10000) {
                    if (!level3) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCL.setBackgroundColor(getResources().getColor(R.color.colorGame3));
                                ibClicker.setImageResource(R.drawable.gelato_512_5);
                                Toast.makeText(getApplicationContext(), "Level 3", Toast.LENGTH_SHORT).show();

                            }
                        });
                        level3 = true;
                    }
                } else if (score >= 10000 && score < 100000) {
                    if (!level4) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCL.setBackgroundColor(getResources().getColor(R.color.colorGame4));
                                ibClicker.setImageResource(R.drawable.gelato_512_6);
                                Toast.makeText(getApplicationContext(), "Level 4", Toast.LENGTH_SHORT).show();

                            }
                        });
                        level4 = true;
                    }
                } else if (score >= 100000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCL.setBackgroundColor(getResources().getColor(R.color.colorGame5));
                            ibClicker.setImageResource(R.drawable.gelato_512_4);
                            Toast.makeText(getApplicationContext(), "Level 5", Toast.LENGTH_SHORT).show();

                        }
                    });
                    level5 = false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Increments the click counter
     *
     * @param incrementScore - int
     */
    private synchronized void updateScore(int incrementScore) {
        tvScore.setText(String.valueOf(score += incrementScore));
    }
}