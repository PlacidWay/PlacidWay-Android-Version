package com.placidway.inc.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.placidway.inc.R;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.segment.SegmentedGroup;


public class PricingResultActivity extends Activity {

    Activity act;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_price_search_result);

        act = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.segmented1);


        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.buttonFeatures:
                        // do operations specific to this selection
                        fr = new FragmentOne();
                        fm = getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fr);
                        fragmentTransaction.commit();
                        break;

                    case R.id.buttonAverage:
                        // do operations specific to this selection
                        PlacidWay.getInstance().priceMainActivity = act;
                        fr = new FragmentTwo();
                        fm = getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fr);
                        fragmentTransaction.commit();

                        break;

                }


            }
        });


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

        if(PlacidWay.getInstance().al_FeaInfo.size() == 0)
        {
            RadioButton rd = (RadioButton)findViewById(R.id.buttonAverage);
            rd.setChecked(true);

            PlacidWay.getInstance().priceMainActivity = act;
            fr = new FragmentTwo();
        }
        else
        {
            RadioButton rd = (RadioButton)findViewById(R.id.buttonFeatures);
            rd.setChecked(true);

            fr = new FragmentOne();
        }

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();


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
        Intent intent = new Intent(PricingResultActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }

    public void selectFrag(View view) {
		 Fragment fr;
		 
		 if(view == findViewById(R.id.buttonAverage)) {
			 fr = new FragmentTwo();
		 
		 }else {
			 fr = new FragmentOne();
		 }
		 
		 FragmentManager fm = getFragmentManager();
	     FragmentTransaction fragmentTransaction = fm.beginTransaction();
	     fragmentTransaction.replace(R.id.fragment_place, fr);
	     fragmentTransaction.commit();
		 
	}
   
}
