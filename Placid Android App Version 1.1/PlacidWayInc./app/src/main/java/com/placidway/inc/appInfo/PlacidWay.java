package com.placidway.inc.appInfo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.placidway.inc.R;
import com.placidway.inc.modal.FeaturedCenterInfo;
import com.placidway.inc.modal.MedicalCenterInfo;
import com.placidway.inc.modal.PaginationInfo;

import java.util.ArrayList;

/**
 * Created by erum on 29/09/2014.
 */


public class PlacidWay extends Application
{
    private static PlacidWay me;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public PaginationInfo pageInfo;
    public PaginationInfo pageFInfo;
    public PaginationInfo pageAInfo;

    public boolean isPriceFeature = false;
    public MedicalCenterInfo medInfo;
    public FeaturedCenterInfo feaInfo;


    public ArrayList al_medInfo = new ArrayList();
    public ArrayList al_AvgInfo = new ArrayList();
    public ArrayList al_FeaInfo = new ArrayList();

    public String popUpUrl;

    public Activity priceMainActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        me  = this;
        pref = getSharedPreferences(getString(R.string.SharedPref_Filename), MODE_PRIVATE);
        editor = pref.edit();
        popUpUrl = "";
        pageInfo = new PaginationInfo();
        priceMainActivity = null;

    }

    public static PlacidWay getInstance() {
        return me;
    }
    public boolean is_internet_available()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
        /*boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            return connected;
        }
        else
            connected = false;
        return connected;*/
    }
    public boolean haveInternet()
    {
        NetworkInfo info = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();


        if (info==null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to disable internet while roaming, just return false
            return true;
        }

        return true;
    }
    public ArrayList getAl_medInfo() {
        return al_medInfo;
    }

    public MedicalCenterInfo getMedInfo() {
        return medInfo;
    }

    public void setMedInfo(MedicalCenterInfo medInfo) {
        this.medInfo = medInfo;
    }
    public void setAl_medInfo(ArrayList al_medInfo) {
        this.al_medInfo = al_medInfo;
    }
    public ArrayList getAl_AvgInfo() {
        return al_AvgInfo;
    }

    public void setAl_AvgInfo(ArrayList al_AvgInfo) {
        this.al_AvgInfo = al_AvgInfo;
    }

    public ArrayList getAl_FeaInfo() {
        return al_FeaInfo;
    }

    public void setAl_FeaInfo(ArrayList al_FeaInfo) {
        this.al_FeaInfo = al_FeaInfo;
    }

    public PaginationInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PaginationInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public FeaturedCenterInfo getFeaInfo() {
        return feaInfo;
    }

    public void setFeaInfo(FeaturedCenterInfo feaInfo) {
        this.feaInfo = feaInfo;
    }
}
