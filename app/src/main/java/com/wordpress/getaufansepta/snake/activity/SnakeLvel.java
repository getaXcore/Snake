package com.wordpress.getaufansepta.snake.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.getaufansepta.snake.R;
import com.wordpress.getaufansepta.snake.service.serv;

public class SnakeLvel extends AppCompatActivity {
    TextView nLvl,mLvl,hLvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //start backsound
        startService(new Intent(SnakeLvel.this,serv.class));

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_snake_lvel);

        //initial for each level navigation
        init();

        //set on click for each level navigation
        nLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nLvl.setBackgroundResource(R.color.greenlight);
                Toast.makeText(SnakeLvel.this,"Level Normal",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SnakeLvel.this,Snake.class);
                intent.putExtra("lvl",0);
                startActivity(intent);
            }
        });
        mLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLvl.setBackgroundResource(R.color.greenlight);
                Toast.makeText(SnakeLvel.this,"Level Medium",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SnakeLvel.this,Snake.class);
                intent.putExtra("lvl",1);
                startActivity(intent);
            }
        });
        hLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hLvl.setBackgroundResource(R.color.greenlight);
                Toast.makeText(SnakeLvel.this,"Level Hard",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SnakeLvel.this,Snake.class);
                intent.putExtra("lvl",2);
                startActivity(intent);
            }
        });
    }

    private void init() {
        nLvl = (TextView)findViewById(R.id.txtlvlN);
        mLvl = (TextView)findViewById(R.id.txtlvlM);
        hLvl = (TextView)findViewById(R.id.txtlvlH);
    }
    public void onDestroy(){
        stopService(new Intent(SnakeLvel.this,serv.class));
        super.onDestroy();
    }
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (keyCode == keyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.minisnakeicon)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.text_exit)
                    .setCancelable(true)
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(i);
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode,keyEvent);
    }
}
