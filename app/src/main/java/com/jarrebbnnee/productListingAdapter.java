package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class productListingAdapter extends BaseAdapter{
    ArrayList<HashMap<String,String>> list;
    Context context;


    public productListingAdapter(ArrayList<HashMap<String,String>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextView tv_name, tv_price;
        ImageView image1;

        if (view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_product_listing, null);
        }
        tv_name = (TextView) view.findViewById(R.id.tv_product_name);
        tv_price = (TextView) view.findViewById(R.id.tv_product_price);
        image1 = (ImageView) view.findViewById(R.id.image1);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
        tv_name.setTypeface(custom_font);
        tv_price.setTypeface(custom_font);
        HashMap<String,String> map = list.get(position);
    //    Log.e("displaying", "Position="+position+"\nname="+map.get("pd_name")+"\nprice="+map.get("pd_name")+"\nimages="+map.get("images"));
        Picasso.with(context).load(map.get("images")).into(image1);
        tv_name.setText(map.get("pd_name"));
        tv_price.setText("$"+map.get("pd_price"));
        return view;
    }

    public void refresh(ArrayList<HashMap<String, String>> items) {

        this.list = items;
        notifyDataSetChanged();
    }
}
