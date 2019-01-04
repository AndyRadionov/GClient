package com.radionov.githubclient.ui;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import okhttp3.*;

import java.io.IOException;

import static com.radionov.githubclient.BuildConfig.*;

public class OAuthActivity extends AppCompatActivity {
    public static String TAG = "OAuthActivity";

    private TokenAsync tokenAsync;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = OAUTH_URL + "?client_id=" + CLIENT_ID;

        final WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String codeFragment = "code=";

                if (url.contains(codeFragment)) {
                    // the GET request contains an authorization code
                    final String accessCode = url.substring(url.indexOf(codeFragment) + codeFragment.length());
                    Log.d(TAG, "accessCode=" + accessCode);
                    //стопаем иначе редирект на callbackUrl
                    webview.stopLoading();

                    tokenAsync = new TokenAsync();
                    tokenAsync.execute(accessCode);

                }
            }


        });
        webview.loadUrl(url);
        setContentView(webview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tokenAsync != null && !tokenAsync.isCancelled()) {
            tokenAsync.cancel(true);
        }
    }

    private static class TokenAsync extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("client_id", CLIENT_ID)
                    .add("client_secret", CLIENT_SECRET)
                    .add("code", params[0])
                    .build();
            Request request = new Request.Builder().url(OAUTH_ACCESS_TOKEN_URL).post(formBody).build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Unexpected code " + response);
                } else {
                    //Сохранить в префы и выйти
                    Log.d(TAG, "token" + response.body().string());
                }
            } catch (IOException e) {
                Log.d(TAG, "IOEXc");
            }
            return null;
        }
    }
}