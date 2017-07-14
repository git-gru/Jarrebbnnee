package com.jarrebbnnee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class FragmentProductListing extends Fragment{
    productListingAdapter adapter,adapter2;
    ArrayList<HashMap<String,String>>list, list2;
    String filtered_list;
    GridViewWithHeaderAndFooter gridView;
    TextView ttvv,ttvvv,load_more1;
    LinearLayout layout_sort, layout_filter,lll;
    String category_id="", max_price="", min_price="",category="",text="",pd_refer_id="";
    ProgressBar bar1;
    PopupWindow popupWindow;
    ListPopupWindow listPopupWindow;
    TextView tv1,tv2,tv3,tv4;
    public static String orderById="", orderBy="";
    View footerView;
    Button btn_load;
    RadioGroup radio_group;
    RadioButton radio_button;
    String[] sort={"Name AtoZ", "Name ZtoA", "Price LtoH","Price HtoL"};
    URLCollection urlCollection= new URLCollection();
    SharedPreferences userPref;
    public static final String UserData = "UserData";
    SharedPreferences.Editor userEditor;

    public static int page1 = 1;
    String status = "1";
    int i1;
    public static String tcount = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_listing, container, false);
        filtered_list = getArguments().getString("filter_categories");
        min_price=getArguments().getString("min_price");
        max_price=getArguments().getString("max_price");
        category_id=getArguments().getString("category_id");
        category=getArguments().getString("filter_categories");
        pd_refer_id=getArguments().getString("pd_refer_id");
        text=getArguments().getString("text");
        userPref = getActivity().getSharedPreferences(UserData, Context.MODE_PRIVATE);
        userPref = getActivity().getSharedPreferences(UserData, Context.MODE_PRIVATE);
        userEditor = userPref.edit();


        ttvv = (TextView) view.findViewById(R.id.ttvv);
        ttvvv = (TextView) view.findViewById(R.id.ttvvv);
        layout_sort = (LinearLayout) view.findViewById(R.id.layout_sort);
        layout_filter = (LinearLayout) view.findViewById(R.id.layout_filter);
        lll = (LinearLayout) view.findViewById(R.id.lll11);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0140m.ttf");
        ttvv.setTypeface(custom_font);
        ttvvv.setTypeface(custom_font);

        list=new ArrayList<HashMap<String, String>>();


        gridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gripProduct);
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
      //  View footer = inflater1.inflate(R.layout.footer_loadmore, null);
      //  gridView.addFooterView(footer);

       /* load_more1 = (TextView) footer.findViewById(R.id.tv_lording);
        bar1 = (ProgressBar) footer.findViewById(R.id.progressBar1);
        bar1.setClickable(false);*/
          footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.static_menu_footer, null, false);
       gridView.addFooterView(footerView);

        btn_load = (Button) footerView.findViewById(R.id.load_more);

      //  list= new ArrayList<HashMap<String,String>>();
        list2= new ArrayList<HashMap<String,String>>();

        adapter= new productListingAdapter(list,getActivity());
        gridView.setAdapter(adapter);
        prepareData("1");


        layout_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentFilter fragment= new FragmentFilter();
                Bundle args= new Bundle();
                args.putString("filtered_list", filtered_list);
                args.putString("category_id", category_id);
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        });

        layout_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDialog();
            }
        });

  /*      gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ad sub cart1", "" + list.size());
                boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount
                        && gridView.getChildAt(visibleItemCount - 1) != null
                        && gridView.getChildAt(visibleItemCount - 1)
                        .getBottom() <= gridView.getHeight();
                i1 = Integer.parseInt(tcount);

                if (!status.equals("1")) {
             //       load_more1.setVisibility(View.GONE);
              //      bar1.setVisibility(View.GONE);
                } else {
             //       load_more1.setVisibility(View.VISIBLE);
             //       bar1.setVisibility(View.VISIBLE);
                }
                try {
                    if (((i1 / 5) + 1) == page1) {
                //        load_more1.setVisibility(View.GONE);
                //        bar1.setVisibility(View.GONE);
                    } else {
                        if (page1 == 1) {
                            if (lastItem == true) {
                                page1++;

                                prepareData();
                            }
                        } else {
                            if (lastItem == true) {
                                page1++;

                                prepareData();
                                // load_more.setVisibility(View.GONE);
                            }

                        }
                    }

                } catch (Exception e) {
                    if (page1 == 1) {
                        if (lastItem == true) {
                            page1++;
                            list.clear();
                            Log.d("on", "on");
                            prepareData();
                        }
                    } else {
                        if (lastItem == true) {
                            page1++;
                            Log.d("kohnjion", "mkkm");
                            prepareData();
                            // load_more.setVisibility(View.GONE);
                        }

                    }

                }
            }
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
              /*  FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentProductDetails llf = new FragmentProductDetails();
                ft.replace(R.id.content, llf);
                ft.commit();*/
                HashMap<String,String> mapTemp =list.get(position);
                FragmentProductDetails fragment= new FragmentProductDetails();
                Bundle args= new Bundle();
                args.putString("product_id", mapTemp.get("pd_refer_id"));
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        });
        return view;
    }



    private void  prepareData(String page11)  {
        Log.e("--INSIDE--","--API--");
        String page = String.valueOf(page1);
        String url=urlCollection.filter+"&category_id="+category+"&orderById="+orderById+"&orderBy="+orderBy+"&min_price="+min_price+"&max_price="+max_price+"&product_name="+text+"&page="+page11+"&pd_refer_id="+pd_refer_id;
    /*    String url=URLCollection.filter+"&category_id="+category_id+"&orderById="+orderById+"&orderBy="+orderBy+"&page="+page11;*/
        Log.e("url",url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

    //    ServerResponse.CallApi(url, getActivity());
       // JSONObject obj = new JSONObject(response);
     //   ServerResponse.CallApi(url, getActivity());
    //    JSONObject response = ServerResponse.CallApi(url, getActivity());// ServerResponse.getResponse();
   //     Log.e("nitesh", ""+response);
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    pDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                     status = object.getString("status");
                    if (status.equals("0")) {

                        final String nextPage = object.getString("nextPage");
                        String totalRecord = object.getString("totalRecord");

                        JSONArray data = object.getJSONArray("data");
                        if (nextPage.equals("0")) {
                            gridView.removeFooterView(footerView);
                            Log.e("footer","removed");
                        } else {
                            btn_load.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    prepareData(nextPage);

                                }
                            });
                        }
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject mObject = data.getJSONObject(i);
                            HashMap<String,String> map= new HashMap<>();
                            String pd_refer_id = mObject.getString("pd_refer_id");
                            String pd_name = mObject.getString("pd_name");
                            String pd_price = mObject.getString("pd_price");
                            String images = mObject.getString("images");
                            map.put("pd_refer_id", pd_refer_id);
                            map.put("pd_name", pd_name);
                            map.put("pd_price", pd_price);
                            map.put("images", images);

                           // POJO_product_list pojo= new POJO_product_list(pd_refer_id, pd_name, pd_price, images);
                            list.add(map);
                            adapter.notifyDataSetChanged();
                         //   Log.e("added", "position : "+i+"\nname="+pd_name+"\nprice="+pd_price+"\nimages="+images);
                            pDialog.hide();
                           /* adapter.notifyDataSetChanged();*/
                        }

                        Log.e("======List Size========",""+list.size());
                        int pos= gridView.getFirstVisiblePosition();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            gridView.setSelectionFromTop(pos+1,0);
                            Log.e("pos", ""+pos+1);
                        }
                      //  gridView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request, "product list");
       /* DemoPojo pojo1= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo2= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo3= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo4= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo5= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo6= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo7= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo8= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo9= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo10= new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo11 = new DemoPojo("Xaomi Note 4", "$500");
        DemoPojo pojo12= new DemoPojo("Xaomi Note 4", "$500");

        list.add(pojo1);
        list.add(pojo2);
        list.add(pojo3);
        list.add(pojo4);
        list.add(pojo5);
        list.add(pojo6);
        list.add(pojo7);
        list.add(pojo8);
        list.add(pojo9);
        list.add(pojo10);
        list.add(pojo11);
        list.add(pojo12);
        adapter.notifyDataSetChanged();*/

    }



    public void showSortDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        radio_group = (RadioGroup) dialogView.findViewById(R.id.radioGroup);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                int selectedId=radio_group.getCheckedRadioButtonId();
                Log.e("selectedId",""+selectedId);
                radio_button=(RadioButton)dialogView.findViewById(selectedId);
                String sortBy = radio_button.getText().toString();
                if (sortBy.equals("Price Low-High")) {
                    orderById = "pd_price";
                    orderBy = "asc";
                    userEditor.putString("orderById",orderById);
                    userEditor.putString("orderBy",orderBy);
                    userEditor.commit();
                }else if (sortBy.equals("Price High-Low")) {
                    orderById = "pd_price";
                    orderBy = "desc";
                    userEditor.putString("orderById",orderById);
                    userEditor.putString("orderBy",orderBy);
                    userEditor.commit();
                }else if (sortBy.equals("Product name Ascending")) {
                    orderById = "pd_name";
                    orderBy = "asc";
                    userEditor.putString("orderById",orderById);
                    userEditor.putString("orderBy",orderBy);
                    userEditor.commit();
                }else if (sortBy.equals("Product name Descending")) {
                    orderById = "pd_name";
                    orderBy = "desc";
                    userEditor.putString("orderById",orderById);
                    userEditor.putString("orderBy",orderBy);
                    userEditor.commit();
                }
                Toast.makeText(getActivity(),radio_button.getText(),Toast.LENGTH_SHORT).show();
                userEditor.putString("sortBy", radio_button.getText().toString());
                userEditor.commit();
                if (list.size()>0) {
                    list.clear();
                    int footerViewCount = gridView.getFooterViewCount();
                    Log.e("footer", "view count="+footerViewCount);
                    /*if ()
                    gridView.removeView(footerView);
                 //   gridView.removeFooterView(footerView);
                    Log.e("footer","terminated");
                    gridView.addFooterView(footerView);
                    */
                    if (footerViewCount<=0) {
                        Log.e("footer", "inside condition");

                        ((ViewGroup)footerView.getParent()).removeView(footerView);

                        gridView.addFooterView(footerView);
                        Log.e("footer", "added");
                    }

                 //   gridView.addFooterView(footerView);
                }
                prepareData("1");
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
