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
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by vi6 on 21-Mar-17.
 */

public class FragmentCheckout extends Fragment{

    TextView tt1,text_fname,text_lname, text_email, text_mobile, text_country, tt2, tt3;
    EditText et_state,et_city, et_pincode, et_address1, et_address2;
    Button btn_payment;
    RadioButton rb1, rb2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_checkout, container, false);

        tt1 = (TextView) view.findViewById(R.id.tt1);
        tt2 = (TextView) view.findViewById(R.id.tt2);
        tt3 = (TextView) view.findViewById(R.id.tt3);
        text_fname = (TextView) view.findViewById(R.id.text_fname);
        text_lname = (TextView) view.findViewById(R.id.text_lname);
        text_country = (TextView) view.findViewById(R.id.text_country);
        text_email = (TextView) view.findViewById(R.id.text_email);
        text_mobile = (TextView) view.findViewById(R.id.text_mobile);
        btn_payment = (Button) view.findViewById(R.id.btn_payment);
        et_state = (EditText) view.findViewById(R.id.et_State);
        et_city = (EditText) view.findViewById(R.id.et_city);
        et_pincode = (EditText) view.findViewById(R.id.et_pincode);
        et_address1 = (EditText) view.findViewById(R.id.et_address1);
        et_address2 = (EditText) view.findViewById(R.id.et_address2);

        rb1 = (RadioButton) view.findViewById(R.id.rb1);
        rb2 = (RadioButton) view.findViewById(R.id.rb2);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "tt0142m.ttf");
        tt1.setTypeface(custom_font);
        tt2.setTypeface(custom_font);
        tt3.setTypeface(custom_font);
        text_fname.setTypeface(custom_font);
        text_lname.setTypeface(custom_font);
        text_country.setTypeface(custom_font);
        text_email.setTypeface(custom_font);
        text_mobile.setTypeface(custom_font);
        et_state.setTypeface(custom_font);
        et_city.setTypeface(custom_font);
        et_pincode.setTypeface(custom_font);
        et_address1.setTypeface(custom_font);
        et_address2.setTypeface(custom_font);



        return view;
    }
}
