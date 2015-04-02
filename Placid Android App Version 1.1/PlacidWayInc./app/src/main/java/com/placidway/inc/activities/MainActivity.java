package com.placidway.inc.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.placidway.inc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;


public class MainActivity extends Activity {

    Button btn_call;
    Button btn_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Configuration config = getResources().getConfiguration();
        Log.i(" Test :",""+config.smallestScreenWidthDp);

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

    public void gotoMedicalCenter(View v)
    {
        Intent intent = new Intent(getBaseContext(),MedicalCenterActivity.class);
        startActivity(intent);
    }
    public void gotoPricing(View v)
    {
        Intent intent = new Intent(getBaseContext(),PricingActivity.class);
        startActivity(intent);
    }
    public void gotoFreeQoutes(View v)
    {
        Intent intent = new Intent(getBaseContext(),FreeQouteActivity.class);
        startActivity(intent);
    }
    public void gotoAboutUs(View v)
    {
        Intent i = new Intent(getBaseContext(), AboutUsActivity.class);
        startActivity(i);
    }


}
