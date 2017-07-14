package com.jarrebbnnee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 23-Mar-17.
 */

public class FragmentMyOrders extends Fragment{
    ArrayList<HashMap<String, String>> list_map;
    ListView list_my_orders;
    MyOrdersAdapter ordersAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_my_orders, container, false);
        list_map= new ArrayList<HashMap<String, String>>();
        list_my_orders = (ListView) view.findViewById(R.id.list_my_orders);
        ordersAdapter= new MyOrdersAdapter(list_map, getActivity());
        list_my_orders.setAdapter(ordersAdapter);
        prepareData();
        return view;
    }

    private void prepareData() {
        HashMap<String,String> map11= new HashMap<String, String>();
        map11.put("name", "Blue Luminous EDC Hand Spinner Fidget Spiral Focus Reduce Stress Tool Hybrid Ceramic");
        HashMap<String,String> map12= new HashMap<String, String>();
        map12.put("name", "Blue Luminous EDC Hand Spinner Fidget Spiral Focus Reduce Stress Tool Hybrid Ceramic");
        HashMap<String,String> map13= new HashMap<String, String>();
        map13.put("name", "Blue Luminous EDC Hand Spinner Fidget Spiral Focus Reduce Stress Tool Hybrid Ceramic");
        HashMap<String,String> map14= new HashMap<String, String>();
        map14.put("name", "Blue Luminous EDC Hand Spinner Fidget Spiral Focus Reduce Stress Tool Hybrid Ceramic");
        HashMap<String,String> map15= new HashMap<String, String>();
        map15.put("name", "Blue Luminous EDC Hand Spinner Fidget Spiral Focus Reduce Stress Tool Hybrid Ceramic");

        list_map.add(map11);
        list_map.add(map12);
        list_map.add(map13);
        list_map.add(map14);
        list_map.add(map15);

        ordersAdapter.notifyDataSetChanged();
    }
}
