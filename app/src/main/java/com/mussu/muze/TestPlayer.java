package com.mussu.muze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mussu.muze.R;

import java.io.File;
import java.util.ArrayList;


public class TestPlayer extends AppCompatActivity {

    // Instantiating the MediaPlayer class
    MediaPlayer music;
    SeekBar sb;
    Thread updateSeekBar;
    TextView songNameText;

    @Override
    protected void onCreate(
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player);

        // Adding the music file to our
        // newly created object music
        music = MediaPlayer.create(this, R.raw.sound);

        songNameText = (TextView) findViewById(R.id.txtSongLabel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Now Playing");

        songNameText.setText("Pirates of the caribbean Theme song");
        songNameText.setSelected(true);

        updateSeekBar=new Thread(){
            @Override
            public void run(){
                int totalDuration = music.getDuration();
                int currentPosition = 0;
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition=music.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        };

        music.start();

    }


    // Plaing the music
    public void musicplay(View v)
    {
        if(music.isPlaying()) {
            music.pause();
        }
        else{
            music.start();
        }
    }

    public void seekback(View v)
    {
        music.seekTo(music.getCurrentPosition()-10000);
    }

    public void seekforward(View v)
    {
        music.seekTo(music.getCurrentPosition()+10000);
    }

    public void seekbar()
    {
        sb.setOnSeekBarChangeListener(new
                                              SeekBar.OnSeekBarChangeListener() {
                                                  @Override
                                                  public void onProgressChanged(SeekBar seekBar, int i,
                                                                                boolean b) {
                                                  }
                                                  @Override
                                                  public void onStartTrackingTouch(SeekBar seekBar) {
                                                  }
                                                  @Override
                                                  public void onStopTrackingTouch(SeekBar seekBar) {
                                                      music.seekTo(seekBar.getProgress());

                                                  }
                                              });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
