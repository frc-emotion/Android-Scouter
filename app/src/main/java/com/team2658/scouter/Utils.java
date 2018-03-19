package com.team2658.scouter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Gokul Swaminathan on 3/14/2018.
 *
 * This class holds all static methods that are going to be used lots of times.
 */

public class Utils {

    //basically show a toast message on android
    public static void showToast(String msg, int time, Context context) {
        CharSequence text = msg;
        int duration = time;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //converts answers of a checkbox to string
    public static String checkBoxToString(CheckBox check) {
        if (check.isChecked()) {
            return "Yes";
        } else {
            return "No";
        }
    }

    //navigate to url
    public static Intent webIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    //navigate to other pages
    public static Intent actIntent(Context context, Class<?> cls) {
        return new Intent(context, cls);
    }

}
