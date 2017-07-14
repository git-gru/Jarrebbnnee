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
 * Created by vi6 on 21-Mar-17.
 */

public class AccountListAdapter extends BaseAdapter{

    ArrayList<DemoPojo> list;
    Context context;

    public AccountListAdapter(ArrayList<DemoPojo> list, Context context) {
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
        TextView tv;
        ImageView iv;
        DemoPojo pojo = list.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_account_item, null);

        }
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
        iv = (ImageView) view.findViewById(R.id.iv_account);
        tv = (TextView) view.findViewById(R.id.tv_account);
        iv.setImageResource(pojo.getImageId());
        tv.setText(pojo.getProductName());
        tv.setTypeface(custom_font);
        return view;
    }
}
