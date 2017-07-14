package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

public class CountryListAdapter2 extends BaseAdapter{

	Context c;
	ArrayList<HashMap<String, String>> cityList;
	HashMap<String, String> resultp;
	LayoutInflater inflater;


	public CountryListAdapter2(Context activity,
							  ArrayList<HashMap<String, String>> stateList) {
		this.c = activity;
		this.cityList = stateList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tvAName;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.custom_splga, parent, false);
		
		resultp = new HashMap<String, String>();
		resultp=cityList.get(position);
		
		tvAName = (TextView)itemView.findViewById(R.id.tv);

		Typeface custom_font = Typeface.createFromAsset(c.getAssets(), "tt0140m.ttf");
		tvAName.setText(resultp.get("country"));
		tvAName.setTypeface(custom_font);
		return itemView;
	}

}
