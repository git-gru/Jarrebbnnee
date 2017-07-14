package com.jarrebbnnee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by vi6 on 21-Mar-17.
 */

public class FragmentAccount extends Fragment{

    ListView list_account;
    ImageView profile_image;
    ArrayList<DemoPojo> list_pojo= new ArrayList<>();
    ArrayList<String> lang=new ArrayList<>();
    ListPopupWindow listPopupWindow;
    ArrayAdapter<String> arrad;
    AccountListAdapter accountListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_my_account, container, false);

        list_account = (ListView) view.findViewById(R.id.list_account);
        profile_image = (ImageView) view.findViewById(R.id.profile_image);
    //    accountListAdapter = new AccountListAdapter(imageId, getActivity());
        accountListAdapter = new AccountListAdapter(list_pojo, getActivity());
        list_account.setAdapter(accountListAdapter);

        lang.add("English");
        lang.add("Arabic");

        arrad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lang);

        listPopupWindow = new ListPopupWindow(
                getActivity());
        listPopupWindow.setAdapter(arrad);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        list_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FragmentMyOrders contactUs= new FragmentMyOrders();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, contactUs);
                    ft.commit();
                }
                if (position == 2) {
                 FragmentContactUs contactUs= new FragmentContactUs();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, contactUs);
                    ft.commit();
                }
                if (position == 4) {
                    FragmentEditProfile editProfile= new FragmentEditProfile();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, editProfile);
                    ft.commit();
                }
                if (position == 6) {
                    showSortDialog();
                }
            }
        });

        initData();

        return view;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(getActivity(), HomeActivity.class);
        startActivity(refresh);
        getActivity().finish();
    }

    private void initData() {
        DemoPojo pojo1= new DemoPojo("My Orders", R.drawable.icon_order);
        DemoPojo pojo3= new DemoPojo("Share with friends", R.drawable.icon_share);
        DemoPojo pojo4= new DemoPojo("Contact us", R.drawable.icon_contact);
        DemoPojo pojo5= new DemoPojo("Change password", R.drawable.icon_password);
        DemoPojo pojo6= new DemoPojo("Edit profile", R.drawable.icon_account_black);
        DemoPojo pojo7= new DemoPojo("Logout", R.drawable.icon_logout);
        DemoPojo pojo8= new DemoPojo("Change Language", R.drawable.icon_logout);

        list_pojo.add(pojo1);
        list_pojo.add(pojo3);
        list_pojo.add(pojo4);
        list_pojo.add(pojo5);
        list_pojo.add(pojo6);
        list_pojo.add(pojo7);
        list_pojo.add(pojo8);

        accountListAdapter.notifyDataSetChanged();
    }



    public void showSortDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setAdapter(arrad, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = lang.get(which);
                Log.e("language",s);
                if (s.equals("English")) {
                    setLocale("en");
                //    URLCollection.lang = "english";
                  LanguageGetSet.lang = "english";
                  LanguageGetSet.setLang("english");
                }else if (s.equals("Arabic")) {
                    setLocale("ar");
             //       URLCollection.lang = "arabic";
                    LanguageGetSet.lang = "arabic";
                   LanguageGetSet.setLang("arabic");
                    //URLCollection.lang = "arabic";
                   // URLCollection.setLang("arabic");
                }
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle("Change Language");
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
