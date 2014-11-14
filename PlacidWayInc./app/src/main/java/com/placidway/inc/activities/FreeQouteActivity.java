package com.placidway.inc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.placidway.inc.R;
import com.placidway.inc.appInfo.PlacidWay;
import com.placidway.inc.webcalls.WebCalls;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreeQouteActivity extends Activity {

    Activity act;
    ArrayList<String> list = new ArrayList<String>();
    Hashtable hashTable = new Hashtable();
    EditText ed_fn;
    EditText ed_email;
    EditText ed_phone;
    EditText ed_address;
    EditText ed_city;
    EditText ed_country;
    EditText ed_subject;
    EditText ed_message;


    ImageButton btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_qoute);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        act = this;
        ed_fn = (EditText) findViewById(R.id.ed_full_name);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_address = (EditText) findViewById(R.id.ed_addres);
        ed_city = (EditText) findViewById(R.id.ed_city);
        ed_country = (EditText) findViewById(R.id.ed_country);
        ed_subject = (EditText) findViewById(R.id.ed_subject);
        ed_message = (EditText) findViewById(R.id.ed_message);

        ed_fn.setTextColor(getResources().getColor(R.color.white));
        ed_email.setTextColor(getResources().getColor(R.color.white));
        ed_phone.setTextColor(getResources().getColor(R.color.white));
        ed_address.setTextColor(getResources().getColor(R.color.white));
        ed_city.setTextColor(getResources().getColor(R.color.white));
        ed_country.setTextColor(getResources().getColor(R.color.white));
        ed_subject.setTextColor(getResources().getColor(R.color.white));
        ed_message.setTextColor(getResources().getColor(R.color.white));


        //Button to trigger web service invocation
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        //Button Click Listener
        btn_send.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(ed_fn.getText().toString().equals(""))
                {
                    showAlert("Enter Full Name");
                }
                else if(ed_email.getText().toString().equals(""))
                {
                    showAlert("Enter Email");
                }
                else if(ed_phone.getText().toString().equals(""))
                {
                    showAlert("Enter Phone");
                }
                else if(ed_address.getText().toString().equals(""))
                {
                    showAlert("Enter Address Name");
                }
                else if(ed_city.getText().toString().equals(""))
                {
                    showAlert("Enter City");
                }
                else if(ed_country.getText().toString().equals(""))
                {
                    showAlert("Enter Country");
                }
                else if(ed_subject.getText().toString().equals(""))
                {
                    showAlert("Enter Subject");
                }
                else if(ed_message.getText().toString().equals(""))
                {
                    showAlert("Enter Message");
                }
                else if(!emailValidator(ed_email.getText().toString()))
                {
                    showAlert("Please Enter Valid Email Address.");
                }
                else
                {
                    list = new ArrayList<String>();
                    hashTable = new Hashtable();
                    hashTable.put("name",ed_fn.getText().toString());
                    hashTable.put("email",ed_email.getText().toString());
                    hashTable.put("phone",ed_phone.getText().toString());
                    hashTable.put("address",ed_address.getText().toString());
                    hashTable.put("city",ed_city.getText().toString());
                    hashTable.put("country",ed_country.getText().toString());
                    hashTable.put("subject",ed_subject.getText().toString());
                    hashTable.put("message",ed_message.getText().toString());

                    if (!PlacidWay.getInstance().is_internet_available())
                    {
                        showAlert("No Internet Connection");
                    }
                    else {
                        //Create instance for AsyncCallWS
                        AsyncCallContactUs task = new AsyncCallContactUs(hashTable, act);
                        //Call execute
                        task.execute();
                    }
                }
            }
        });
    }
    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void popupCountryList(View v)
    {
        final CharSequence[] country = {"Afghanistan",
                "Albania",
                "Algeria",
                "American Samoa",
                "Andorra",
                "Angola",
                "Anguilla",
                "Antarctica",
                "Antigua and Barbuda",
                "Argentina",
                "Armenia",
                "Aruba",
                "Australia",
                "Austria",
                "Azerbaijan",
                "Bahamas",
                "Bahrain",
                "Bangladesh",
                "Barbados",
                "Belarus",
                "Belgium",
                "Belize",
                "Benin",
                "Bermuda",
                "Bhutan",
                "Bolivia",
                "Bosnia and Herzegowina",
                "Botswana",
                "Bouvet Island",
                "Brazil",
                "British Indian Ocean Territory",
                "Brunei Darussalam",
                "Bulgaria",
                "Burkina Faso",
                "Burundi",
                "Cambodia",
                "Cameroon",
                "Canada",
                "Cape Verde",
                "Cayman Islands",
                "Central African Republic",
                "Chad",
                "Chile",
                "China",
                "Christmas Island",
                "Cocos (Keeling) Islands",
                "Colombia",
                "Comoros",
                "Congo",
                "Congo, the Democratic Republic of the",
                "Cook Islands",
                "Costa Rica",
                "Cote dIvoire",
                "Croatia (Hrvatska)",
                "Cuba",
                "Cyprus",
                "Czech Republic",
                "Denmark",
                "Djibouti",
                "Dominica",
                "Dominican Republic",
                "East Timor",
                "Ecuador",
                "Egypt",
                "El Salvador",
                "Equatorial Guinea",
                "Eritrea",
                "Estonia",
                "Ethiopia",
                "Falkland Islands (Malvinas)",
                "Faroe Islands",
                "Fiji",
                "Finland",
                "France",
                "France Metropolitan",
                "French Guiana",
                "French Polynesia",
                "French Southern Territories",
                "Gabon",
                "Gambia",
                "Georgia",
                "Germany",
                "Ghana",
                "Gibraltar",
                "Greece",
                "Greenland",
                "Grenada",
                "Guadeloupe",
                "Guam",
                "Guatemala",
                "Guinea",
                "Guinea-Bissau",
                "Guyana",
                "Haiti",
                "Heard and Mc Donald Islands",
                "Holy See (Vatican City State)",
                "Honduras",
                "Hong Kong",
                "Hungary",
                "Iceland",
                "India",
                "Indonesia",
                "Iran (Islamic Republic of)",
                "Iraq",
                "Ireland",
                "Israel",
                "Italy",
                "Jamaica",
                "Japan",
                "Jordan",
                "Kazakhstan",
                "Kenya",
                "Kiribati",
                "Korea, Democratic Peoples Republic of",
                "Korea, Republic of",
                "Kuwait",
                "Kyrgyzstan",
                "Lao, Peoples Democratic Republic",
                "Latvia",
                "Lebanon",
                "Lesotho",
                "Liberia",
                "Libyan Arab Jamahiriya",
                "Liechtenstein",
                "Lithuania",
                "Luxembourg",
                "Macau",
                "Macedonia, The Former Yugoslav Republic of",
                "Madagascar",
                "Malawi",
                "Malaysia",
                "Maldives",
                "Mali",
                "Malta",
                "Marshall Islands",
                "Martinique",
                "Mauritania",
                "Mauritius",
                "Mayotte",
                "Mexico",
                "Micronesia, Federated States of",
                "Moldova, Republic of",
                "Monaco",
                "Mongolia",
                "Montserrat",
                "Morocco",
                "Mozambique",
                "Myanmar",
                "Namibia",
                "Nauru",
                "Nepal",
                "Netherlands",
                "Netherlands Antilles",
                "New Caledonia",
                "New Zealand",
                "Nicaragua",
                "Niger",
                "Nigeria",
                "Niue",
                "Norfolk Island",
                "Northern Mariana Islands",
                "Norway",
                "Oman",
                "Pakistan",
                "Palau",
                "Panama",
                "Papua New Guinea",
                "Paraguay",
                "Peru",
                "Philippines",
                "Pitcairn",
                "Poland",
                "Portugal",
                "Puerto Rico",
                "Qatar",
                "Reunion",
                "Romania",
                "Russian Federation",
                "Rwanda",
                "Saint Kitts and Nevis",
                "Saint Lucia",
                "Saint Vincent and the Grenadines",
                "Samoa",
                "San Marino",
                "Sao Tome and Principe",
                "Saudi Arabia",
                "Senegal",
                "Seychelles",
                "Sierra Leone",
                "Singapore",
                "Slovakia (Slovak Republic)",
                "Slovenia",
                "Solomon Islands",
                "Somalia",
                "South Africa",
                "South Georgia and the South Sandwich Islands",
                "Spain",
                "Sri Lanka",
                "St. Helena",
                "St. Pierre and Miquelon",
                "Sudan",
                "Suriname",
                "Svalbard and Jan Mayen Islands",
                "Swaziland",
                "Sweden",
                "Switzerland",
                "Syrian Arab Republic",
                "Taiwan, Province of China",
                "Tajikistan",
                "Tanzania, United Republic of",
                "Thailand",
                "Togo",
                "Tokelau",
                "Tonga",
                "Trinidad and Tobago",
                "Tunisia",
                "Turkey",
                "Turkmenistan",
                "Turks and Caicos Islands",
                "Tuvalu",
                "Uganda",
                "Ukraine",
                "United Arab Emirates",
                "United Kingdom",
                "United States",
                "United States Minor Outlying Islands",
                "Uruguay",
                "Uzbekistan",
                "Vanuatu",
                "Venezuela",
                "Vietnam",
                "Virgin Islands (British)",
                "Virgin Islands (U.S.)",
                "Wallis and Futuna Islands",
                "Western Sahara",
                "Yemen",
                "Yugoslavia",
                "Zambia",
                "Zimbabwe"};

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                this,android.R.style.Theme_DeviceDefault_Light_Dialog));

        builder.setTitle("Select Country*");
        builder.setItems(country, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                ed_country.setText(country[item]);
                //Toast.makeText(getApplicationContext(), country[item], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = builder.create();

        alert.show();
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
    private class AsyncCallContactUs extends AsyncTask<String, Void, String>
    {
        ProgressDialog pd;

        Activity act;
        String response;
        Hashtable list;
        public AsyncCallContactUs(Hashtable list,Activity act) {
            this.act = act;
            pd = ProgressDialog.show(act, "", "Please Wait");
            this.list=list;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            response = WebCalls.wcFreeQoute(list, act);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            super.onPostExecute(result);
            showAlert(result);
        }

    }
    public void goMainHome(View v)
    {
        Intent intent = new Intent(FreeQouteActivity.this,MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View v)
    {
        finish();
    }


}
