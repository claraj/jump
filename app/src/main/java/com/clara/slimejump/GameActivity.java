package com.clara.slimejump;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView gameOverTv;
    FrameLayout contentView;

    int background, actors;   //Colors, not currently used

    ArrayList<View> gameComponents;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        contentView = (FrameLayout) findViewById(android.R.id.content);
        contentView.setOnClickListener(this);

        background = ContextCompat.getColor(this, R.color.background);
        actors = ContextCompat.getColor(this, R.color.foreground);

        gameOverTv = (TextView) findViewById(R.id.game_over_message);

        gameComponents = new ArrayList<>();

        gameComponents.add(gameOverTv);

    }


    void gameStart() {
        gameOverTv.setVisibility(View.INVISIBLE);
    }

    void gameOver() {
        gameOverTv.setVisibility(View.VISIBLE);

    }


    void addGameComponent(View view) {
        gameComponents.add(view);
        contentView.addView(view);
    }

    void removeGameComponents() {
        for (View view : gameComponents) {
            contentView.removeView(view);
        }
    }

    void updateGameUI() {
        for (View view : gameComponents) {
            view.invalidate();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {

            game = new Game(this, contentView.getWidth(), contentView.getHeight());
        }
    }


    @Override
    public void onClick(View view) {

        // if game over, restart.
        game.click();

    }
}
