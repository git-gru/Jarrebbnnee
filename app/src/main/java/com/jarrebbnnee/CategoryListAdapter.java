package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class CategoryListAdapter extends BaseAdapter{

    Context context;
    ArrayList<HashMap<String,String>> listMap;
    HashMap<String, String> map;
    public CategoryListAdapter(Context context, ArrayList<HashMap<String,String>> listMap) {
        this.context = context;
        this.listMap = listMap;
    }

    @Override
    public int getCount() {
        return listMap.size();
    }

    @Override
    public Object getItem(int position) {
        return listMap.get(position);
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
            view = inflater.inflate(R.layout.single_category_listing_item, null);
        }
        map = listMap.get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
        tv = (TextView) view.findViewById(R.id.tv_category_listing_test);
        tv.setTypeface(custom_font);
        tv.setText(map.get("pc_name"));
        return view;
    }
}
