package com.jarrebbnnee;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vi6 on 08-Mar-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    Context context;
    ArrayList<DemoPojo> list;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        Button bSearch;
        TextView tv_name, tv_price;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image1);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            bSearch = (Button) view.findViewById(R.id.bSearch);

        }
    }

    public RecyclerAdapter(Context context, ArrayList<DemoPojo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_latest_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DemoPojo pojo = list.get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0142m.ttf");
        holder.tv_name.setTypeface(custom_font);
        holder.tv_price.setTypeface(custom_font);
        holder.tv_name.setText(pojo.getProductName());
        holder.tv_price.setText(pojo.getProductPrice());
        holder.bSearch.setText("   Add to cart   ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
