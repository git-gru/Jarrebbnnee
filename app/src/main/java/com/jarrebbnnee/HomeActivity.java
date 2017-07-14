package com.jarrebbnnee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tv_nav_1, tv_nav_2,tv_,tvll1,tvll2,tvll3;
    LinearLayout footer_home, footer_categories, footer_cart, footer_account, footer_search, ll_my_account,ll_my_wishlist,ll_contact_us;
    public static final String FacebookData = "FacebookData";
    public static final String ServerData = "ServerData", LoginManage = "LoginManager";
    SharedPreferences fbPref, serverPref, loginPref;
    SharedPreferences.Editor fbEditor;
    SharedPreferences.Editor loginEditor;
    SharedPreferences.Editor serverEditor;
    String filter_categoties;
    ArrayList<POJO_Drawer> list_drawer;
    ArrayList<HashMap<String,String>> list_cats;
    ListView left_drawer;
    CustomDrawerAdapter drawerAdapter;
    DrawerLayout drawer;
    SaveSharedPrefrence prefrence;
    String naame;
    Database database;
    boolean addData = true;
    URLCollection collection= new URLCollection();

    Menu m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment1 fragment= new Fragment1();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
        prefrence= new SaveSharedPrefrence();
        database = new Database(HomeActivity.this);
        naame = prefrence.getUserFName(HomeActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "" + "</font>")));
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        footer_home = (LinearLayout) findViewById(R.id.footer_home);
        footer_categories = (LinearLayout) findViewById(R.id.footer_categories);
        footer_cart = (LinearLayout) findViewById(R.id.footer_cart);
        footer_account = (LinearLayout) findViewById(R.id.footer_account);
        footer_search = (LinearLayout) findViewById(R.id.footer_search);
        list_drawer=new ArrayList<POJO_Drawer>();

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        serverPref = getSharedPreferences(ServerData, Context.MODE_PRIVATE);
        fbPref = getSharedPreferences(FacebookData, Context.MODE_PRIVATE);
        loginPref = getSharedPreferences(LoginManage, Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();

        fbEditor = fbPref.edit();
        serverEditor = serverPref.edit();

        LoadData();

       /* loadPreference.loadSharedPrefs(HomeActivity.this, Config.SHARED_PREF);
        loadPreference.loadSharedPrefs(HomeActivity.this, FacebookData);
        loadPreference.loadSharedPrefs(HomeActivity.this, ServerData);*/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tv_nav_1 = (TextView) navigationView.findViewById(R.id.tv_welcome);
        tv_nav_2 = (TextView) navigationView.findViewById(R.id.tv_logout);
        tv_nav_1.setText("Welcome, "+naame);
        tv_ = (TextView) navigationView.findViewById(R.id.tv_lll);

        left_drawer = (ListView) navigationView.findViewById(R.id.left_drawer);

        View footerView = ((LayoutInflater) HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_static_menu, null, false);
        left_drawer.addFooterView(footerView);

        ll_contact_us = (LinearLayout) footerView.findViewById(R.id.ll_contact_us);
        ll_my_account = (LinearLayout) footerView.findViewById(R.id.ll_my_account);
        ll_my_wishlist = (LinearLayout) footerView.findViewById(R.id.ll_my_wishlist);
        tvll1 = (TextView) footerView.findViewById(R.id.tvll1);
        tvll2 = (TextView) footerView.findViewById(R.id.tvll2);
        tvll3 = (TextView) footerView.findViewById(R.id.tvll3);


        drawerAdapter = new CustomDrawerAdapter(list_drawer, HomeActivity.this);

        left_drawer.setAdapter(drawerAdapter);

        //setListViewHeightBasedOnChildren(left_drawer);
      //  setListViewHeightBasedOnChildren(left_drawer_2);
        /*left_drawer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true; // Indicates that this has been handled by you and will not be forwarded further.
                }
                return false;
            }
        });*/
        ll_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Fragment f = new FragmentAccount();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });
        ll_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Fragment f = new FragmentContactUs();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });

        ll_my_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                Fragment f = new fragmentWishlist();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });


        left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POJO_Drawer pojo= list_drawer.get(position);
                Toast.makeText(HomeActivity.this,"Name "+pojo.getName()+"\nID "+pojo.getId(),Toast.LENGTH_SHORT).show();
                Fragment fragment = new FragmentProductListing();
                Bundle args= new Bundle();
                args.putString("filter_categories", pojo.getId());
                args.putString("category_id", "");
                args.putString("min_price","");
                args.putString("max_price","");
                args.putString("text", "");
                args.putString("pd_refer_id", "");
                fragment.setArguments(args);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
                drawer.closeDrawers();
            }
        });
        /* navigationView.setItemIconTintList(null);
        m= navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        MenuItem m1 = m.getItem(0);
        final SubMenu topChannelMenu = m.addSubMenu("Top Categories");
        final Drawable iconRes = getResources().getDrawable(R.drawable.icon_toys);
       */ StringRequest sr= new StringRequest(Request.Method.GET, collection.categoryListing, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object= new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("0")) {
                        JSONArray data = object.getJSONArray("data");
                        list_cats = database.viewLastSyncCategories();
                        if (list_cats.size()>0) {
                            addData = false;
                        }
                            for (int i = 0; i < data.length(); i++) {
                            JSONObject mObject = data.getJSONObject(i);
                            String pc_id = mObject.getString("pc_id");
                            String pc_name = mObject.getString("pc_name");
                           /* topChannelMenu.add(pc_name);
                            MenuItem mi2 = m.getItem(m.size()-1);
                            mi2.setTitle(mi2.getTitle());
                            mi2.setIcon(iconRes);
                            applyFontToMenuItem(mi2);*/
                            POJO_Drawer pojo= new POJO_Drawer(pc_name, pc_id);
                                if (addData) {
                                    database.add_cat_id_pair(pc_id, pc_name);
                                }


                            list_drawer.add(pojo);
                            drawerAdapter.notifyDataSetChanged();
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
        AppController.getInstance().addToRequestQueue(sr, "category list");
        /*View header = navigationView.getHeaderView(0);
        tv_nav_1 = (TextView) header.findViewById(R.id.tv_welcome);
        tv_nav_2 = (TextView) header.findViewById(R.id.tv_logout);*/

        tv_nav_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
            }
        });


        tv_nav_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Log.e("facebook", "You have been logged out");
                fbEditor.clear();
                fbEditor.commit();
                prefrence.DeletePrefrence(HomeActivity.this);
                Log.e("Logout", "----------------------------------------------------------------------------------");
                loginEditor.putString("isLoggedIn", "false");
                loginEditor.commit();
                loadPreference.loadSharedPrefs(HomeActivity.this, LoginManage);
                loadPreference.loadSharedPrefs(HomeActivity.this, FacebookData);
                drawer.closeDrawers();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "tt0140m.ttf");
        tv_nav_1.setTypeface(custom_font);
        tv_nav_2.setTypeface(custom_font);
        tv_.setTypeface(custom_font);
        tvll1.setTypeface(custom_font);
        tvll2.setTypeface(custom_font);
        tvll3.setTypeface(custom_font);


        footer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footer_home.setBackgroundResource(R.color.bottomMenuActive);
                footer_search.setBackgroundResource(R.color.bottomMenuInactive);
                footer_cart.setBackgroundResource(R.color.bottomMenuInactive);
                footer_categories.setBackgroundResource(R.color.bottomMenuInactive);
                footer_account.setBackgroundResource(R.color.bottomMenuInactive);

                Toast.makeText(getApplicationContext(), "Home Clicker", Toast.LENGTH_SHORT).show();
                Fragment f = new Fragment1();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });

        footer_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Categories", Toast.LENGTH_SHORT).show();
                footer_categories.setBackgroundResource(R.color.bottomMenuActive);
                footer_search.setBackgroundResource(R.color.bottomMenuInactive);
                footer_cart.setBackgroundResource(R.color.bottomMenuInactive);
                footer_home.setBackgroundResource(R.color.bottomMenuInactive);
                footer_account.setBackgroundResource(R.color.bottomMenuInactive);
                Fragment f = new CategoriesFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });

        footer_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cart", Toast.LENGTH_SHORT).show();

                footer_cart.setBackgroundResource(R.color.bottomMenuActive);
                footer_search.setBackgroundResource(R.color.bottomMenuInactive);
                footer_home.setBackgroundResource(R.color.bottomMenuInactive);
                footer_categories.setBackgroundResource(R.color.bottomMenuInactive);
                footer_account.setBackgroundResource(R.color.bottomMenuInactive);

                Fragment f = new FragmentCart();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });

        footer_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Accounts", Toast.LENGTH_SHORT).show();
                footer_account.setBackgroundResource(R.color.bottomMenuActive);
                footer_search.setBackgroundResource(R.color.bottomMenuInactive);
                footer_cart.setBackgroundResource(R.color.bottomMenuInactive);
                footer_categories.setBackgroundResource(R.color.bottomMenuInactive);
                footer_home.setBackgroundResource(R.color.bottomMenuInactive);

                FragmentAccount fragment= new FragmentAccount();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();
            }
        });

        footer_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                footer_search.setBackgroundResource(R.color.bottomMenuActive);
                footer_home.setBackgroundResource(R.color.bottomMenuInactive);
                footer_cart.setBackgroundResource(R.color.bottomMenuInactive);
                footer_categories.setBackgroundResource(R.color.bottomMenuInactive);
                footer_account.setBackgroundResource(R.color.bottomMenuInactive);
                Fragment f = new FragmentCart();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, f);
                ft.commit();
            }
        });

        //FontsOverride.setDefaultFont(HomeActivity.this, "MONOSPACE", "tt0140m.ttf");
    }

    private void LoadData() {
        String u_id = prefrence.getUserID(HomeActivity.this);
        String u_first_name = prefrence.getUserFName(HomeActivity.this);
        String u_last_name = prefrence.getUserLName(HomeActivity.this);
        String u_email = prefrence.getUserEmail(HomeActivity.this);
        String u_phone = prefrence.getUserPhone(HomeActivity.this);
        String u_country = prefrence.getUserCountry(HomeActivity.this);
        String device_token = prefrence.getDeviceToken(HomeActivity.this);
        String is_logged_in = prefrence.getisLoggedIn(HomeActivity.this);
        String sm_social_provider_id = prefrence.getSM_SOCIAL_PROVIDER_ID(HomeActivity.this);
        Log.e("###########","###########");
        Log.e("u_id",u_id);
        Log.e("u_first_name",u_first_name);
        Log.e("u_last_name",u_last_name);
        Log.e("u_phone",u_phone);
        Log.e("u_country",u_country);
        Log.e("device_token",device_token);
        Log.e("is_logged_in",is_logged_in);
        Log.e("sm_social_provider_id",sm_social_provider_id);
        Log.e("u_email",u_email);
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (id){
            case  R.id.nav_account:
                fragment = new FragmentAccount();
                break;
            case  R.id.nav_wishlist:
                fragment = new fragmentWishlist();
                break;
            case  R.id.nav_contact:
                break;
            default:
                fragment = new Fragment1();
                break;
        }
      /*  if (id == R.id.nav_bags) {
            // Handle the camera action
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+1);
            fragment.setArguments(args);
        } else if (id == R.id.nav_cell) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+2);
            fragment.setArguments(args);
        } else if (id == R.id.nav_computer) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+3);
            fragment.setArguments(args);
        } else if (id == R.id.nav_electronics) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+4);
            fragment.setArguments(args);
        } else if (id == R.id.nav_mobile) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+5);
            fragment.setArguments(args);
        } else if (id == R.id.nav_tablet) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+6);
            fragment.setArguments(args);
        } else if (id == R.id.nav_toy) {
            fragment = new FragmentProductListing();
            args.putStringArrayList("filter_categories", filter_categoties);
            args.putString("category_id", ""+7);
            fragment.setArguments(args);
        } else*/
   /*   if (id == R.id.nav_account) {
            fragment = new FragmentAccount();
        } else if (id == R.id.nav_wishlist) {
            fragment = new fragmentWishlist();
      } else if (id == R.id.nav_contact) {

      } else {
          fragment = new Fragment1();
      }*/
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "tt0140m.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}
