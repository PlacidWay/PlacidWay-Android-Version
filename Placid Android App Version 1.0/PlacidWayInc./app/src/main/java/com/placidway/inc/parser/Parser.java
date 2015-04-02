package com.placidway.inc.parser;

import com.placidway.inc.modal.MedicalCenterInfo;
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
    public ArrayList SearchListParser(String str) throws JSONException
    {
        ArrayList res = new ArrayList();
        //{"success" : "Form Submit Successfully."}
        //JSONObject jsonobj = new JSONObject(str);
        JSONArray jsonArray = new JSONArray(str);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            MedicalCenterInfo med = new MedicalCenterInfo();

            med.set_id(jsonArray.getJSONObject(i).getString("id"));
            med.setTitle(jsonArray.getJSONObject(i).getString("name"));
            med.setImageName(jsonArray.getJSONObject(i).getString("img_url"));
            med.setDescription(jsonArray.getJSONObject(i).getString("description"));
            med.setPhone(jsonArray.getJSONObject(i).getString("phone"));
            med.setState(jsonArray.getJSONObject(i).getString("state"));
            med.setCountry(jsonArray.getJSONObject(i).getString("country"));
            med.setCity(jsonArray.getJSONObject(i).getString("city"));
            med.setDetail_link(jsonArray.getJSONObject(i).getString("detail_link"));

            res.add(med);

        }
        return res;

    }
}
