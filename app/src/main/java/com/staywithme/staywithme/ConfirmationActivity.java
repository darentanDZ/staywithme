package com.staywithme.staywithme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import javax.net.ssl.SSLContext;

import local.org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import local.org.apache.http.conn.ssl.SSLContexts;
import local.org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import local.org.apache.http.impl.client.CloseableHttpClient;
import local.org.apache.http.impl.client.HttpClients;

public class ConfirmationActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private EditText dateText;
    private EditText timeText;
    private EditText locationText;
    private boolean notificationFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // remove Status bar aka Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String address = getIntent().getStringExtra("Address");

        mVideoView = (VideoView) findViewById(R.id.videoView);
        dateText = (EditText) findViewById(R.id.dateText);
        timeText = (EditText) findViewById(R.id.timeText);
        locationText = (EditText) findViewById(R.id.locationText);

        dateText.setText(DateTimeActivity.dateString);
        timeText.setText(DateTimeActivity.timeString);
        locationText.setText(address);

        mVideoView.setVideoURI(MainActivity.mVideoUri);
        mVideoView.start();
//        mVideoView.pause();

        Button next_btn = (Button) this.findViewById(R.id.submitButton);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notificationFlag == false)
                {
                    thread.start();
                    notificationFlag = true;
                }
                else {
                    notificationFlag = false;
                    Intent m = new Intent(getBaseContext(), ViewMessageActivity.class);
                    startActivity(m);
                }
            }
        });
    }


    //Async Thread
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                SSLContext sslcontext = SSLContexts.custom()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();

                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                CloseableHttpClient httpclient = HttpClients.custom()
                        .setSSLSocketFactory(sslsf)
                        .build();
                Unirest.setHttpClient(httpclient);

                //HTTPRESPONSE
                HttpResponse<String> response = Unirest.post("https://developer.tm.com.my:8443/SMSSBV1/SMSImpl/SMSImplRS/SendMessage")
                        .header("content-type", "application/json")
                        .header("apitokenid", "2TS/Bd9QsqHdF2Na1i8pFkbXZ4Q=")
                        .header("partnerid", "sxz5hBCxIbcSUoIjqTzFO0tYCtU=")
                        .header("partnertokenid", "E6JpGTI98ccDzeuGp6+4vrDVVtg=")
                        .body("{\r\n  \"username\" : \"ceo\",\r\n  \"password\" : \"9b55148c9acf5d400756ec35eede5ee7e078b0ef\",\r\n  \"msgtype\" : \"text\",\r\n  \"message\" : \"Happy birthday my son. Unfortunately I am unable to celebrate with you.   One of my roomate in old folks home shown me this awesome app that I can connect with you even when I am not around anymore.   I wasn't a good father in the past, so I thought of making you presents for every birthday that I will missed.  \",\r\n  \"to\" : \"0163078949\",\r\n  \"hashkey\" : \"328e176d23f722d1ffd4ab9f8c1c0c34b8b97d12\",\r\n  \"filename\" : \"null\",\r\n  \"transcid\" : \"163078949\"\r\n}")
                        .asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
    });
}
