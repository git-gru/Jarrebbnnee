package com.jarrebbnnee;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by vi6 on 21-Mar-17.
 */

public class FragmentContactUs extends Fragment{

    TextView tv1,tv2,tv3,tv4;
    EditText et1,et2,et3,et4,et5;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_contact_us, container, false);
        tv1 = (TextView) view.findViewById(R.id.tvC1);
        tv2 = (TextView) view.findViewById(R.id.tvC2);
        tv3 = (TextView) view.findViewById(R.id.tvC3);
        tv4 = (TextView) view.findViewById(R.id.tvC4);
        et1 = (EditText) view.findViewById(R.id.etC1);
        et2 = (EditText) view.findViewById(R.id.etC2);
        et3 = (EditText) view.findViewById(R.id.etC3);
        et4 = (EditText) view.findViewById(R.id.etC4);
        et5 = (EditText) view.findViewById(R.id.etC5);
        btn = (Button) view.findViewById(R.id.btn_send);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0140m.ttf");

        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);
        tv3.setTypeface(custom_font);
        tv4.setTypeface(custom_font);
        et1.setTypeface(custom_font);
        et2.setTypeface(custom_font);
        et3.setTypeface(custom_font);
        et4.setTypeface(custom_font);
        et5.setTypeface(custom_font);
        btn.setTypeface(custom_font);



        return view;
    }
}
