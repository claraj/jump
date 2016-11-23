package com.clara.slimejump;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;


/**
 * Created by clara on 11/22/2016.
 * Obstacles that the Blob must jump over.
 */

abstract public class Obstacle extends View {

    Path path;
    float x;
    Paint paint;
    float screenMaxX;

    float groundLevelY = Course.GROUND_LEVEL_Y;

    public Obstacle(Context context, float screenMax) {
        super(context);
        this.screenMaxX = screenMax;

        paint = new Paint();
        paint.setColor(Color.MAGENTA);   //Change this in subclasses if they should be different colors

        path = new Path();
    }

    public abstract boolean intersects(Blob blob);

}

class Stalagmite extends Obstacle {

    String TAG = "STALAGMITE";
    float height;

    float width = 50;

    Stalagmite(Context context, float screenMaxX, float height) {

        super(context, height);

        x = screenMaxX;
        this.height = height;

        //Path is triangle shape.
        path.moveTo(screenMaxX, groundLevelY);
        path.lineTo(screenMaxX + (width / 2), groundLevelY - height);
        path.lineTo(screenMaxX + width, groundLevelY);
        path.close();
    }

    @Override
    public boolean intersects(Blob blob) {

        //todo this doesn't work correctly, it just draws a box around the Triangle and tests to see if the blob intersects with it.
        //todo figure out correct math?

        //Know blob's center x and y
        //and this triangle's path

        RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        //is inside bound box?

        if ( blob.x + (blob.size/2) > bounds.left && blob.x - (blob.size/2) < bounds.right
                &&  blob.y + (blob.size/2) > bounds.top && blob.y - (blob.size/2) < bounds.bottom )
        {
            return true;
        }

        return false;
    }
}

class Stalactite extends Obstacle {

    String TAG = "STALACTITE";
    float height;

    float width = 50;

    Stalactite(Context context, float screenMaxX, float height) {

        super(context, height);

        x = screenMaxX;
        this.height = height;

        //Path is triangle shape.
        path.moveTo(screenMaxX, 0);
        path.lineTo(screenMaxX + (width / 2), 0 + height);
        path.lineTo(screenMaxX + width, 0);
        path.close();
    }

    @Override
    public boolean intersects(Blob blob) {

        //todo this doesn't work correctly, it just draws a box around the Triangle and tests to see if the blob intersects with it.
        //todo figure out correct math?

        //Know blob's center x and y
        //and this triangle's path

        RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        //is inside bound box?

        if ( blob.x + (blob.size/2) > bounds.left && blob.x - (blob.size/2) < bounds.right
                &&  blob.y + (blob.size/2) > bounds.top && blob.y - (blob.size/2) < bounds.bottom )
        {
            return true;
        }

        return false;
    }
}

class Box extends Obstacle {

    float height;
    float width = 30;

    public Box(Context context, float screenMax, float height) {
        super(context, screenMax);
        this.height = height;
        path.addRect(screenMaxX, groundLevelY - height, screenMaxX + width, groundLevelY, Path.Direction.CCW);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean intersects(Blob blob) {

        RectF bounds = new RectF();
        path.computeBounds(bounds, true);

        //is inside rectangle  bound box?

        if ( blob.x + (blob.size/2) > bounds.left && blob.x - (blob.size/2) < bounds.right
                &&  blob.y + (blob.size/2) > bounds.top && blob.y - (blob.size/2) < bounds.bottom )
        {
            return true;
        }

        return false;

    }
}

