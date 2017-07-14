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

public class RecyclerAdapter_latest_classified extends RecyclerView.Adapter<RecyclerAdapter_latest_classified.MyViewHolder2>{


    Context context;
    ArrayList<DemoPojo> list;

    public RecyclerAdapter_latest_classified(Context context, ArrayList<DemoPojo> list) {
        this.context = context;
        this.list = list;
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView image;
        Button b_Search;
        TextView tvDate, tvPrice, tvBrand;

        public MyViewHolder2(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image1);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvBrand = (TextView) view.findViewById(R.id.tvBrand);
            b_Search = (Button) view.findViewById(R.id.b_Search);
        }
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_latest_classified, parent, false);

        return new RecyclerAdapter_latest_classified.MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {
        DemoPojo pojo = list.get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "tt0142m.ttf");
        holder.tvDate.setTypeface(custom_font);
        holder.tvPrice.setTypeface(custom_font);
        holder.tvBrand.setTypeface(custom_font);
        holder.tvDate.setText(pojo.getDate());
        holder.tvPrice.setText(pojo.getProductPrice());
        holder.tvBrand.setText(pojo.getProductBrand());
        holder.b_Search.setText("   Add to cart   ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
