package com.jarrebbnnee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by vi6 on 14-Mar-17.
 */

public class FragmentCart extends Fragment {

    ListView list_cart;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    cartListAdapter listAdapter;
    TextView tv1, tv2, tv4, tv3, tv5, tv6, tv7;
    Button btn_checkout;
    LinearLayout removeFromCart, moveWishlist;
    SaveSharedPrefrence prefrence;
    String ci_id,product_qty,u_id;
    URLCollection collection= new URLCollection();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cart, container, false);
        prefrence = new SaveSharedPrefrence();
        u_id = prefrence.getUserID(getActivity());
        list_cart = (ListView) view.findViewById(R.id.list_cart);
        moveWishlist = (LinearLayout) view.findViewById(R.id.move_to_wishlist);
        removeFromCart = (LinearLayout) view.findViewById(R.id.remove_from_cart);
        listAdapter = new cartListAdapter(list, getActivity());
        list_cart.setAdapter(listAdapter);

        prepareData();

        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
        list_cart.addFooterView(footerView);
        tv1 = (TextView) footerView.findViewById(R.id.tv_cart_1);
        tv2 = (TextView) footerView.findViewById(R.id.tv_cart_2);
        tv3 = (TextView) footerView.findViewById(R.id.tv_cart_3);
        tv4 = (TextView) footerView.findViewById(R.id.tv_cart_4);
        tv5 = (TextView) footerView.findViewById(R.id.tv_cart_5);
        tv6 = (TextView) footerView.findViewById(R.id.tv_cart_6);
        tv7 = (TextView) footerView.findViewById(R.id.tv_cart_7);
        btn_checkout = (Button) footerView.findViewById(R.id.btn_cart_checkout);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "tt0140m.ttf");
        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        tv3.setTypeface(custom_font);
        tv4.setTypeface(custom_font);
        tv5.setTypeface(custom_font);
        tv6.setTypeface(custom_font);
        tv7.setTypeface(custom_font);
        btn_checkout.setTypeface(custom_font);


        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCheckout fragment = new FragmentCheckout();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }


    public void prepareData() {
        String url = collection.cartListing+"&u_id="+u_id;
        Log.e("url", url);
        Log.e("list", "size" + list.size());

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        pDialog.dismiss();
                        JSONObject data = object.getJSONObject("data");
                        String cart_id = data.getString("cart_id");
                        String cart_key = data.getString("cart_key");
                        String cart_sub_total = data.getString("cart_sub_total");
                        String cart_discount = data.getString("cart_discount");
                        String cart_coupon_code = data.getString("cart_coupon_code");
                        String cart_grand_total = data.getString("cart_grand_total");
                        tv3.setText("$" + cart_sub_total);
                        tv7.setText("$" + cart_grand_total);
                        JSONArray cart_items = data.getJSONArray("cart_items");
                        if (cart_items.length()==0) {
                            list_cart.setVisibility(View.GONE);
                            Log.e("list", "visiblity gone");
                        }
                        for (int i = 0; i < cart_items.length(); i++) {

                            JSONObject obj = cart_items.getJSONObject(i);
                            String ci_id = obj.getString("ci_id");
                            String ci_cart_id = obj.getString("ci_cart_id");
                            String ci_product_id = obj.getString("ci_product_id");
                            String ci_price = obj.getString("ci_price");
                            String ci_product_qty = obj.getString("ci_product_qty");
                            String ci_price_subtotal = obj.getString("ci_price_subtotal");
                            String product_name = obj.getString("product_name");
                            String images = obj.getString("images");
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("ci_id", ci_id);
                            map.put("ci_cart_id", ci_cart_id);
                            map.put("ci_product_id", ci_product_id);
                            map.put("ci_price", ci_price);
                            map.put("ci_product_qty", ci_product_qty);
                            map.put("ci_price_subtotal", ci_price_subtotal);
                            map.put("product_name", product_name);
                            map.put("images", images);
                            list.add(map);
                            listAdapter.notifyDataSetChanged();

                        }

                    } else {
                        pDialog.dismiss();
                        String message = object.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        list_cart.setVisibility(View.GONE);
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
        AppController.getInstance().addToRequestQueue(sr, "cart listing");

    }

    public class cartListAdapter extends BaseAdapter {

        ArrayList<HashMap<String, String>> list;
        Context context;
        HashMap<String, String> map;
        SaveSharedPrefrence prefrence;
        Fragment fragment;

        public cartListAdapter(ArrayList<HashMap<String, String>> list, Context context) {
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
            final TextView tv_name, tv_price, tv1, tv2, tv_remove, tv_move;
            ImageView imageView,iv_minus, iv_plus;
            LinearLayout removeFromCart, moveWishlist;
            prefrence = new SaveSharedPrefrence();
            map = list.get(position);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.single_cart_item, null);
            }
            tv_name = (TextView) view.findViewById(R.id.tv_cart_name);
            imageView = (ImageView) view.findViewById(R.id.iv_wishlist);
            tv_price = (TextView) view.findViewById(R.id.tv_cart_price);
            iv_plus = (ImageView) view.findViewById(R.id.iv_plus);
            iv_minus = (ImageView) view.findViewById(R.id.iv_minus);
            moveWishlist = (LinearLayout) view.findViewById(R.id.move_to_wishlist);
            removeFromCart = (LinearLayout) view.findViewById(R.id.remove_from_cart);
            removeFromCart.setTag(map.get("ci_id"));
            iv_plus.setTag(map.get("ci_id"));
            iv_minus.setTag(map.get("ci_id"));

            tv1 = (TextView) view.findViewById(R.id.tv_cart1);
            tv2 = (TextView) view.findViewById(R.id.tv_cart2);
            tv_remove = (TextView) view.findViewById(R.id.tv_cart_remove);
            iv_minus = (ImageView) view.findViewById(R.id.iv_minus);
            iv_plus = (ImageView) view.findViewById(R.id.iv_plus);
            tv_move = (TextView) view.findViewById(R.id.tv_cart_move);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "tt0140m.ttf");
            tv_name.setTypeface(custom_font);
            tv_price.setTypeface(custom_font);
            tv1.setTypeface(custom_font);
            tv2.setTypeface(custom_font);
            tv_remove.setTypeface(custom_font);
            tv_move.setTypeface(custom_font);

            tv_name.setText(map.get("product_name"));
            tv1.setText(map.get("ci_product_qty"));
            tv_price.setText("$" + map.get("ci_price"));
            Picasso.with(context).load(map.get("images")).into(imageView);

            iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ci_id1 = v.getTag().toString();

                    String currentProduct_qty = tv1.getText().toString();
                    int current = Integer.parseInt(currentProduct_qty);
                    Log.e("current", ""+current);
                    current = current + 1;
                    product_qty = String.valueOf(current);
                    CallUpdateQuantity(ci_id1,product_qty);
                }
            });

            iv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ci_id1 = v.getTag().toString();

                    String currentProduct_qty = tv1.getText().toString();
                    int current = Integer.parseInt(currentProduct_qty);
                    Log.e("current", ""+current);
                    current = current - 1;
                    if (current > 0) {
                        product_qty = String.valueOf(current);
                        CallUpdateQuantity(ci_id1, product_qty);
                    } else {
                        Toast.makeText(getActivity(),"Cannot devcrease quantity below 1", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            removeFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ci_id = v.getTag().toString();

                    showSortDialog();
                }
            });

            return view;
        }

        public void showSortDialog() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setMessage("Are you sure you want to remove this product from cart?");
            dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //do something with edt.getText().toString();

                    Log.e("remove", ci_id);


                    CallRemoveFromCart(ci_id);
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


        private void CallRemoveFromCart(String ci_id) {
            if (list.size()>0) {
                list.clear();
                Log.e("list","cleared");
            }

            String url = collection.removeFromCart + "&u_id=" + u_id + "&ci_id=" + ci_id;
            Log.e("url", url);
            final ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading...");
            pDialog.show();
            StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        pDialog.dismiss();
                        JSONObject object = new JSONObject(response);
                        String status = object.getString("status");
                        if (status.equals("0")) {
                            String message = object.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
            AppController.getInstance().addToRequestQueue(sr, "remove from cart");
        }



    }

    private void CallUpdateQuantity(String ci_id, String pro) {
        if (list.size()>0) {
            list.clear();
            Log.e("list","cleared");
        }
        String url=collection.updateQuantity+"&u_id="+u_id+"&ci_id="+ci_id+"&product_qty="+pro;
        Log.e("url", url);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        prepareData();
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
        AppController.getInstance().addToRequestQueue(sr, "update quantity");
    }
}
