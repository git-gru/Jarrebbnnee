package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 23-Mar-17.
 */

public class MyOrdersAdapter extends BaseAdapter{
    ArrayList<HashMap<String, String>> list;
    Context context;

    public MyOrdersAdapter(ArrayList<HashMap<String, String>> list, Context context) {
        this.list = list;
        this.context = context;
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
        View view = convertView;
        TextView tv1,tv2,tv3,tv4,tv5;
        ImageView iv;
        HashMap<String,String> map = list.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_my_orders, null);

        }
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
        tv1 = (TextView) view.findViewById(R.id.tv_wishlist_order_number);
        tv2 = (TextView) view.findViewById(R.id.tv_wishlist_date);
        tv3 = (TextView) view.findViewById(R.id.tv_wishlist_qty);
        tv4 = (TextView) view.findViewById(R.id.tv_wishlist_name);
        tv5 = (TextView) view.findViewById(R.id.tv_wishlist_details);
        tv4.setText(map.get("name"));
        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        tv3.setTypeface(custom_font);
        tv4.setTypeface(custom_font);
        tv5.setTypeface(custom_font);

        return view;
    }
}
