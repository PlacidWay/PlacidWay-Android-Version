package com.placidway.inc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.placidway.inc.R;
import com.placidway.inc.adapter.PriceListAdapter;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.listview.LoadingView;
import com.placidway.inc.modal.AverageCenterInfo;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;


public class FragmentTwo extends Fragment{

    Activity act;

    ArrayList list;
    ListView lv;

    BaseAdapter bindingData;
    private LoadingView loadingView;
    boolean isLoading = false;


    View v;

   @Override
   public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
      /**
       * Inflate the layout for this fragment
       */
       v = inflater.inflate(
               R.layout.fragment_two, container, false);

       act = this.getActivity();

       list = PlacidWay.getInstance().al_AvgInfo;
       bindingData = new PriceListAdapter(act,v,list);

       lv = (ListView) v.findViewById(R.id.listView2);

       Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");


       lv.setAdapter(bindingData);
       Log.i("AFTER", "<<------------- After SetAdapter-------------->>");


       // Click event for single list row
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {


               PlacidWay.getInstance().isPriceFeature = false;

               AverageCenterInfo avgInfo = (AverageCenterInfo)list.get(position);

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
                   pageInfo = PlacidWay.getInstance().pageAInfo;

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

      return v;
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

            //if(result.size() > 0 )
            {
                Intent intent = new Intent(PlacidWay.getInstance().priceMainActivity,SearchResultActivity.class);
                startActivity(intent);
            }

        }

    }
    private void showAlert(final String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                act,android.R.style.Theme_DeviceDefault_Light_Dialog));

        builder.setTitle("Alert");
        builder.setMessage(msg);

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //stuff you want the button to do

                dialog.dismiss();
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
            result = PlacidWay.getInstance().al_AvgInfo;

            for (int i = 0 ; i < result.size() ; i++)
            {
                list.add(result.get(i));
            }

            //bindingData = new PriceListFeaturedAdapter(act,list);
            bindingData.notifyDataSetChanged();


        }

    }
}
