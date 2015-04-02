package com.placidway.inc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.placidway.inc.R;
import com.placidway.inc.adapter.MedInfoAdapter;
import com.placidway.inc.adapter.PriceListAdapter;
import com.placidway.inc.adapter.PriceListFeaturedAdapter;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.listview.LoadingView;
import com.placidway.inc.modal.AverageCenterInfo;
import com.placidway.inc.modal.FeaturedCenterInfo;
import com.placidway.inc.modal.MedicalCenterInfo;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.segment.SegmentedGroup;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;

public class PriceSearchResultActivity extends Activity {

    Activity act;

    ArrayList list;
    ArrayList listAvg;

    ListView lv;

    boolean isAverage = false;

    BaseAdapter bindingData;
    private LoadingView loadingView;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = this;

        setContentView(R.layout.activity_price_search_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.segmented1);

        isAverage = false;

        if(PlacidWay.getInstance().al_FeaInfo.size() == 0)
        {
            RadioButton rd = (RadioButton)findViewById(R.id.buttonAverage);
            rd.setChecked(true);
            isAverage = true;
            listAvg = PlacidWay.getInstance().al_AvgInfo;
            bindingData = new PriceListAdapter(this,null,listAvg);
        }
        else
        {
            isAverage = false;
            list = PlacidWay.getInstance().al_FeaInfo;
            bindingData = new PriceListFeaturedAdapter(this,null,list);
        }

        lv = (ListView) findViewById(R.id.list_search);

        Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");


        lv.setAdapter(bindingData);
        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");



        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.buttonFeatures:
                        // do operations specific to this selection
                        isLoading = false;
                        isAverage = false;
                        list = PlacidWay.getInstance().al_FeaInfo;
                        bindingData = new PriceListFeaturedAdapter(act,null,list);
                        lv.setAdapter(bindingData);
                        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

                        break;

                    case R.id.buttonAverage:
                        // do operations specific to this selection
                        isLoading = false;

                        isAverage = true;
                        listAvg = PlacidWay.getInstance().al_AvgInfo;
                        bindingData = new PriceListAdapter(act,null,listAvg);
                        lv.setAdapter(bindingData);
                        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

                        break;

                }


            }
        });

        // Click event for single list row
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if (isAverage)
                {

                    PlacidWay.getInstance().isPriceFeature = false;

                    AverageCenterInfo avgInfo = (AverageCenterInfo)listAvg.get(position);

                    String s = avgInfo.getSearch_link();

                    Uri uri=Uri.parse(s);
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
                else if(!isAverage)
                {

                    PlacidWay.getInstance().isPriceFeature = true;

                    FeaturedCenterInfo feaInfo = (FeaturedCenterInfo)list.get(position);

                    PlacidWay.getInstance().setFeaInfo(feaInfo);

                    Intent i = new Intent(PriceSearchResultActivity.this, DetailListActivity.class);

                    startActivity(i);
                }

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


                    PaginationInfo pageInfo;
                    if (isAverage)
                    {
                        pageInfo = PlacidWay.getInstance().pageAInfo;
                        //bindingData = new PriceListAdapter(act,list);
                    }
                    else
                    {
                        pageInfo = PlacidWay.getInstance().pageFInfo;
                        //bindingData = new PriceListFeaturedAdapter(act,list);
                    }

                    loadingView = new LoadingView(act.getBaseContext());
                    lv.addFooterView(loadingView);
                    loadingView.setVisibility(View.VISIBLE);
                    lv.setAdapter(bindingData);

                    if(!pageInfo.getNext().equals(""))
                    {
                        if (!PlacidWay.getInstance().is_internet_available())
                        {
                            showAlert("No Internet Connection' message");
                        }
                        else
                        {
                            isLoading = true;

                            String url = pageInfo.getNext();

                            AsyncCallPageSearchList asyncCallSearchList = new AsyncCallPageSearchList(act, url);
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
        Intent intent = new Intent(PriceSearchResultActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }
    private class AsyncCallSearchList extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog pd;

        Activity act;
        ArrayList response;

        String treatment;
        String country;
        String city;
        public AsyncCallSearchList(Activity act,String treatment,String country,String city) {
            this.act = act;
            pd = ProgressDialog.show(act, "", "Please Wait");
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
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);
            PlacidWay.getInstance().setAl_medInfo(result);

            Intent intent = new Intent(act,SearchResultActivity.class);
            startActivity(intent);
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
    private class AsyncCallPageSearchList extends AsyncTask<String, Void, ArrayList>
    {

        Activity act;
        ArrayList response;

        String url;

        public AsyncCallPageSearchList(Activity act,String url) {
            this.act = act;

        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetPricingPagination(act, url);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {

            super.onPostExecute(result);

            isLoading = false;
            //lv.removeFooterView(loadingView);
            loadingView.setVisibility(View.GONE);
            if(isAverage)
            {
                //result = PlacidWay.getInstance().al_AvgInfo;

                listAvg = PlacidWay.getInstance().al_AvgInfo;
//                for (int i = 0 ; i < listAvg.size(); i++)
//                {
//                    listAvg.remove(i);
//                }
//                for (int i = 0 ; i < result.size(); i++)
//                {
//                    listAvg.add(result.get(i));
//                }

                //bindingData = new PriceListAdapter(act,list);
                bindingData.notifyDataSetChanged();
            }
            else
            {
                result = PlacidWay.getInstance().al_FeaInfo;

                for (int i = 0 ; i < result.size() ; i++)
                {
                    list.add(result.get(i));
                }

                //bindingData = new PriceListFeaturedAdapter(act,list);
                bindingData.notifyDataSetChanged();
            }

        }

    }


}
