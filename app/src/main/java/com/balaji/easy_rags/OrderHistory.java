package com.balaji.easy_rags;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfHistory> listItems;
    private ImageView backButton;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getSupportActionBar().hide();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listItems = new ArrayList<>();
        adapter = new ContactAdapterHistory(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        session = new Session(getApplicationContext());

        orderHistory();

    }

    private void orderHistory() {

        String uri = getResources().getString(R.string.base_url)+"order_history.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String data = "Y";
                        try {
                            JSONObject JO = new JSONObject(s);

                            JSONArray JA = JO.getJSONArray("res");
                            JSONArray jaOrder = new JSONArray();
                            for(int i=0; i<JA.length(); i++){
                                JSONObject JO1 = JA.getJSONObject(i);
                                FetchedListOfHistory flp = new FetchedListOfHistory(JO1.getString("order_id"),
                                        JO1.getString("id"),
                                        JO1.getString("total"),
                                        JO1.getString("order_date"),
                                        JO1.getString("order_status"));
                                jaOrder=JO1.getJSONArray("order");
                                listItems.add(flp);
                                Log.d("LLLLLLL",jaOrder.get(0).toString());

                            for(int j=0;j<1;j++){
                                JSONObject jsonObj=jaOrder.getJSONObject(i);
                                flp.setImage(jsonObj.getString("Image"));
                                Log.d("LLLLLLL",jaOrder.get(0).toString());
                            }
                            }

                            Log.d("LLLLLLL",jaOrder.get(0).toString());

                            adapter = new ContactAdapterHistory(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            //progressDialog.dismiss();
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
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile_no", session.prefs.getString("Mbl", null));
            return params;
        }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
