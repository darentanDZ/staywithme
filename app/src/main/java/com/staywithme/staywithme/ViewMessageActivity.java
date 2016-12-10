package com.staywithme.staywithme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewMessageActivity extends AppCompatActivity {

    private String PATH_TO_FILE;

    OkHttpClient client = new OkHttpClient();

    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(mediaType, "{\r\n  \"username\" : \"ceo\",\r\n  \"password\" : \"9b55148c9acf5d400756ec35eede5ee7e078b0ef\",\r\n  \"msgtype\" : \"text\",\r\n  \"message\" : \"HELLO WORLD\",\r\n  \"to\" : \"0163078949\",\r\n  \"hashkey\" : \"328e176d23f722d1ffd4ab9f8c1c0c34b8b97d12\",\r\n  \"filename\" : \"null\",\r\n  \"transcid\" : \"163078949\"\r\n}");
    Request request = new Request.Builder()
            .url("https://developer.tm.com.my:8443/SMSSBV1/SMSImpl/SMSImplRS/SendMessage")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("apitokenid", "2TS/Bd9QsqHdF2Na1i8pFkbXZ4Q=")
            .addHeader("partnerid", "sxz5hBCxIbcSUoIjqTzFO0tYCtU=")
            .addHeader("partnertokenid", "E6JpGTI98ccDzeuGp6+4vrDVVtg=")
            .addHeader("cache-control", "no-cache")
            .addHeader("postman-token", "241c941f-d68e-5b7e-d7e8-7f13aabb25ee")
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        //thread.start();
    }

    public void viewMessage(View view) throws IOException {

        /*OkHttpClient client = new OkHttpClient();
        String url = "https://developer.tm.com.my:8443/SMSSBV1/SMSImpl/SMSImplRS/SendMessage";
        String json = "{\n" +
                "  \"username\" : \"ceo\",\n" +
                "  \"password\" : \"9b55148c9acf5d400756ec35eede5ee7e078b0ef\",\n" +
                "  \"msgtype\" : \"text\",\n" +
                "  \"message\" : \"HELLO WORLD\",\n" +
                "  \"to\" : \"0163078949\",\n" +
                "  \"hashkey\" : \"328e176d23f722d1ffd4ab9f8c1c0c34b8b97d12\",\n" +
                "  \"filename\" : \"null\",\n" +
                "  \"transcid\" : \"163078949\"\n" +
                "}";

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String post(String url, String json) throws IOException
        {
            RequestBody body = RequestBody.create(mediaType, "json");
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("apitokenid", "2TS/Bd9QsqHdF2Na1i8pFkbXZ4Q=")
                    .addHeader("partnerid", "sxz5hBCxIbcSUoIjqTzFO0tYCtU=")
                    .addHeader("partnertokenid", "E6JpGTI98ccDzeuGp6+4vrDVVtg=")
                    .build();
            try (Response response = client.newCall(request).execute();
            return response.body().string();
        }
*/



        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }


    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}
