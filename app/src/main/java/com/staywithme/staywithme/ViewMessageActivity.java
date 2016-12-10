package com.staywithme.staywithme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class ViewMessageActivity extends AppCompatActivity {

    private String PATH_TO_FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
    }

    public void viewMessage(View view) {
        /*MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(R.raw.testvideo);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();*/

        /*MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.testvideo );
        mediaPlayer.start();*/

        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }
}
