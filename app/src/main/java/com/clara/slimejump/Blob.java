package com.clara.slimejump;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * Created by we4954cp on 11/22/2016.
 */

public class Blob extends View {

    private static final String TAG = "BLOB VIEW";
    final float x = 100;   //blob stays in one place, scenery moves beneath it

    float gravityPull = 10;
    float jumpPush = 40;

    float maxY = 100;
    float groundY = Course.GROUND_LEVEL_Y;

    float y = groundY ; // how high on screen?

    float size = 30;

    Paint paint;

    public Blob(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.MAGENTA);

    }


    void jump() {

        if (y > maxY) {
            y -= jumpPush;
        }

    }

    void update(){

        if (y < groundY) {
            y += gravityPull;
        }

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y - (size), size, paint);
    }


}
