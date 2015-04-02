package com.placidway.inc.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.placidway.inc.R;

public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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


    public void goMainHome(View v)
    {
        Intent intent = new Intent(AboutUsActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }


}
