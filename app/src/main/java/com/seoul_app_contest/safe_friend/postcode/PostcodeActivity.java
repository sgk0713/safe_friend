package com.seoul_app_contest.safe_friend.postcode;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.seoul_app_contest.safe_friend.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class PostcodeActivity extends AppCompatActivity {
    private String apiUrl = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd?ServiceKey=zx0GlL2RVyBHKsGY1QUduUR0txiL4ZEByp%2BZ5M1v%2FUbM4XxEV%2Brv%2FoFP5iKMpzGdeKvGpXg1wKL7E%2ByWoLdjEw%3D%3D&searchSe=dong&srchwrd=";

    @BindView(R.id.test)TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcode);
        ButterKnife.bind(this);
        Asynck asynck = new Asynck();
        asynck.execute();


    }
    class Asynck extends AsyncTask<String, Void, byte[]>{

        @Override
        protected byte[] doInBackground(String... strings) {
            String temp = "사동 1559-4";
            try {
                temp = URLEncoder.encode(temp, "EUC-KR");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                temp = URLDecoder.decode(temp, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String tt = null;
            try {
                tt = URLEncoder.encode(temp, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            apiUrl += tt;
            HttpURLConnection connection = null;

            URL url = null;
            try {
                url = new URL(apiUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept-language","ko");

            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = null;
            ByteArrayOutputStream baos = null;

            byte[] byteData = new byte[0];
            try {
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "EUC-KR"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is = connection.getInputStream();
                    baos = new ByteArrayOutputStream();

                    byte[] bytes = new byte[1024];
                    byteData = null;
                    int nLength = 0;

                    while ((nLength = is.read(bytes)) > 0) {
                        baos.write(bytes, 0, nLength);
                    }
                    byteData = baos.toByteArray();
                    Log.d("BEOM123", new String(byteData, "UTF-8"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return byteData;        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            try {
                Log.d("BEOM123", "1" + new String(bytes, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
