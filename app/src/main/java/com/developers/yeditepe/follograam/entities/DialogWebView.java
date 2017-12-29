package com.developers.yeditepe.follograam.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.developers.yeditepe.follograam.R;
import com.developers.yeditepe.follograam.utils.Constans;
import com.developers.yeditepe.follograam.interfaces.AuthenticateListener;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class DialogWebView extends AppCompatDialog {

    private WebView authorizatonWebView;
    private AuthenticateListener authenticateListener;
    private boolean isDone = false;

    public DialogWebView(Context context, boolean cancelable, OnCancelListener cancelListener, AuthenticateListener authenticateListener) {
        super(context, cancelable, cancelListener);
        this.authenticateListener = authenticateListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_web_view);
        authorizatonWebView = findViewById(R.id.web_view_authorization);
        authorizatonWebView.getSettings().setJavaScriptEnabled(true);
        authorizatonWebView.getSettings().setLoadWithOverviewMode(true);
        authorizatonWebView.getSettings().setUseWideViewPort(true);
        authorizatonWebView.getSettings().setBuiltInZoomControls(true);
        authorizatonWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        authorizatonWebView.getSettings().setSavePassword(true);
        authorizatonWebView.getSettings().setSaveFormData(true);

        authorizatonWebView.setWebViewClient(new InstagramWebViewClient());
        authorizatonWebView.loadUrl(getAuthorizationURL());


    }

    private String getAuthorizationURL() {
        return Constans.AUTH_URL + "?client_id=" + Constans.CLIENT_ID + "&redirect_uri=" + Constans.REDIRECT_URL + "&response_type=code&scope=basic+public_content+follower_list+likes+comments+relationships";
    }

    private class InstagramWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            URL aURL = null;

            try {
                aURL = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if (url.startsWith(Constans.REDIRECT_URL) && !isDone){
                String[] parsedUrl = aURL.getQuery().split("=");
                if (parsedUrl[0].contains("error")){
                    authenticateListener.onAuthenticate(false, parsedUrl[1]);
                } else if (parsedUrl[0].equals("code")){
                    authenticateListener.onAuthenticate(true, parsedUrl[1]);
                }
                isDone = true;
                dismiss();
            }
        }
    }
}
