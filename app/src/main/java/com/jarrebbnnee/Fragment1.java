package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vi6 on 08-Mar-17.
 */

public class Fragment1 extends Fragment{

    RecyclerView recyclerView,recyclerView2;
    ArrayList<DemoPojo> pojoArrayList, latest_classified_list;
    RecyclerAdapter adap;
    TextView tv1,tv2,tv3,tv4;
    CirclePageIndicator indicator;
    RecyclerAdapter_latest_classified adapter_latest_classified;
    URLCollection collection= new URLCollection();

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
  //  private static final Integer[] IMAGES= {R.drawable.banner,R.drawable.demo,R.drawable.nexus};
    ArrayList<HashMap<String,String>> IMAGES= new ArrayList<>();
    private ArrayList<String> ImagesArray = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_1, container, false);


        pojoArrayList= new ArrayList<DemoPojo>();
        latest_classified_list=new ArrayList<DemoPojo>();
        adap= new RecyclerAdapter(getActivity(),pojoArrayList);
        adapter_latest_classified=new RecyclerAdapter_latest_classified(getActivity(), latest_classified_list);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        mPager = (ViewPager)view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        init();
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0142m.ttf");
        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        tv3.setTypeface(custom_font);
        tv4.setTypeface(custom_font);

        StringRequest str= new StringRequest(Request.Method.GET, "www.google.co.in", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(str, "hi");

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(adap);
        recyclerView2.setAdapter(adapter_latest_classified);
        prepareData();
        prepareLatestClassifiedData();
        Toast.makeText(getActivity(), "Fragment 1 called ", Toast.LENGTH_SHORT).show();



        return view;
    }

    private void prepareLatestClassifiedData() {
        DemoPojo pojo1= new DemoPojo("Google Nexus","03-Dec-2016", "$400");
        DemoPojo pojo2= new DemoPojo("Google Nexus","03-Dec-2016", "$500");
        DemoPojo pojo3= new DemoPojo("Google Nexus","03-Dec-2016", "$600");
        DemoPojo pojo4= new DemoPojo("Google Nexus","03-Dec-2016", "$400");
        DemoPojo pojo5= new DemoPojo("Google Nexus","03-Dec-2016", "$500");
        DemoPojo pojo6= new DemoPojo("Google Nexus","03-Dec-2016", "$400");
        DemoPojo pojo7= new DemoPojo("Google Nexus","03-Dec-2016", "$400");
        DemoPojo pojo8= new DemoPojo("Google Nexus","03-Dec-2016", "$600");

        latest_classified_list.add(pojo1);
        latest_classified_list.add(pojo2);
        latest_classified_list.add(pojo3);
        latest_classified_list.add(pojo4);
        latest_classified_list.add(pojo5);
        latest_classified_list.add(pojo6);
        latest_classified_list.add(pojo7);
        latest_classified_list.add(pojo8);
        adapter_latest_classified.notifyDataSetChanged();
    }

    private void prepareData() {
        DemoPojo pojo1= new DemoPojo("Product 1", "$2001");
        DemoPojo pojo2= new DemoPojo("Product 2", "$2002");
        DemoPojo pojo3= new DemoPojo("Product 3", "$2003");
        DemoPojo pojo4= new DemoPojo("Product 4", "$2004");
        DemoPojo pojo5= new DemoPojo("Product 5", "$2005");
        DemoPojo pojo6= new DemoPojo("Product 6", "$2006");
        DemoPojo pojo7= new DemoPojo("Product 7", "$2007");

        pojoArrayList.add(pojo1);
        pojoArrayList.add(pojo2);
        pojoArrayList.add(pojo3);
        pojoArrayList.add(pojo4);
        pojoArrayList.add(pojo5);
        pojoArrayList.add(pojo6);
        pojoArrayList.add(pojo7);
        adap.notifyDataSetChanged();
    }

    private void init() {
        ImagesArray= new ArrayList<String>();
        final SlidingImageAdapter imageAdapter = new SlidingImageAdapter(ImagesArray, getActivity());
        mPager.setAdapter(imageAdapter);

        String url = collection.homeSlider;
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
       StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   pDialog.dismiss();
                   JSONObject object= new JSONObject(response);
                    String   status = object.getString("status");
                   if (status.equals("0")) {
                       JSONArray data = object.getJSONArray("data");
                       for (int i=0;i<data.length();i++) {
                           JSONObject objec = data.getJSONObject(i);
                           String   sir_img = objec.getString("sir_img");
                           String   sir_heading_text = objec.getString("sir_heading_text");
                           String   sir_sub_heading_text = objec.getString("sir_sub_heading_text");
                           HashMap<String, String> map= new HashMap<String, String>();
                           map.put("sir_img",sir_img);
                           map.put("sir_heading_text",sir_heading_text);
                           map.put("sir_sub_heading_text",sir_sub_heading_text);
                           IMAGES.add(map);
                           ImagesArray.add(sir_img);
                           imageAdapter.notifyDataSetChanged();
                       }

                   }
               } catch (JSONException e) {
                   Log.e("Server","error");
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });

        AppController.getInstance().addToRequestQueue(sr, "Home Slider");

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(4 * density);

        NUM_PAGES =IMAGES.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
}
