package com.placidway.inc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.placidway.inc.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Configuration config = getResources().getConfiguration();
        Log.i(" Test :",""+config.smallestScreenWidthDp);


    }
    public void gotoMedicalCenter(View v)
    {
        Intent intent = new Intent(getBaseContext(),MedicalCenterActivity.class);
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
