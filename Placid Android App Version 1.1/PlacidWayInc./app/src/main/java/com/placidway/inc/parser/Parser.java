package com.placidway.inc.parser;

import android.net.Uri;

import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.modal.AverageCenterInfo;
import com.placidway.inc.modal.FeaturedCenterInfo;
import com.placidway.inc.modal.MedicalCenterInfo;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.modal.Treatment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Parser {

	public String ContactUsParser(String str) throws JSONException
	{
        //{"success" : "Form Submit Successfully."}
		JSONObject jsonobj = new JSONObject(str);
		System.out.println(jsonobj.getString("success"));
        String res = jsonobj.getString("success");
        return res;
		
	}
    public ArrayList TreatmentParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            /*Treatment treatment = new Treatment();

            treatment.set_id(jsonArray.getJSONObject(i).getString("id"));
            treatment.setName(jsonArray.getJSONObject(i).getString("name"));

            res.add(treatment);*/
            res.add(jsonArray.getJSONObject(i).getString("name"));

        }
        return res;

    }
    public ArrayList CountryParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            /*Treatment treatment = new Treatment();

            treatment.set_id(jsonArray.getJSONObject(i).getString("id"));
            treatment.setName(jsonArray.getJSONObject(i).getString("name"));

            res.add(treatment);*/
            res.add(jsonArray.getJSONObject(i).getString("name"));

        }
        return res;

    }
    public ArrayList CityParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            /*Treatment treatment = new Treatment();

            treatment.set_id(jsonArray.getJSONObject(i).getString("id"));
            treatment.setName(jsonArray.getJSONObject(i).getString("name"));

            res.add(treatment);*/
            res.add(jsonArray.getJSONObject(i).getString("name"));

        }
        return res;

    }
    public ArrayList PricingCountryParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            /*Treatment treatment = new Treatment();

            treatment.set_id(jsonArray.getJSONObject(i).getString("id"));
            treatment.setName(jsonArray.getJSONObject(i).getString("name"));

            res.add(treatment);*/
            res.add(jsonArray.getJSONObject(i).getString("country"));

        }
        return res;

    }
    public ArrayList PricingTreatmentParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();

        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            res.add(jsonArray.getJSONObject(i).getString("treatment"));

        }
        return res;

    }
    public ArrayList PricingProceduresParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();

        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            res.add(jsonArray.getJSONObject(i).getString("procedure"));

        }
        return res;

    }
    public ArrayList PricingListParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        ArrayList resAvg = new ArrayList();
        ArrayList resFea = new ArrayList();

        PlacidWay.getInstance().al_AvgInfo = new ArrayList();
        PlacidWay.getInstance().al_FeaInfo = new ArrayList();

        JSONArray jsonArray = new JSONArray(str);


        for (int i = 0; i < jsonArray.length(); i++)
        {

            String cat = jsonArray.getJSONObject(i).getString("category");

            if (cat.equals("Average"))
            {
                PaginationInfo pagInfo = new PaginationInfo();

                String next = "--";

                if (jsonArray.getJSONObject(i).has("Next"))
                  next = jsonArray.getJSONObject(i).getString("Next");

                if (!next.equals("--"))
                {
                    Uri uri = Uri.parse(next);
                    String start = uri.getQueryParameter("start");
                    pagInfo.setStart(start);

                    //pagInfo.setStart(jsonArray.getJSONObject(i).getString("start"));
                    pagInfo.setEnd(jsonArray.getJSONObject(i).getString("end"));
                    pagInfo.setTotal(jsonArray.getJSONObject(i).getString("total"));
                    pagInfo.setNext(jsonArray.getJSONObject(i).getString("Next"));

                    PlacidWay.getInstance().pageAInfo = pagInfo;

                }
                else
                {
                    AverageCenterInfo avgInfo = new AverageCenterInfo();

                    avgInfo.setCategory(jsonArray.getJSONObject(i).getString("category"));
                    avgInfo.setSearch_link(jsonArray.getJSONObject(i).getString("search_link"));
                    avgInfo.setImg_url(jsonArray.getJSONObject(i).getString("img_url"));
                    avgInfo.setDetails(jsonArray.getJSONObject(i).getString("details"));
                    avgInfo.setCenter_name(jsonArray.getJSONObject(i).getString("center_name"));
                    avgInfo.setPrice(jsonArray.getJSONObject(i).getString("price"));

                    //resAvg.add(avgInfo);
                    PlacidWay.getInstance().al_AvgInfo.add(avgInfo);
                }

            }
            else if (cat.equals("Featured"))
            {

                PaginationInfo pagInfo = new PaginationInfo();


                String next = "--";

                if (jsonArray.getJSONObject(i).has("Next"))
                    next = jsonArray.getJSONObject(i).getString("Next");

                if (!next.equals("--"))
                {
                    Uri uri = Uri.parse(next);
                    String start = uri.getQueryParameter("start");
                    pagInfo.setStart(start);

                    //pagInfo.setStart(jsonArray.getJSONObject(i).getString("start"));
                    pagInfo.setEnd(jsonArray.getJSONObject(i).getString("end"));
                    pagInfo.setTotal(jsonArray.getJSONObject(i).getString("total"));
                    pagInfo.setNext(jsonArray.getJSONObject(i).getString("Next"));

                    PlacidWay.getInstance().pageFInfo = pagInfo;

                }
                else
                {
                    FeaturedCenterInfo feaInfo = new FeaturedCenterInfo();

                    feaInfo.setCategory(jsonArray.getJSONObject(i).getString("category"));
                    feaInfo.setDetail_link(jsonArray.getJSONObject(i).getString("profile_link"));
                    feaInfo.setImg_url(jsonArray.getJSONObject(i).getString("img_url"));
                    feaInfo.setDetails(jsonArray.getJSONObject(i).getString("details"));
                    feaInfo.setPrice(jsonArray.getJSONObject(i).getString("price"));
                    feaInfo.setPhone(jsonArray.getJSONObject(i).getString("phone"));
                    feaInfo.setState(jsonArray.getJSONObject(i).getString("state"));
                    feaInfo.setCountry(jsonArray.getJSONObject(i).getString("country"));
                    feaInfo.setCity(jsonArray.getJSONObject(i).getString("city"));
                    feaInfo.setCenter_name(jsonArray.getJSONObject(i).getString("center_name"));

                    //resFea.add(feaInfo);
                    PlacidWay.getInstance().al_FeaInfo.add(feaInfo);
                }

            }


        }

        //res.add(0,resAvg);
        //res.add(1,resFea);
        //PlacidWay.getInstance().setAl_AvgInfo(resAvg);
        //PlacidWay.getInstance().setAl_FeaInfo(resFea);

        return res;

    }
    public ArrayList SearchListParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);


        PaginationInfo pagInfo = new PaginationInfo();


        String next = "";

        if (jsonArray.getJSONObject(0).has("Next"))
            next = jsonArray.getJSONObject(0).getString("Next");

        if (!next.equals(""))
        {
            Uri uri = Uri.parse(next);
            String start = uri.getQueryParameter("start");
            pagInfo.setStart(start);
            //pagInfo.setStart(jsonArray.getJSONObject(0).getString("start"));
            pagInfo.setEnd(jsonArray.getJSONObject(0).getString("end"));
            pagInfo.setTotal(jsonArray.getJSONObject(0).getString("total"));
            pagInfo.setNext(jsonArray.getJSONObject(0).getString("Next"));

            PlacidWay.getInstance().pageInfo = pagInfo;

        }

        for (int i = 1; i < jsonArray.length(); i++)
        {



            {
                MedicalCenterInfo med = new MedicalCenterInfo();

                med.set_id(jsonArray.getJSONObject(i).getString("id"));
                med.setImageName(jsonArray.getJSONObject(i).getString("img_url"));
                med.setTitle(jsonArray.getJSONObject(i).getString("name"));
                med.setDescription(jsonArray.getJSONObject(i).getString("description"));
                med.setPhone(jsonArray.getJSONObject(i).getString("phone"));
                med.setState(jsonArray.getJSONObject(i).getString("state"));
                med.setCountry(jsonArray.getJSONObject(i).getString("country"));
                med.setCity(jsonArray.getJSONObject(i).getString("city"));
                med.setDetail_link(jsonArray.getJSONObject(i).getString("detail_link"));

                res.add(med);
            }


        }
        return res;

    }
}
