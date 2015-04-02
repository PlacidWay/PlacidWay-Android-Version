package com.placidway.inc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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
import com.placidway.inc.adapter.PriceListFeaturedAdapter;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.listview.LoadingView;
import com.placidway.inc.modal.AverageCenterInfo;
import com.placidway.inc.modal.FeaturedCenterInfo;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;

public class FragmentOne extends Fragment {

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
      
       //Inflate the layout for this fragment
       v = inflater.inflate(
               R.layout.fragment_one, container, false);

       act = this.getActivity();

       list = PlacidWay.getInstance().al_FeaInfo;
       bindingData = new PriceListFeaturedAdapter(act,v,list);

       lv = (ListView) v.findViewById(R.id.listView);

       Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");


       lv.setAdapter(bindingData);
       Log.i("AFTER", "<<------------- After SetAdapter-------------->>");


       // Click event for single list row
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {


               PlacidWay.getInstance().isPriceFeature = true;

               FeaturedCenterInfo feaInfo = (FeaturedCenterInfo)list.get(position);

               PlacidWay.getInstance().setFeaInfo(feaInfo);

               Intent i = new Intent(act, DetailListActivity.class);

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


                   PaginationInfo pageInfo;
                   pageInfo = PlacidWay.getInstance().pageFInfo;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
