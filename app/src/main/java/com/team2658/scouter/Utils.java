package com.team2658.scouter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Gokul Swaminathan on 3/14/2018.
 */

public class Utils {

    public static void showToast(String msg, int time, Context context)
    {
        CharSequence text = msg;
        int duration = time;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
