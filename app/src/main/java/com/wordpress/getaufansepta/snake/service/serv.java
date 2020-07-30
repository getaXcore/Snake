package com.wordpress.getaufansepta.snake.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wordpress.getaufansepta.snake.R;

/**
 * Created by Taufan Septaufani on 09-May-18.
 */

public class serv extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        mediaPlayer = MediaPlayer.create(this, R.raw.backtune);
        mediaPlayer.setLooping(false);
    }
    public void onDestroy(){
        mediaPlayer.stop();
    }
    public void onStart(Intent intent,int startid){
        Log.d("onStart","backtune");
        mediaPlayer.start();
    }
    public int onStartCommand(Intent intent,int flags,int startid){
        Log.d("onStartCommand","backtune");
        mediaPlayer.start();

        return START_NOT_STICKY;
    }
}
