package com.placidway.inc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.placidway.inc.R;
import com.placidway.inc.adapter.MedInfoAdapter;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.modal.MedicalCenterInfo;

import java.util.ArrayList;

public class SearchResultActivity extends Activity {

    ArrayList list;
    MedInfoAdapter adapter = null;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        list = PlacidWay.getInstance().getAl_medInfo();
        MedInfoAdapter bindingData = new MedInfoAdapter(this,list);

        lv = (ListView) findViewById(R.id.list_search);

        Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

        lv.setAdapter(bindingData);

        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

        // Click event for single list row
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                MedicalCenterInfo medInfo = (MedicalCenterInfo)list.get(position);
                PlacidWay.getInstance().setMedInfo(medInfo);

                Intent i = new Intent(SearchResultActivity.this, DetailListActivity.class);

                startActivity(i);
            }
        });

    }
    public void goMainHome(View v)
    {
        Intent intent = new Intent(SearchResultActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }


}
