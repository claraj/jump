package com.clara.slimejump;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by clara on 11/22/2016. The scenery and obstacles.
 */

public class Course extends View {

    public static float GROUND_LEVEL_Y = 300;
    private static final String TAG = "COURSE VIEW";
    public final float SCREEN_MAX_X;

    int clockTicksSinceLastObstacle = 0;
    int timeUntilNewObstacle = 0;
    int maxTimeUntilNewObstacle = 30; //no more than 3 seconds between obstacles

    Random rnd = new Random(System.currentTimeMillis());

    Context context;

    ArrayList<Obstacle> obstacles;

    Paint paint;

    Matrix slideLeftMatrix;

    float obstacleSpeed = 30;

    public Course(Context context, float screenmax, float screenheight) {
        super(context);
        this.context = context;
        SCREEN_MAX_X = screenmax;
        GROUND_LEVEL_Y = screenheight / 3 * 2;   // two thirds down screen

        timeUntilNewObstacle = rnd.nextInt(maxTimeUntilNewObstacle);
        obstacles = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStrokeWidth(10);

        slideLeftMatrix = new Matrix();
        slideLeftMatrix.setTranslate(-obstacleSpeed, 0);

    }

    void update() {

        clockTicksSinceLastObstacle++;

        if (timeUntilNewObstacle < clockTicksSinceLastObstacle) {
            clockTicksSinceLastObstacle = 0;
            timeUntilNewObstacle = rnd.nextInt(maxTimeUntilNewObstacle);
            addRandomObstacle();
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.path.transform(slideLeftMatrix);
        }

    }


    private void addRandomObstacle() {

        Obstacle obstacle = null;
        int random = rnd.nextInt(2);
        switch (random) {
            case 0 : {
                obstacle = new Stalagmite(context, SCREEN_MAX_X, 50 + rnd.nextInt(40));
                break;
            }
            case 1 : {
                obstacle = new Box(context, SCREEN_MAX_X, 30 + rnd.nextInt(70));
                break;
            }

            default: {
                Log.e(TAG, "error, don't know what type of obstacle to add.");
            }
        }

        if (obstacle != null) {
            obstacles.add(obstacle);
        }
    }


    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawLine(0, GROUND_LEVEL_Y, SCREEN_MAX_X, GROUND_LEVEL_Y, paint);

        for (Obstacle obstacle : obstacles) {

            if (obstacle.x < 0) {
                obstacle = null;
            }

            Log.d(TAG, "drawing " + obstacle + " " + obstacle.path);
            canvas.drawPath(obstacle.path, paint);
        }

        //remove null obstacles, start at end of list and work through to front
        for (int x = obstacles.size()-1; x >= 0 ; x--) {
            if (obstacles.get(x) == null) {
                obstacles.remove(x);
            }
        }
    }


    public boolean didCollideWithObstacle(Blob blob) {

        //has blob hit obstacle?

        for (Obstacle obstacle : obstacles) {
            if (obstacle.intersects(blob)) {
                return true;
            }
        }

        return false;

    }
}
