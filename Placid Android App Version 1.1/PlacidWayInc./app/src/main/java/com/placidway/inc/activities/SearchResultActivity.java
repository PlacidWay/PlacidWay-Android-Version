package com.placidway.inc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.placidway.inc.R;
import com.placidway.inc.adapter.MedInfoAdapter;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.listview.LoadingView;
import com.placidway.inc.modal.MedicalCenterInfo;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;

public class SearchResultActivity extends Activity {

    ArrayList list;
    ListView lv;

    Activity act;
    PaginationInfo pageInfo;
    private LoadingView loadingView;
    boolean isLoading = false;
    MedInfoAdapter bindingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        act = this;

        pageInfo = PlacidWay.getInstance().pageInfo;

        list = PlacidWay.getInstance().getAl_medInfo();
        bindingData = new MedInfoAdapter(act,list);

        lv =  (ListView) findViewById(R.id.list_search);

        Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

        lv.setAdapter(bindingData);

        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

        // Click event for single list row
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                PlacidWay.getInstance().isPriceFeature = false;
                MedicalCenterInfo medInfo = (MedicalCenterInfo)list.get(position);
                PlacidWay.getInstance().setMedInfo(medInfo);

                Intent i = new Intent(SearchResultActivity.this, DetailListActivity.class);

                startActivity(i);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastVisibleItem = firstVisibleItem + visibleItemCount;

                if ((!isLoading && lastVisibleItem == totalItemCount))
                {

                    loadingView = new LoadingView(act.getBaseContext());
                    lv.addFooterView(loadingView);
                    loadingView.setVisibility(View.VISIBLE);
                    lv.setAdapter(bindingData);
                    if(!pageInfo.getNext().equals("")) {
                        if (!PlacidWay.getInstance().is_internet_available())
                        {
                            showAlert("No Internet Connection' message");
                        }
                        else
                        {
                            isLoading = true;

                            String s = pageInfo.getNext();
                            Uri uri = Uri.parse(s);
                            String country = uri.getQueryParameter("countryName");
                            String city = uri.getQueryParameter("cityName");
                            String treatment = uri.getQueryParameter("treatmentPackageName");


                            if(country == null)
                                country = "";

                            if(city == null)
                                city = "";

                            if(treatment == null)
                                treatment = "";
                            AsyncCallSearchList asyncCallSearchList = new AsyncCallSearchList(act, treatment, country, city);
                            asyncCallSearchList.execute();

                        }
                    }
                    else
                    {
                        isLoading = true;
                        //lv.removeFooterView(loadingView);
                        loadingView.setVisibility(View.GONE);
                    }
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
        Intent intent = new Intent(SearchResultActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }

    private class AsyncCallSearchList extends AsyncTask<String, Void, ArrayList>
    {
        //ProgressDialog pd;

        Activity act;
        ArrayList response;

        String treatment;
        String country;
        String city;
        public AsyncCallSearchList(Activity act,String treatment,String country,String city) {
            this.act = act;
            //pd = ProgressDialog.show(act, "", "Please Wait");
            if (treatment.equals("Choose Treatment"))
                this.treatment = "";
            else
                this.treatment = treatment;
            if (country.equals("Choose Country"))
                this.country = "";
            else
                this.country = country;
            if (city.equals("Choose City"))
                this.city = "";
            else
                this.city = city;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetSearchResult(act, treatment, country, city);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            //if (pd != null && pd.isShowing())
              //  pd.dismiss();
            super.onPostExecute(result);
            isLoading = false;
            //lv.removeFooterView(loadingView);
            loadingView.setVisibility(View.GONE);
            for (int i = 0 ; i < result.size() ; i++)
            {
                list.add(result.get(i));
            }
            //list.add(result);
            bindingData.notifyDataSetChanged();

            //lv.onFinishLoading(true, result);//PlacidWay.getInstance().setAl_medInfo(result);

            //Intent intent = new Intent(act,SearchResultActivity.class);
            //startActivity(intent);
        }

    }
    private void showAlert(final String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                this,android.R.style.Theme_DeviceDefault_Light_Dialog));

        builder.setTitle("Alert");
        builder.setMessage(msg);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //stuff you want the button to do

                dialog.dismiss();
                if(msg.equals("Form Submit Successfully."))
                {
                    finish();
                }
            }
        });
        final AlertDialog alert = builder.create();

        alert.show();
    }
}
