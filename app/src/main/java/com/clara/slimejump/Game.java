package com.clara.slimejump;

import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by clara on 11/23/2016.  This handles the state of the game. 
 */

public class Game {

    GameActivity activity;

    long period = 100;

    boolean gameOver;

    float screenWidth;
    float screenHeight;

    Blob blob;
    Course course;


    Game(GameActivity activity, int width, int height) {
        this.activity = activity;
        screenWidth = width;
        screenHeight = height;
        gameOver = true;
    }


    void start() {

        activity.removeGameComponents();

        activity.gameStart();

        ///mainView.removeView(course);
        //mainView.removeView(blob);
        course = new Course(activity, screenWidth, screenHeight);
        blob = new Blob(activity);

        activity.addGameComponent(blob);
        activity.addGameComponent(course);

        gameOver = false;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!collision()) {
                    activity.updateGameUI();
                    updateGameState();
                    handler.postDelayed(this, period);

                } else {
                    gameOver = true;
                    activity.gameOver();
                }
            }
        }, period);

    }

    private boolean collision() {
        return course.didCollideWithObstacle(blob);
    }


    private void updateGameState() {
        blob.update();
        course.update();
    }


    public void click() {

        if (gameOver) {
            gameOver = false;
            activity.gameStart();
            start();
        }

        //otherwise, jump!

        else {
            blob.jump();
        }

    }
}
