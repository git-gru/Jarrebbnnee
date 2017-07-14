package com.jarrebbnnee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vi6 on 15-Mar-17.
 */

public class FragmentProductDetails extends Fragment{

    ViewPager pager2;
    CirclePageIndicator indicator2;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
 //   private static final Integer[] IMAGES= {R.drawable.banner,R.drawable.demo,R.drawable.nexus};
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    ImageView minus1, minus2, iv_share, iv_add_wishlist, iv_add_cart;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20,tv21,tv22,tv23,tv24,tv25,tv26,tvTemp, tvCommentName,tvComment;
    LinearLayout desc1, desc2,UserComments,add_to_cart;
    EditText et_comment;
    SharedPreferences fbPref;
    SaveSharedPrefrence prefrence;
    String u_id, product_id,comment, is_in_wishlist;
    Typeface custom_font;
    Button btn_post_comment;
    public static final String FacebookData = "FacebookData";
    URLCollection collection= new URLCollection();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_product_details_2, null);
        pager2 = (ViewPager) view.findViewById(R.id.pager2);
        indicator2 = (CirclePageIndicator) view.findViewById(R.id.indicator2);
        prefrence= new SaveSharedPrefrence();
        fbPref = getActivity().getSharedPreferences(FacebookData, Context.MODE_PRIVATE);
        product_id = getArguments().getString("product_id");
        u_id = prefrence.getUserID(getActivity());
        btn_post_comment = (Button) view.findViewById(R.id.btn_post_comment);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv5 = (TextView) view.findViewById(R.id.tv5);
        iv_add_cart = (ImageView) view.findViewById(R.id.iv_add_cart);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        iv_add_wishlist = (ImageView) view.findViewById(R.id.iv_add_wishlist);

        /*tvCommentName = (TextView) view.findViewById(R.id.tvCommentUserName);
        tvComment = (TextView) view.findViewById(R.id.tvComment);*/
        UserComments = (LinearLayout) view.findViewById(R.id.UserComments);
        add_to_cart = (LinearLayout) view.findViewById(R.id.add_to_cart);
      /*  tv6 = (TextView) view.findViewById(R.id.tv6);
        tv7 = (TextView) view.findViewById(R.id.tv7);
        tv8 = (TextView) view.findViewById(R.id.tv8);
        tv9 = (TextView) view.findViewById(R.id.tv9);
        tv10 = (TextView) view.findViewById(R.id.tv10);
        tv11 = (TextView) view.findViewById(R.id.tv11);
        tv12 = (TextView) view.findViewById(R.id.tv12);
        tv13 = (TextView) view.findViewById(R.id.tv13);
        tv14 = (TextView) view.findViewById(R.id.tv14);
        tv15 = (TextView) view.findViewById(R.id.tv15);
        tv16 = (TextView) view.findViewById(R.id.tv16);
        tv17 = (TextView) view.findViewById(R.id.tv17);
        tv18 = (TextView) view.findViewById(R.id.tv18);
        tv19 = (TextView) view.findViewById(R.id.tv19);
        tv20 = (TextView) view.findViewById(R.id.tv10);
        tv21 = (TextView) view.findViewById(R.id.tv21);
        tv22 = (TextView) view.findViewById(R.id.tv22);
        tv23 = (TextView) view.findViewById(R.id.tv23);
        tv24 = (TextView) view.findViewById(R.id.tv24);*/
        tv25 = (TextView) view.findViewById(R.id.tv25);
        tv26 = (TextView) view.findViewById(R.id.tv26);
        tvTemp = (TextView) view.findViewById(R.id.tvTemp);

        et_comment = (EditText) view.findViewById(R.id.et_comment);
        desc1 = (LinearLayout) view.findViewById(R.id.desc1);
        desc2 = (LinearLayout) view.findViewById(R.id.desc2);
        minus1 = (ImageView) view.findViewById(R.id.minus1);
        minus2 = (ImageView) view.findViewById(R.id.minus2);

       custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0142m.ttf");

        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        tv3.setTypeface(custom_font);
        tv4.setTypeface(custom_font);
        tv5.setTypeface(custom_font);
        btn_post_comment.setTypeface(custom_font);
       /* tv6.setTypeface(custom_font);
        tv7.setTypeface(custom_font);
        tv8.setTypeface(custom_font);
        tv9.setTypeface(custom_font);
        tv10.setTypeface(custom_font);
        tv11.setTypeface(custom_font);
        tv12.setTypeface(custom_font);
        tv13.setTypeface(custom_font);
        tv14.setTypeface(custom_font);
        tv15.setTypeface(custom_font);
        tv16.setTypeface(custom_font);
        tv17.setTypeface(custom_font);
        tv18.setTypeface(custom_font);
        tv19.setTypeface(custom_font);
        tv20.setTypeface(custom_font);
        tv21.setTypeface(custom_font);
        tv22.setTypeface(custom_font);
        tv23.setTypeface(custom_font);
        tv24.setTypeface(custom_font);*/
        tv25.setTypeface(custom_font);
        tv26.setTypeface(custom_font);
        tvTemp.setTypeface(custom_font);
       /* tvCommentName.setTypeface(custom_font);
        tvComment.setTypeface(custom_font);*/
        et_comment.setTypeface(custom_font);

        prepareData();

        desc1.setVisibility(View.VISIBLE);
        desc2.setVisibility(View.VISIBLE);

        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desc1.getVisibility() == View.VISIBLE) {
                    desc1.setVisibility(View.GONE);
                    minus1.setImageResource(R.drawable.icon_plus);
                } else if (desc1.getVisibility() == View.GONE) {
                    desc1.setVisibility(View.VISIBLE);
                    minus1.setImageResource(R.drawable.icon_minus);
                }
            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (desc2.getVisibility() == View.VISIBLE) {
                    desc2.setVisibility(View.GONE);
                    minus2.setImageResource(R.drawable.icon_plus);
                } else if (desc2.getVisibility() == View.GONE) {
                    desc2.setVisibility(View.VISIBLE);
                    minus2.setImageResource(R.drawable.icon_minus);
                }
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAddToCart();
            }
        });

        btn_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = et_comment.getText().toString().trim();
                if (comment.length()>0) {
                    CallCommentApi(comment);
                }
            }
        });

        iv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAddToCart();
            }
        });

        iv_add_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_in_wishlist.equals("0")) {
                    CallAddWishlist();
                } else {
                    Toast.makeText(getActivity(),"product already in wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        
      //  init();
        return view;
    }

    private void CallAddWishlist() {
        String url= collection.addWishlist+"&product_id="+product_id+"&u_id="+u_id;
        Log.e("url", "url");
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Posting your comment...");
        pDialog.show();
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    JSONObject object= new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        String message = object.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        String wl_id = object.getString("wl_id");
                    } else {
                        String message = object.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        AppController.getInstance().addToRequestQueue(request, "add comment");
    }

    private void CallCommentApi(String comment) {
        String url=collection.addComment+"&u_id="+u_id+"&product_id="+product_id+"&com_desc="+comment;
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Posting your comment...");
        pDialog.show();
        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    pDialog.dismiss();
                    if (status.equals("0")) {
                        String com_id = object.getString("com_id");
                        Log.e("com_id", com_id);
                        prepareData();
                    } else {
                        String message = object.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        AppController.getInstance().addToRequestQueue(sr, "add comment");
    }

    private void CallAddToCart() {
        String url=collection.addToCart+"&u_id="+u_id+"&product_id="+product_id+"&product_qty=1";
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    pDialog.dismiss();
                    if (status.equals("0")) {
                        Toast.makeText(getActivity(),"Item added to cart successfuly",Toast.LENGTH_SHORT).show();
                        String cart_id = object.getString("cart_id");
                        Log.e("cart_id", cart_id);
                    } else {
                        String message = object.getString("message");
                        Log.e("message", message);
                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
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
        AppController.getInstance().addToRequestQueue(sr, "add to cart");
    }


    void prepareData() {
        String url=collection.productDetails+"&u_id="+u_id+"&product_id="+product_id;
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("0")) {
                        pDialog.dismiss();
                        JSONObject data = jsonObject.getJSONObject("data");
                        String pd_name = data.getString("pd_name");
                        tv1.setText(pd_name);
                        tv3.setText(product_id);
                        String pd_price = data.getString("pd_price");
                        tv4.setText(pd_price);
                        String pd_description = data.getString("pd_description");
                        tvTemp.setText(Html.fromHtml(pd_description));
                        is_in_wishlist = data.getString("is_in_wishlist");
                        JSONArray images = data.getJSONArray("images");
                        for(int i=0;i<images.length();i++)
                        {
                            String image = images.getString(i);
                            Log.e("..........",""+image);
                            // loop and add it to array or arraylist
                            ImagesArray.add(image);
                        }
                        init();
                        JSONArray comments = data.getJSONArray("comments");
                        for (int i=0;i<comments.length();i++) {
                            JSONObject obj = comments.getJSONObject(i);
                            String u_first_name = obj.getString("u_first_name");
                            String u_last_name = obj.getString("u_last_name");
                            String com_desc = obj.getString("com_desc");
                            final TextView tv111 = new TextView(getActivity());
                            tv111.setTypeface(custom_font);
                            tv111.setText(u_first_name+" "+u_last_name);
                            tv111.setTextSize(12);
                            tv111.setTextColor(getResources().getColor(R.color.colorPrimary));
                            final TextView tv222 = new TextView(getActivity());
                            tv222.setTypeface(custom_font);
                            tv222.setText(com_desc);
                            tv222.setTextSize(12);
                            tv222.setTextColor(getResources().getColor(R.color.productTitle));

                            UserComments.addView(tv111);
                            UserComments.addView(tv222);
                        }

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
        AppController.getInstance().addToRequestQueue(request, "product details");

    }




    private void init() {
        /*for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);
*/

        pager2.setAdapter(new SlidingImageProductAdapter(ImagesArray, getActivity()));




        indicator2.setViewPager(pager2);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator2.setRadius(4 * density);

        //NUM_PAGES =IMAGES.length;
        NUM_PAGES = ImagesArray.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                pager2.setCurrentItem(currentPage++, true);
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
        indicator2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
