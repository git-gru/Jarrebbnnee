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

/**
 * Created by vi6 on 23-Mar-17.
 */

public class CustomDrawerAdapter extends BaseAdapter{

    ArrayList<POJO_Drawer> list;
    Context context;

    public CustomDrawerAdapter(ArrayList<POJO_Drawer> list, Context context) {
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
        POJO_Drawer pojo= list.get(position);
        TextView tv;
        ImageView iv;
        if (view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_drawer_item, null);
        }
        tv = (TextView) view.findViewById(R.id.tv_drawer_text);
        iv = (ImageView) view.findViewById(R.id.iv_drawer_image);
        if (pojo.getImgRes()>0) {
            iv.setImageResource(pojo.getImgRes());
        }
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
        tv.setTypeface(custom_font);
        tv.setText(pojo.getName());
        return view;
    }
}
