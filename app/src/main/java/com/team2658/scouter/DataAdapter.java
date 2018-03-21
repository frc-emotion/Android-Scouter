package com.team2658.scouter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Gokul Swaminathan on 3/20/2018.
 *
 * This class holds the array adapter for the grid view.
 */

public class DataAdapter extends BaseAdapter
{

        private final Context mContext;
        private final String[] data;

        public DataAdapter(Context context, String[] data) {
            this.mContext = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //display each piece of data in the text
            TextView dummyTextView = new TextView(mContext);
            dummyTextView.setText(data[position]);
            return dummyTextView;
        }

}