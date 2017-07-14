package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

/**
 * Created by vi6 on 18-Mar-17.
 */

public class FragmentFilter extends Fragment{

 //   RangeSeekBar<Integer> range_seekbar;

    EditText et_search_by_product_name;
    TextView tvvv1,tvvv2,tvvv3,tvvv4,tvvv5,tvvv6,tvvv7,tvvv8,tvMin,tvMax,tvvv;
    ArrayList<String> filter_categoties;
    ArrayList<HashMap<String, String>> list_search;
    String already_selected,max_price, min_price;
    String text="",pd_refer_id="";
    ListView lv_search;
    LinearLayout layout_clear, layout_apply;
   SearvhListAdapter arrad;
    ArrayList<HashMap<String, String>> list_drawer;
    GridView grid;
    ListPopupWindow listPopupWindow;
    FilterGridAdapter gridAdapter;
    URLCollection urlCollection= new URLCollection();
    HashMap<String, String> map;
    Database database;
    boolean check = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_filter, container, false);
        already_selected = getArguments().getString("filtered_list");
        final String category_id=getArguments().getString("category_id");
        database= new Database(getActivity());

        /*range_seekbar = (RangeSeekBar<Integer>) view.findViewById(R.id.range_seekbar);
        range_seekbar.setTextAboveThumbsColor(R.color.colorPrimary);
        range_seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
             //   Toast.makeText(getActivity(), "Min value= "+minValue+"\nMax value="+maxValue,Toast.LENGTH_SHORT).show();
                Log.e("range", "Min value= "+minValue+"\nMax value="+maxValue);
            }
        });
        range_seekbar.setNotifyWhileDragging(true);*/
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar1);
        grid = (GridView) view.findViewById(R.id.grid_cat);

// get min and max text view
        et_search_by_product_name = (EditText) view.findViewById(R.id.et_search_by_product_name);
        tvMin = (TextView) view.findViewById(R.id.textMin1);
        tvMax = (TextView) view.findViewById(R.id.textMax1);

/*
        tvvv7 = (TextView) view.findViewById(R.id.tvvv7);*/
        tvvv8 = (TextView) view.findViewById(R.id.tvvv8);
        tvvv = (TextView) view.findViewById(R.id.tvvv);

        list_search= new ArrayList<>();

        lv_search = (ListView) view.findViewById(R.id.lv_search);
        lv_search.setVisibility(View.GONE);
        arrad = new SearvhListAdapter(getActivity(), list_search);
      /*  listPopupWindow = new ListPopupWindow(
                getActivity());
        listPopupWindow.setAdapter(arrad);
        listPopupWindow.setAnchorView(et_search_by_product_name);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        listPopupWindow.setHeight(200);

        listPopupWindow.setModal(true);
        listPopupWindow.show();*/
         lv_search.setAdapter(arrad);

       /* cb_electronics = (CheckBox) view.findViewById(R.id.cb_electronics);
*/

        CallCategoryList();

        layout_apply = (LinearLayout) view.findViewById(R.id.layout_apply_filter);
        layout_clear = (LinearLayout) view.findViewById(R.id.layout_clear_filter);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0140m.ttf");
        tvvv.setTypeface(custom_font);
        et_search_by_product_name.setTypeface(custom_font);

        tvvv8.setTypeface(custom_font);
        tvMin.setTypeface(custom_font);
        tvMax.setTypeface(custom_font);

        filter_categoties= new ArrayList<String>();
        /*for (int i=0; i<already_selected.size();i++) {
            String s = already_selected.get(i);
            filter_categoties.add(s);
            Log.e("received",s);
            if (s.equals(tvvv1.getText().toString())) {
                cb_bags.setChecked(true);
            }else if (s.equals(tvvv2.getText().toString())) {
                cb_mobile.setChecked(true);
            }else if (s.equals(tvvv3.getText().toString())) {
                cb_cellphone.setChecked(true);
            }else if (s.equals(tvvv4.getText().toString())) {
                cb_tablet.setChecked(true);
            }else if (s.equals(tvvv5.getText().toString())) {
                cb_computer.setChecked(true);
            }else if (s.equals(tvvv6.getText().toString())) {
                cb_toys.setChecked(true);
            }else if (s.equals(tvvv7.getText().toString())) {
                cb_electronics.setChecked(true);
            }
        }*/


// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("$"+String.valueOf(minValue));
                tvMax.setText("$"+String.valueOf(maxValue));
                max_price = maxValue.toString();
                min_price = minValue.toString();
            }
        });

     /*   cb_computer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_computer.isChecked()) {
                    filter_categoties.add(tvvv5.getText().toString());
                    Log.e("added", tvvv5.getText().toString());
                } else if (!cb_computer.isChecked()) {
                    if (filter_categoties.contains(tvvv5.getText().toString())) {
                        filter_categoties.remove(tvvv5.getText().toString());
                        Log.e("removed", tvvv5.getText().toString());
                    }

                }
            }
        });*/

        et_search_by_product_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //Toast.makeText(getActivity(), "beforeTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getActivity(), "onTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>=3) {
                     text= s.toString();
             //       listPopupWindow.show();
                    Log.e("text",text);
                    Log.e("===afterTextChanged","===afterTextChanged");
                    CallFilter(text);
                    //Toast.makeText(getActivity(), "afterTextChanged", Toast.LENGTH_SHORT).show();
                }

            }
        });
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.e("range", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                map = list_search.get(position);
                String s = map.get("pd_name");
                et_search_by_product_name.setText(s);
                pd_refer_id = map.get("pd_refer_id");
                check = false;
                Log.e("search selected", pd_refer_id);
                if (list_search.size() > 0) {
                    list_search.clear();
                    Log.e("list_search","cleared");
                }
                lv_search.setVisibility(View.GONE);

            }
        });
        layout_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category="";

                for (int i=0;i<filter_categoties.size();i++) {
                    Log.e("final", filter_categoties.get(i));
                    category = category.concat(filter_categoties.get(i)+",");
                }
                if (category != null && category.length() > 0) {
                    category = category.substring(0, category.length()-1);
                }
                Log.e("finalized", category);

                FragmentProductListing fragment= new FragmentProductListing();
                Bundle args= new Bundle();
                args.putString("filter_categories", category);
                args.putString("min_price", min_price);
                args.putString("category_id", ""+category_id);
                args.putString("max_price", max_price);

                if (check) {
                    args.putString("text", text);
                } else {
                    args.putString("text", "");
                }
                args.putString("pd_refer_id", pd_refer_id);
                Log.e("fragment", "category "+category+" min_price"+min_price+" max_price"+max_price+" pd_refer_id"+pd_refer_id+" text"+text+" filter_categories"+filter_categoties);
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        });
        layout_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter_categoties.size() > 0) {
                    filter_categoties.clear();
                }
                FragmentProductListing fragment= new FragmentProductListing();
                Bundle args= new Bundle();
                args.putString("filter_categories", category_id);
                args.putString("min_price", "");
                args.putString("category_id", "");
                args.putString("max_price", "");
                args.putString("text", "");
                args.putString("pd_refer_id", "");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        });



        return view;
    }



    private void CallCategoryList() {

        list_drawer = database.viewLastSyncCategories();
        gridAdapter = new FilterGridAdapter(list_drawer, getActivity());
        grid.setAdapter(gridAdapter);
    }


    private void CallFilter(String text) {
        String url=urlCollection.filter+"&product_name="+text;
        Log.e("url", url);

        if (list_search.size()>0) {
            list_search.clear();
            Log.e("list", "cleared");
        }
        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object= new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        JSONArray data = object.getJSONArray("data");
                        if (data.length() <= 0) {
                            lv_search.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No such item", Toast.LENGTH_SHORT).show();
                        } else {
                            lv_search.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < data.length(); i++) {

                            JSONObject objec = data.getJSONObject(i);
                            HashMap<String, String> map= new HashMap<String, String>();
                            String pd_name = objec.getString("pd_name");
                            String pd_refer_id = objec.getString("pd_refer_id");
                            map.put("pd_name", pd_name);
                            map.put("pd_refer_id", pd_refer_id);
                            list_search.add(map);
                            arrad.notifyDataSetChanged();
                        }
                    }

                    Log.e("size", ""+list_search.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(sr, "filter products");
    }





    public class FilterGridAdapter extends BaseAdapter {

        ArrayList<HashMap<String, String>> list;
        HashMap<String, String> map;
        Context context;

        public FilterGridAdapter(ArrayList<HashMap<String, String>> list, Context context) {
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
            final TextView tv;
            final CheckBox checkBox;
            View view = convertView;
            if (view==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.single_grid, null);
            }
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
            tv = (TextView) view.findViewById(R.id.rrtvvv7);
            map = list.get(position);
            tv.setText(map.get("pc_name"));
            checkBox = (CheckBox) view.findViewById(R.id.cb_electronics);
            checkBox.setTag(map.get("pc_id"));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String tag = buttonView.getTag().toString();
                    Log.e("tag", tag);
                    if (checkBox.isChecked()) {
                        filter_categoties.add(tag);
                        Log.e("added", tag);
                    } else if (!checkBox.isChecked()) {
                        if (filter_categoties.contains(tag)) {
                            filter_categoties.remove(tag);
                            Log.e("removed",tag);
                        }

                    }
                }
            });
            tv.setTypeface(custom_font);
            return view;
        }
    }

}
