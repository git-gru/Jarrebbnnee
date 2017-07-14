package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class CountryListAdapter extends BaseAdapter{

    Context context;
    ArrayList<countryPOJO> list;
    countryPOJO pojo;

    public CountryListAdapter(Context context, ArrayList<countryPOJO> listMap) {
        this.context = context;
        this.list = listMap;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        View view = convertView;
        if (view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_spinner_item, null);
            pojo = list.get(position);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
            tv = (TextView) view.findViewById(R.id.text1);
            tv.setTypeface(custom_font);
            tv.setText(pojo.getCountry());

        }

        return view;
    }
}
