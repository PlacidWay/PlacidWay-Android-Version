package com.placidway.inc.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.placidway.inc.R;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.modal.FeaturedCenterInfo;
import com.placidway.inc.modal.MedicalCenterInfo;

public class DetailListActivity extends Activity {

    MedicalCenterInfo medInfo;
    FeaturedCenterInfo feaInfo;

    TextView tv_phone;
    TextView tv_email;
    TextView tv_loc;
    TextView tv_center_name;

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        tv_phone = (TextView)findViewById(R.id.tv_phone); // title
        tv_email = (TextView)findViewById(R.id.tv_email); // title
        tv_loc = (TextView)findViewById(R.id.tv_loc); // title
        tv_center_name = (TextView)findViewById(R.id.tv_center_name); // title

        tv_phone.setText("+1.303.500.3821");
        tv_email.setText("info@placidway.com");


        String address = "";

        if(PlacidWay.getInstance().isPriceFeature)
        {
            feaInfo = PlacidWay.getInstance().getFeaInfo();

            if (!feaInfo.getCity().equals(""))
            {
                address += feaInfo.getCity() + " | ";
            }
            if (!feaInfo.getState().equals(""))
            {
                address += feaInfo.getState() + " | ";
            }
            if (!feaInfo.getCountry().equals(""))
            {
                address += feaInfo.getCountry();
            }
            tv_loc.setText(address);

            tv_center_name.setText(feaInfo.getCenter_name());

            webView = (WebView) findViewById(R.id.wv_detail);
            webView.getSettings().setJavaScriptEnabled(true);

            String url  = "http://placidway:placidway@" +
                    getResources().getString(R.string.UrlBase2) +
                    feaInfo.getDetail_link();
            webView.loadUrl(url);

            PlacidWay.getInstance().popUpUrl = url;
        }
        else
        {
            medInfo = PlacidWay.getInstance().getMedInfo();

            if (!medInfo.getCity().equals(""))
            {
                address += medInfo.getCity() + " | ";
            }
            if (!medInfo.getState().equals(""))
            {
                address += medInfo.getState() + " | ";
            }
            if (!medInfo.getCountry().equals(""))
            {
                address += medInfo.getCountry();
            }
            tv_loc.setText(address);

            tv_center_name.setText(medInfo.getTitle());

            webView = (WebView) findViewById(R.id.wv_detail);
            webView.getSettings().setJavaScriptEnabled(true);

            String url  = "http://placidway:placidway@" +
                    getResources().getString(R.string.UrlBase2) +
                    medInfo.getDetail_link();
            webView.loadUrl(url);

            PlacidWay.getInstance().popUpUrl = url;

        }




        Button btn_call = (Button) findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                goCall();
            }
        });
        Button btn_mail = (Button) findViewById(R.id.btn_mail);
        btn_mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                goEmail();
            }
        });

    }
    public void goCall()
    {

        String number = "tel:" + getString(R.string.placid_call);
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        startActivity(callIntent);
    }
    public void goEmail()
    {
        String subject = "PlacidWay Inc.";
        String emailto = getString(R.string.placid_mail);
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {emailto});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setClassName("com.android.email", "com.android.email.activity.MessageCompose");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            anfe.printStackTrace();
        }
    }


    public void gotoFreeQoutes(View v)
    {
        Intent intent = new Intent(getBaseContext(),FreeQouteActivity.class);
        startActivity(intent);
    }
    public void gotoPopWebView(View v)
    {
        Intent intent = new Intent(getBaseContext(),PopUpWebView.class);
        startActivity(intent);
    }
    public void goMainHome(View v)
    {
        Intent intent = new Intent(DetailListActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }

    public void goCall(View v)
    {

        String number = "tel:" + tv_phone.getText().toString().trim();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        startActivity(callIntent);
    }
    public void goEmail(View v)
    {
        String subject = "PlacidWay Inc.";
        String emailto = "info@placidway.com";
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {emailto});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setClassName("com.android.email", "com.android.email.activity.MessageCompose");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            anfe.printStackTrace();
        }
    }
}
