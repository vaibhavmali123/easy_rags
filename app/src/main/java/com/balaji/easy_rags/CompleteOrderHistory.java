package com.balaji.easy_rags;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CompleteOrderHistory extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfCompleteOrder> listItems;

    private TextView bill_total;
    private TextView textView_order_id;
    private ImageView backButton;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order_history);
        getSupportActionBar().hide();

        bill_total = (TextView) findViewById(R.id.bill_total);
        textView_order_id = (TextView) findViewById(R.id.order_id);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listItems = new ArrayList<>();
        adapter = new ContactAdapterCompleteOrder(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
        backButton = (ImageView) findViewById(R.id.backButton);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        order_id = b.getString("order_id");

        loadRecyclerViewData();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void loadRecyclerViewData() {

        String uri = getResources().getString(R.string.base_url)+"order_list.php";
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
                            JSONObject JO2 = JA.getJSONObject(0);
                            String order = JO2.getString("cart");
                            String total = JO2.getString("total");
                            bill_total.setText(total+" Rs");
                            textView_order_id.setText("Order Id:" + JO2.getString("id"));

                            JSONArray JA1 = new JSONArray(order);


                            //JSONArray JA1 = JO2.getJSONArray("cart");

                            for(int i=0; i<JA1.length(); i++){
                                JSONObject JO1 = JA1.getJSONObject(i);
                                FetchedListOfCompleteOrder flp = new FetchedListOfCompleteOrder(JO1.getString("name"),
                                        JO1.getString("SaleType"),
                                        JO1.getString("qty"),
                                        JO1.getString("price"),
                                        JO1.getString("total"));
                                listItems.add(flp);
                            }

                            adapter = new ContactAdapterCompleteOrder(listItems,getApplicationContext());
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
            params.put("order_no", order_id);
            return params;
        }
        };
        queue.add(stringRequest);

    }


}
