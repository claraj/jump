package com.clara.slimejump;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Blob blob;
    Course course;

    long period = 100;

    TextView gameOverTv;

    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        FrameLayout contentView = (FrameLayout) findViewById(android.R.id.content);
        contentView.setOnClickListener(this);

        gameOverTv = (TextView) findViewById(R.id.game_over_message);
        gameOverTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {

            FrameLayout mainView =  (FrameLayout) findViewById(android.R.id.content);
            float screenWidth = mainView.getWidth();
            blob = new Blob(this);
            course = new Course(this, screenWidth);

            mainView.addView(blob);
            mainView.addView(course);

            start();
        }
    }

    private void start() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update();

                if (!collision()) {
                    handler.postDelayed(this, period);
                } else {
                    gameOver = true;
                    gameOverTv.setVisibility(View.VISIBLE);
                }
            }
        }, period);

    }

    private boolean collision() {
        return course.collideWithObstacle(blob);
    }

    private void update() {
        blob.update();
        course.update();
        blob.invalidate();
        course.invalidate();
    }

    @Override
    public void onClick(View view) {
        //jump!

        if (gameOver) {
            gameOver = false;
            gameOverTv.setVisibility(View.INVISIBLE);
        }

        else {
            blob.jump();
        }

    }
}
