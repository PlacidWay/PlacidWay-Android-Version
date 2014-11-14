package com.placidway.inc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.placidway.inc.R;

public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
