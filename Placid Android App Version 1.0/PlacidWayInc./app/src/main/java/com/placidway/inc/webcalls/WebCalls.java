package com.placidway.inc.webcalls;

import android.app.Activity;

import com.placidway.inc.R;
import com.placidway.inc.parser.Parser;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by erum on 04/11/2014.
 */
public class WebCalls
{


    public static ArrayList wcGetAllTreatment(Activity act)
    {

        Parser parser = new Parser();

        String response;

        String url = act.getString(R.string.UrlBase) + act.getString(R.string.UrlGetAllTreatment);


        url = url.replaceAll(" ", "%20");


        System.out.println("url ==> " + url);

        //url = "http://www.placidway.com/mobile_apps/contact-us.php?submit=yes&name=abc&email=erumawan.21@gmail.com&phone=123&city=Islamabad&country=Pakistan&subject=abcretjjmnbmn&message=xyz";
        DefaultHttpClient client = new DefaultHttpClient();


        client.getCredentialsProvider().setCredentials(new AuthScope(act.getString(R.string.UrlBase), 80), new UsernamePasswordCredentials(act.getString(R.string.httpUser), act.getString(R.string.httpPassword)));


        HttpPost post = new HttpPost(url);
        //post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //post.setEntity(new UrlEncodedFormEntity(params));
        try {
            HttpResponse res = client.execute(post);
            //int r = res.getStatusLine().getStatusCode() ;
            if (res.getStatusLine().getStatusCode() == 200)
            {
                // if OK
                response = EntityUtils.toString(res.getEntity());
                System.out.println("response" + response);
                ArrayList resL = parser.TreatmentParser(response);
                return resL;
            }
            System.out.println("code" + res.getStatusLine().getStatusCode());
        } catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            response = "Error occured";
        }
        return null;
    }
    public static ArrayList wcGetAllCountry(Activity act)
    {

        Parser parser = new Parser();

        String response;

        String url = act.getString(R.string.UrlBase) + act.getString(R.string.UrlGetAllCountries);


        url = url.replaceAll(" ", "%20");


        System.out.println("url ==> " + url);

        //url = "http://www.placidway.com/mobile_apps/contact-us.php?submit=yes&name=abc&email=erumawan.21@gmail.com&phone=123&city=Islamabad&country=Pakistan&subject=abcretjjmnbmn&message=xyz";
        DefaultHttpClient client = new DefaultHttpClient();


        client.getCredentialsProvider().setCredentials(new AuthScope(act.getString(R.string.UrlBase), 80), new UsernamePasswordCredentials(act.getString(R.string.httpUser), act.getString(R.string.httpPassword)));


        HttpPost post = new HttpPost(url);
        //post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //post.setEntity(new UrlEncodedFormEntity(params));
        try {
            HttpResponse res = client.execute(post);
            //int r = res.getStatusLine().getStatusCode() ;
            if (res.getStatusLine().getStatusCode() == 200)
            {
                // if OK
                response = EntityUtils.toString(res.getEntity());
                System.out.println("response" + response);
                ArrayList resL = parser.CountryParser(response);
                return resL;
            }
            System.out.println("code" + res.getStatusLine().getStatusCode());
        } catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            response = "Error occured";
        }
        return null;
    }
    public static ArrayList wcGetCityByCountry(Activity act,String country)
    {

        Parser parser = new Parser();

        String response;

        String url = act.getString(R.string.UrlBase) + act.getString(R.string.UrlGetAllCitiesByCountry);
        url += country;

        url = url.replaceAll(" ", "%20");


        System.out.println("url ==> " + url);

        //url = "http://www.placidway.com/mobile_apps/contact-us.php?submit=yes&name=abc&email=erumawan.21@gmail.com&phone=123&city=Islamabad&country=Pakistan&subject=abcretjjmnbmn&message=xyz";
        DefaultHttpClient client = new DefaultHttpClient();


        client.getCredentialsProvider().setCredentials(new AuthScope(act.getString(R.string.UrlBase), 80), new UsernamePasswordCredentials(act.getString(R.string.httpUser), act.getString(R.string.httpPassword)));


        HttpPost post = new HttpPost(url);
        //post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //post.setEntity(new UrlEncodedFormEntity(params));
        try {
            HttpResponse res = client.execute(post);
            //int r = res.getStatusLine().getStatusCode() ;
            if (res.getStatusLine().getStatusCode() == 200)
            {
                // if OK
                response = EntityUtils.toString(res.getEntity());
                System.out.println("response" + response);
                ArrayList resL = parser.CityParser(response);
                return resL;
            }
            System.out.println("code" + res.getStatusLine().getStatusCode());
        } catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            response = "Error occured";
        }
        return null;
    }

    public static ArrayList wcGetSearchResult(Activity act,String treatment,String country,String city)
    {

        Parser parser = new Parser();

        String response;

        String url = act.getString(R.string.UrlBase) + act.getString(R.string.UrlGetSearchedMedicalList);
        url += country;
        url += "&cityName=";
        url += city;
        url += "&treatmentPackageName=";
        url += treatment;

        url = url.replaceAll(" ", "%20");


        //url = "http://www.placidway.com/mobile_apps/SearchMedicalCenters.php?countryName=&cityName=&treatmentPackageName=Cancer%20Treatment";

        System.out.println("url ==> " + url);

        DefaultHttpClient client = new DefaultHttpClient();


        client.getCredentialsProvider().setCredentials(new AuthScope(act.getString(R.string.UrlBase), 80), new UsernamePasswordCredentials(act.getString(R.string.httpUser), act.getString(R.string.httpPassword)));


        HttpPost post = new HttpPost(url);
        //post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //post.setEntity(new UrlEncodedFormEntity(params));
        try {
            HttpResponse res = client.execute(post);
            //int r = res.getStatusLine().getStatusCode() ;
            if (res.getStatusLine().getStatusCode() == 200)
            {
                // if OK
                response = EntityUtils.toString(res.getEntity());
                System.out.println("response" + response);
                ArrayList resL = parser.SearchListParser(response);
                return resL;
            }
            System.out.println("code" + res.getStatusLine().getStatusCode());
        } catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            response = "Error occured";
        }
        return null;
    }

    public static String wcFreeQoute(Hashtable list,Activity act)
    {

        Parser parser = new Parser();

        String response;

        String url = act.getString(R.string.UrlBase) + act.getString(R.string.UrlContactUs);
        url += "&name=";
        url += list.get("name");
        url += "&email=";
        url += list.get("email");
        url += "&phone=";
        url += list.get("phone");
        url += "&address=";
        url += list.get("address");
        url += "&city=";
        url += list.get("city");
        url += "&country=";
        url += list.get("country");
        url += "&subject=";
        url += list.get("subject");
        url += "&message=";
        url += list.get("message");

        url = url.replaceAll(" ", "%20");


        System.out.println("url ==> " + url);

        //url = "http://www.placidway.com/mobile_apps/contact-us.php?submit=yes&name=abc&email=erumawan.21@gmail.com&phone=123&city=Islamabad&country=Pakistan&subject=abcretjjmnbmn&message=xyz";
        DefaultHttpClient client = new DefaultHttpClient();


        client.getCredentialsProvider().setCredentials(new AuthScope(act.getString(R.string.UrlBase), 80), new UsernamePasswordCredentials(act.getString(R.string.httpUser), act.getString(R.string.httpPassword)));


        HttpPost post = new HttpPost(url);
        //post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        //post.setEntity(new UrlEncodedFormEntity(params));
        try {
        HttpResponse res = client.execute(post);
        //int r = res.getStatusLine().getStatusCode() ;
        if (res.getStatusLine().getStatusCode() == 200)
        {
            // if OK
            response = EntityUtils.toString(res.getEntity());
            System.out.println("response" + response);
            response = parser.ContactUsParser(response);
            return response;
        }
        System.out.println("code" + res.getStatusLine().getStatusCode());
        } catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            response = "Error occured";
        }
        return null;
    }

}
