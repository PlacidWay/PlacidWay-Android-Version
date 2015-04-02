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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.placidway.inc.R;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.modal.PaginationInfo;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;

public class PricingActivity extends Activity {

    Activity act;
    ArrayList<String> array_treatment;
    ArrayList<String> array_country;
    ArrayList<String> array_procedure;

    TextView tv_treatment;
    TextView tv_country;
    TextView tv_procedure;

    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        act = this;

        tv_treatment = (TextView) findViewById(R.id.tv_treatment);
        tv_country = (TextView) findViewById(R.id.tv_country);
        tv_procedure = (TextView) findViewById(R.id.tv_procedure_mc);

        if (!PlacidWay.getInstance().is_internet_available())
        {
            showAlert("No Internet Connection");
        }
        else {
            //Create instance for AsyncCallWS
            AsyncCallGetTreatment task = new AsyncCallGetTreatment(act);
            //Call execute
            task.execute();
            /*if (!PlacidWay.getInstance().is_internet_available()) {
                showAlert("No Internet Connection");
            } else{
                AsyncCallGetCountry asyncCallGetCountry = new AsyncCallGetCountry(act);
                asyncCallGetCountry.execute();
            }*/
        }
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

    private class AsyncCallGetTreatment extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog pd;

        Activity act;
        ArrayList response;
        public AsyncCallGetTreatment(Activity act) {
            this.act = act;
            pd = ProgressDialog.show(act, "", "Please Wait");
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetPricingTreatment(act);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);
            array_treatment = result;
        }

    }
    private class AsyncCallGetCountry extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog pd;

        Activity act;
        ArrayList response;
        String treatment;
        String procedure;
        public AsyncCallGetCountry(Activity act,String procedure,String treatment) {
            this.act = act;
            pd = ProgressDialog.show(act, "", "Please Wait");
            this.treatment=treatment;
            this.procedure=procedure;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetPricingCountryByProcedure(act,procedure,treatment);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);
            array_country = result;

        }

    }

    private class AsyncCallGetProcedure extends AsyncTask<String, Void, ArrayList>
    {
        ProgressDialog pd;

        Activity act;
        ArrayList response;
        String treatment;
        public AsyncCallGetProcedure(Activity act,String treatment) {
            this.act = act;
            pd = ProgressDialog.show(act, "", "Please Wait");
            this.treatment=treatment;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetPricingProcedureByTreatment(act, treatment);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);
            array_procedure = result;

        }

    }


    public void popupTreatment(View v)
    {
        if (array_treatment != null)
        {
                final String[] treatment = array_treatment.toArray(new String[array_treatment.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                        this, android.R.style.Theme_DeviceDefault_Light_Dialog));

                builder.setTitle("Choose Treatment");
                builder.setItems(treatment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        tv_treatment.setText(treatment[item]);
                        //Toast.makeText(getApplicationContext(), country[item], Toast.LENGTH_SHORT).show();

                        if (!PlacidWay.getInstance().is_internet_available()) {
                            showAlert("No Internet Connection");
                        } else {
                            AsyncCallGetProcedure asyncCallGetCountry = new AsyncCallGetProcedure(act, treatment[item]);
                            asyncCallGetCountry.execute();
                        }
                    }
                });

                alert = builder.create();

                if(!alert.isShowing())
                    alert.show();

        }
        else
        {

                if (!PlacidWay.getInstance().is_internet_available()) {
                    showAlert("No Internet Connection");
                } else {
                    AsyncCallGetTreatment task = new AsyncCallGetTreatment(act);
                    //Call execute
                    task.execute();
                }

        }

    }
    public void popupCountryListMC(View v)
    {
        if (array_country != null) {

                final String[] countries = array_country.toArray(new String[array_country.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                        this, android.R.style.Theme_DeviceDefault_Light_Dialog));

                builder.setTitle("Choose Country");
                builder.setItems(countries, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                    tv_country.setText(countries[item]);
                    //Toast.makeText(getApplicationContext(), country[item], Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                });

                alert = builder.create();

                if(!alert.isShowing())
                    alert.show();

        }
        else
        {

                if (tv_treatment.getText().toString().equals("Choose Treatment") ||
                        tv_procedure.getText().toString().equals("Choose Procedure")) {
                    showAlert("Choose Treatment First.");
                }


        }

    }
    public void popupProcedure(View v)
    {
        if (array_procedure != null) {
                if (tv_country.getText().toString().equals("Choose Procedure")) {
                    showAlert("Choose Treatment First.");
                } else {
                    final String[] procedure = array_procedure.toArray(new String[array_procedure.size()]);

                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                            this, android.R.style.Theme_DeviceDefault_Light_Dialog));

                    builder.setTitle("Choose Procedure");
                    builder.setItems(procedure, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            tv_procedure.setText(procedure[item]);
                            tv_country.setText("Choose Country");
                            if (!PlacidWay.getInstance().is_internet_available()) {
                                showAlert("No Internet Connection");
                            } else {
                                AsyncCallGetCountry asyncCallGetCountry = new AsyncCallGetCountry(act, procedure[item], tv_treatment.getText().toString());
                                asyncCallGetCountry.execute();
                            }
                        }

                    });


                    alert = builder.create();

                    if(!alert.isShowing())
                        alert.show();
                }

        }
        else
        {
                if (tv_treatment.getText().toString().equals("Choose Treatment")) {
                    showAlert("Choose Treatment First.");
                }

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

            }
        });
        final AlertDialog alert = builder.create();

        alert.show();
    }
    public void gotoSearch(View v)
    {
        PlacidWay.getInstance().pageInfo = new PaginationInfo();
        PlacidWay.getInstance().pageAInfo = new PaginationInfo();
        PlacidWay.getInstance().pageFInfo = new PaginationInfo();
        PlacidWay.getInstance().al_medInfo = new ArrayList();
        PlacidWay.getInstance().al_AvgInfo = new ArrayList();
        PlacidWay.getInstance().al_FeaInfo = new ArrayList();


        if (!PlacidWay.getInstance().is_internet_available())
        {
            showAlert("No Internet Connection' message");
        }
        else {
            AsyncCallSearchList asyncCallSearchList = new AsyncCallSearchList(act, tv_treatment.getText().toString(),tv_procedure.getText().toString(),tv_country.getText().toString());
            asyncCallSearchList.execute();
        }
    }
    public void goMainHome(View v)
    {
        Intent intent = new Intent(act,MainActivity.class);
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
        String procedure;
        public AsyncCallSearchList(Activity act,String treatment,String procedure,String country) {
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
            if (procedure.equals("Choose Procedure"))
                this.procedure = "";
            else
                this.procedure = procedure;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(String... params)
        {
            response = WebCalls.wcGetPricingSearchList(act, treatment, procedure,country);
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);

            //Intent intent = new Intent(act,PriceSearchResultActivity.class);
            //startActivity(intent);

            Intent intent = new Intent(act,PricingResultActivity.class);
            startActivity(intent);

        }

    }



}
