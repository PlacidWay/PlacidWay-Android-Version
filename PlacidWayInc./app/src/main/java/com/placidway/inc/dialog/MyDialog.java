package com.placidway.inc.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.placidway.inc.R;

/**
 * Created by erum on 03/11/2014.
 */
public class MyDialog {

    public static AlertDialog.Builder create(final Context context, final LayoutInflater layoutInflater, final String title, final CharSequence[] content) {
        View view = layoutInflater.inflate(R.layout.dialog_country, null);
        //((TextView)view.findViewById(R.id.textViewTitleDialog)).setText(title);
        //((TextView)view.findViewById(R.id.textViewContentDialog)).setText(content);

        return new AlertDialog.Builder(context).setView(view);
    }

}
