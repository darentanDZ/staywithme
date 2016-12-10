package com.staywithme.staywithme;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import java.text.SimpleDateFormat;

public class ConfirmationActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private EditText dateText;
    private EditText timeText;
    private EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mVideoView = (VideoView) findViewById(R.id.videoView);
        dateText = (EditText) findViewById(R.id.dateText);
        timeText = (EditText) findViewById(R.id.timeText);
        locationText = (EditText) findViewById(R.id.locationText);

//        dateText.setText();
//        timeText.setText(cHour + ":" + cMinute);
//        locationText.setText();

        mVideoView.setVideoURI(MainActivity.mVideoUri);
        mVideoView.start();
        mVideoView.pause();

    }
}