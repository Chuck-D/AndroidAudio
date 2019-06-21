package com.example.androidaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    //define an audiomanager

    AudioManager audioManager;

    public void play(View view){

        mediaPlayer.start();
    }

    public void pause(View view){

        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup the audio manager

        audioManager =(AudioManager) getSystemService(AUDIO_SERVICE);

        //get max volume for current device
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //get current volume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer = MediaPlayer.create(this, R.raw.rain);

        //find seekbar

        SeekBar volumeControl = findViewById(R.id.volumeSeekBar);


        //set max volume
        volumeControl.setMax(maxVolume);

        //set slider to current volume
        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Seekbar changed", Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0 );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar seekControl = findViewById(R.id.scrubSeekBar);
        seekControl.setMax(mediaPlayer.getDuration());


        seekControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Scrub Seekbar changed", Integer.toString(i));
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 300);

    }
}
