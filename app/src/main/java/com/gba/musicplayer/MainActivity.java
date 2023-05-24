package com.gba.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView rewind;
    ImageView play;
    ImageView pause;
    ImageView forward;
    SeekBar seekbar;
    TextView textView;


    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    double curPosTime = 0;
    double durationTime = 0;
    static int oneTimeOnly = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rewind = findViewById(R.id.rewindID);
        forward = findViewById(R.id.forwardID);
        play = findViewById(R.id.playID);
        pause = findViewById(R.id.pauseID);

        seekbar = findViewById(R.id.seekBarID);

        textView = findViewById(R.id.textID);

        mediaPlayer = MediaPlayer.create(this, R.raw.you);
        seekbar.setClickable(false);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                durationTime = mediaPlayer.getDuration();
                curPosTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int)durationTime);
                    oneTimeOnly = 1;
                }

                seekbar.setProgress((int)curPosTime);
                handler.postDelayed(UpdateSongTime, 100);
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPosTime - 1000 >= 0) {
                    curPosTime = curPosTime - 1000;
                    mediaPlayer.seekTo((int)curPosTime);
                }
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPosTime + 1000 <= durationTime) {
                    curPosTime = curPosTime + 1000;
                    mediaPlayer.seekTo((int)curPosTime);
                }
            }
        });



    }
    private final Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            curPosTime = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int)curPosTime);
            handler.postDelayed(this, 100);
        }
    };
}