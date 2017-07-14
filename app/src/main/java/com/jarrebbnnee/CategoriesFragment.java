package com.jarrebbnnee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class CategoriesFragment extends Fragment{
    CategoryListAdapter listAdapter;
    ListView listCategories;
    ArrayList<HashMap<String, String>> list_drawer;
    Database database;
    HashMap<String,String> map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_categories, container, false);
        listCategories = (ListView) view.findViewById(R.id.listCategories);
        database = new Database(getActivity());
        prepareDate();
        listCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                map = list_drawer.get(position);
                Fragment fragment = new FragmentProductListing();
                Bundle args= new Bundle();
                args.putString("filter_categories", map.get("pc_id"));
                args.putString("category_id", "");
                args.putString("min_price","");
                args.putString("max_price","");
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

    private void prepareDate() {
        list_drawer = database.viewLastSyncCategories();
        listAdapter = new CategoryListAdapter(getActivity(), list_drawer);
        listCategories.setAdapter(listAdapter);
    }
}
