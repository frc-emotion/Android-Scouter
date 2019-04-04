package com.gsnathan.android_scouter;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import net.glxn.qrgen.android.QRCode;

import java.io.IOException;
import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;


public class QRDialog extends DialogFragment {

    public static String TAG = "FullScreenDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.qr_layout, container, false);
        try {
            Bitmap myBitmap = QRCode.from(MainActivity.ultimate.toString()).bitmap();
            ImageView image = view.findViewById(R.id.qr_code);
            image.setImageBitmap(myBitmap);
        }catch(IllegalArgumentException e){
            Snackbar mySnackBar = Snackbar.make(view, "Empty File!", Snackbar.LENGTH_SHORT);
            mySnackBar.show();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
