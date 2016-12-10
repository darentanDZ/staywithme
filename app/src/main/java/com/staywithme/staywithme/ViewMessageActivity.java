package com.staywithme.staywithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import local.org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import local.org.apache.http.conn.ssl.SSLContexts;
import local.org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import local.org.apache.http.impl.client.CloseableHttpClient;
import local.org.apache.http.impl.client.HttpClients;


public class ViewMessageActivity extends AppCompatActivity {

    public ViewMessageActivity() throws UnirestException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        // remove Status bar aka Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //thread.start();

    }

    public void viewMessage(View view) throws IOException {

        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }


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
                        .body("{\r\n  \"username\" : \"ceo\",\r\n  \"password\" : \"9b55148c9acf5d400756ec35eede5ee7e078b0ef\",\r\n  \"msgtype\" : \"text\",\r\n  \"message\" : \"HELLO WORLD\",\r\n  \"to\" : \"0163078949\",\r\n  \"hashkey\" : \"328e176d23f722d1ffd4ab9f8c1c0c34b8b97d12\",\r\n  \"filename\" : \"null\",\r\n  \"transcid\" : \"163078949\"\r\n}")
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
