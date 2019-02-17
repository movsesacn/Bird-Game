package com.example.bird;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GameView.OnGameOver {

    private static final long TIMER_INTERVAL = 30;

    private GameView mGameView;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGameView = new GameView(this);

        setContentView(mGameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGameView.invalidate();
                    }
                });
            }
        },0, TIMER_INTERVAL);
    }

    @Override
    public void onGameOver(int score) {
        Intent intent = new Intent(this, GameResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
