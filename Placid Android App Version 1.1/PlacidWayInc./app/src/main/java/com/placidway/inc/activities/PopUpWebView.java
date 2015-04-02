package com.placidway.inc.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;

import com.placidway.inc.R;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.modal.MedicalCenterInfo;

/**
 * Created by erum on 06/11/2014.
 */
public class PopUpWebView extends Activity
{
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_web);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        webView = (WebView) findViewById(R.id.wv_pop_up);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = PlacidWay.getInstance().popUpUrl;
        webView.loadUrl(url);
    }
}
