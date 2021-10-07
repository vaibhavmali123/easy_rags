package com.balaji.easy_rags;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmployeePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfOrderForEmployee> listItems;
    private ProgressDialog progressDialog;

    private Session session;
    private DrawerLayout drawer;
    private TextView userMobileNumber;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new Session(getApplicationContext());

        RelativeLayout cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.INVISIBLE);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        View nView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        Menu menuNave = navigationView.getMenu();
        MenuItem logout = menuNave.findItem(R.id.logout);
        MenuItem login = menuNave.findItem(R.id.login);
        MenuItem account = menuNave.findItem(R.id.account);
        MenuItem orders = menuNave.findItem(R.id.orders);

        userMobileNumber = (TextView) nView.findViewById(R.id.userMobileNumber);
        userName = (TextView) nView.findViewById(R.id.userName);

        if (session.loggedin()) {

            logout.setVisible(true);
            login.setVisible(false);

            userMobileNumber.setText(session.prefs.getString("Mbl", null));
            userName.setText(session.prefs.getString("Unm", null));

        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listItems = new ArrayList<>();

        progressDialog = new ProgressDialog(EmployeePage.this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        new A().execute();

    }


    class A extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            loadMealOffers();
            return null;
        }
    }

    private void loadMealOffers() {

        String uri = getResources().getString(R.string.base_url)+"orders_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String data = "Y";
                        try {
                            JSONObject JO = new JSONObject(s);

                            JSONArray JA = JO.getJSONArray("res");

                            if(JA.length()>0){
                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    FetchedListOfOrderForEmployee flp = new FetchedListOfOrderForEmployee(JO1.getString("id"),
                                            JO1.getString("order_date"),
                                            JO1.getString("name")+"("+
                                                    JO1.getString("phone")+")",
                                            JO1.getString("phone"),
                                            JO1.getString("location"),
                                            JO1.getString("loc_lat"),
                                            JO1.getString("loc_long"),
                                            JO1.getString("total"));
                                    listItems.add(flp);
                                }

                                adapter = new ContactAdapterOrderListForEmployee(listItems,getApplicationContext());
                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Toast.makeText(getContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){};
        queue.add(stringRequest);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            drawer.closeDrawer(Gravity.LEFT);
        }
//        else if (id == R.id.fruit) {
//            Intent i = new Intent(getApplicationContext(), Fruits.class);
//            startActivity(i);
//        } else if (id == R.id.vegetables) {
//            Intent i = new Intent(getApplicationContext(), Vegetables.class);
//            startActivity(i);
//        } else if (id == R.id.dryfruit) {
//            Intent i = new Intent(getApplicationContext(), DryFruits.class);
//            startActivity(i);
//        } else if (id == R.id.cutsandsprouts) {
//            Intent i = new Intent(getApplicationContext(), CutsandSprouts.class);
//            startActivity(i);
//        }
        else if (id == R.id.account) {
            Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);
        } else if (id == R.id.orders) {
            Intent i = new Intent(getApplicationContext(), OrderHistory.class);
            startActivity(i);
        } else if (id == R.id.login) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            logOut();
        } else if (id == R.id.aboutus) {
            Intent i = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }



    public void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                if (session.loggedin()) {
                    session.setMobile("");
                    session.setUserRole("");
                    session.setLoggedin(false);
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You are not logged in!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}