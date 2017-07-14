package com.jarrebbnnee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 09-Mar-17.
 */

public class fragmentWishlist extends Fragment{

    ListView wishlist;
    ArrayList<HashMap<String,String>> list;
    WishlistAdapter adapter;
    String u_id,product_id;
    SaveSharedPrefrence prefrence;
    URLCollection collection= new URLCollection();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wishlist, null);
        list= new ArrayList<HashMap<String, String>>();
        prefrence= new SaveSharedPrefrence();
        u_id = prefrence.getUserID(getActivity());
        wishlist = (ListView) view.findViewById(R.id.wishlist);
        adapter = new WishlistAdapter(list, getActivity());
        wishlist.setAdapter(adapter);
        prepareData();
        return view;
    }

    private void prepareData() {
        String url = collection.getWishlist+"&u_id="+u_id;
        Log.e("url", url);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest sr= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        JSONArray data = object.getJSONArray("data");

                        for (int i=0;i<data.length();i++) {
                            JSONObject obj = data.getJSONObject(i);
                            String wl_id = obj.getString("wl_id");
                            String wl_product_id = obj.getString("wl_product_id");
                            String product_name = obj.getString("product_name");
                            String price = obj.getString("price");
                            String images = obj.getString("images");


                            HashMap<String,String> map= new HashMap<>();
                            map.put("wl_id",wl_id);
                            map.put("wl_product_id",wl_product_id);
                            map.put("product_name",product_name);
                            map.put("price",price);
                            map.put("images",images);
                            list.add(map);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        String message = object.getString("message");
                            wishlist.setVisibility(View.GONE);
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
        AppController.getInstance().addToRequestQueue(sr, "view wishlist");
    }

    public class WishlistAdapter extends BaseAdapter {

        ArrayList<HashMap<String,String>> list;
        Context context;
        HashMap<String,String> map;

        public WishlistAdapter(ArrayList<HashMap<String,String>> list, Context context) {
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
            map = list.get(position);
            TextView tv1,tv2;
            ImageView iv_wishlist, iv_share, iv_add_cart, iv_delete;
            View view = convertView;
            if (view==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.single_fragment_wishlist, null);
                Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0140m.ttf");
                tv1 = (TextView) view.findViewById(R.id.tv_wishlist_name);
                tv2 = (TextView) view.findViewById(R.id.tv_wishlist_price);
                iv_add_cart = (ImageView) view.findViewById(R.id.iv_wishlist_add_cart);
                iv_share = (ImageView) view.findViewById(R.id.iv_wishlist_share);
                iv_delete = (ImageView) view.findViewById(R.id.iv_wishlist_delete);
                iv_wishlist = (ImageView) view.findViewById(R.id.iv_wishlist_main);
                tv1.setTypeface(custom_font);
                tv2.setTypeface(custom_font);
                tv1.setText(map.get("product_name"));
                tv2.setText("$"+map.get("price"));
                product_id = map.get("wl_product_id");
                Picasso.with(context).load(map.get("images")).into(iv_wishlist);

                iv_add_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CallAddToCart(product_id);
                    }
                });

                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSortDialog();
                    }
                });
            }
            return view;
        }
    }

    private void CallDeleteFromWishlist(String product_id) {
        if (list.size()>0) {
            list.clear();
            Log.e("list","cleared");
        }
        String url=collection.deleteFromWishlist+"&u_id="+u_id+"&product_id="+product_id;
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if (status.equals("0")) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        prepareData();
                    } else {
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
        AppController.getInstance().addToRequestQueue(request, "remove from wishlist");
    }

    private void CallAddToCart(String product_id) {
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


    public void showSortDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("Are you sure you want to remove this product from wishlist?");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                CallDeleteFromWishlist(product_id);

            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
