package com.wordpress.getaufansepta.snake.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wordpress.getaufansepta.snake.R;
import com.wordpress.getaufansepta.snake.core.SnakeView;
import com.wordpress.getaufansepta.snake.modul.OnSwipeTouchListener;

public class Snake extends AppCompatActivity {
    private SnakeView mSnakeView;
    private static String ICICLE_KEY = "snake-view";
    public static LinearLayout linearbox;
    public static TextView txtscore;
    public static long mModeDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_snake);

        txtscore = (TextView)findViewById(R.id.txtscore);
        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text));

        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(SnakeView.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }

        //set mode level
        int lvl = getIntent().getIntExtra("lvl",0);
        if (lvl == 0){
            mModeDelay = 600; //normal
        }else if (lvl == 1){
            mModeDelay = 200; //medium
        }else if (lvl == 2){
            mModeDelay = 50; //hard
        }
        //Handle game controller
        setControll(mModeDelay);
    }

    private void setControll(final long lvlVal) {
        mSnakeView.setOnTouchListener(new OnSwipeTouchListener(Snake.this){
            @Override
            public void onSwipeTop() {
                if (SnakeView.mMode == SnakeView.READY || SnakeView.mMode == SnakeView.LOSE){
                    mSnakeView.initNewGame(lvlVal);
                    mSnakeView.setMode(SnakeView.RUNNING);
                    mSnakeView.update();
                }
                if (SnakeView.mMode == SnakeView.PAUSE){
                    mSnakeView.setMode(SnakeView.RUNNING);
                    mSnakeView.update();
                }
                if (SnakeView.mDirection != SnakeView.SOUTH ){
                    SnakeView.mNextDirection = SnakeView.NORTH;
                }
            }
            @Override
            public void onSwipeBottom() {
                if (SnakeView.mDirection != SnakeView.NORTH){
                    SnakeView.mNextDirection = SnakeView.SOUTH;
                }
            }
            @Override
            public void onSwipeLeft() {
                if (SnakeView.mDirection != SnakeView.EAST){
                    SnakeView.mNextDirection = SnakeView.WEST;
                }
            }

            @Override
            public void onSwipeRight() {
                if (SnakeView.mDirection != SnakeView.WEST){
                    SnakeView.mNextDirection = SnakeView.EAST;
                }
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store the game state
        super.onSaveInstanceState(outState);
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (keyCode == keyEvent.KEYCODE_BACK){
            if (SnakeView.mMode == SnakeView.READY || SnakeView.mMode == SnakeView.LOSE){
                //no action
                //return false;
            }else{
                //Pause the game
                mSnakeView.setMode(SnakeView.PAUSE);
            }

            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.minisnakeicon)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.text_end)
                    .setCancelable(true)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent a = new Intent(Snake.this,SnakeLvel.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(a);
                            finish();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode,keyEvent);
    }

}
