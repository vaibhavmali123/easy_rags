package com.softhub.balajienterprise;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.Random;

public class CartActivity extends AppCompatActivity {

    private CartDatabaseHelper db;
    public JSONObject JObject;
    public JSONArray jsonArray;
    public List cartItemList;
    private List<FetchedListOfCartItem> listItems;
    public List<Cart> cart;
    private List<FetchedListOfShipping> listOfShippings;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private double totalItemCart = 0;

    private TextView subtotal;
    private TextView grandtotal;
    private TextView selected_address;
    private Button select_address;
    private ImageView backButton;
    private TextView checkout;
    private TextView continue_shopping,shipping;

    private String address;
    private String mobile;
    private Session session;
    public Bundle b;
    int index = 0;
    int tip;

    private int oid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.setMessage("Please Wait...");


        //updateCartList();

        selected_address = (TextView) findViewById(R.id.selected_address);
        select_address = (Button) findViewById(R.id.select_address);
        checkout = (TextView) findViewById(R.id.checkout);
        continue_shopping = (TextView) findViewById(R.id.continue_shopping);
        backButton = (ImageView) findViewById(R.id.backButton);

        shipping = findViewById(R.id.shipping);
        listOfShippings = new ArrayList<>();

        session = new Session(getApplicationContext());
        mobile = session.prefs.getString("Mbl", null);
        loadRecyclerViewData();


        b = new Bundle();
        b = getIntent().getExtras();
        address = b.getString("address");

        Random rand = new Random();
        oid = rand.nextInt(9999) + 1000;

        if(address.equals("abc")){
            //Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
        }else{
            selected_address.setText(address);
            //Toast.makeText(getApplicationContext(), address+"K", Toast.LENGTH_SHORT).show();
        }



        //Log.d("CART", cartItemList.toString());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session.loggedin()){
                    Intent i = new Intent(getApplicationContext(), AddressSelect.class);
                    i.putExtra("flag", "cart");
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Login First or sign up",Toast.LENGTH_SHORT).show();
                }
            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session.loggedin()){
                    if(db.getCartItemCount()<1){
                        Toast.makeText(getApplicationContext(), "No Items in Cart",Toast.LENGTH_SHORT).show();
                    }else{
                        if(totalItemCart<100){
                            Toast.makeText(getApplicationContext(), "Minimum Order Rs: 100", Toast.LENGTH_SHORT).show();
                        }else{
                            if(address.equals("abc")){
                                if(session.loggedin()){
                                    Intent i = new Intent(getApplicationContext(), AddressSelect.class);
                                    i.putExtra("flag", "cart");
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Login First or sign up",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                addToCart();
                                //Toast.makeText(getApplicationContext(), address+"K", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please Login First or sign up",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void loadRecyclerViewData() {

        String uri = getResources().getString(R.string.base_url)+"shipping.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String data = "Y";
                        try {
//                            JSONObject JO = new JSONObject(s);
                            JSONArray JA = new JSONArray(s);
//                            String code = JO.getString("data_code");
                            if(JA.length()>0){

                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    FetchedListOfShipping flp = new FetchedListOfShipping(JO1.getString("id"),
                                            JO1.getString("shipping"));
                                    listOfShippings.add(flp);

                                }

                                shipping.setText(listOfShippings.get(index).getShipping());
                                tip = Integer.parseInt(String.valueOf(shipping.getText()));
                                Log.d("tip", String.valueOf(tip));
                                updateCartList();

                               /* adapter = new SizeListAdapter(listOfSizes,getApplicationContext());
                                recyclerView.setAdapter(adapter);*/
                            }else{

                                Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_SHORT).show();
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
                });
        queue.add(stringRequest);
    }

    public void updateCartList() {
        subtotal = (TextView) findViewById(R.id.subtotal);
        grandtotal = (TextView) findViewById(R.id.grandtotal);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listItems = new ArrayList<>();
        adapter = new ContactAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
        db = new CartDatabaseHelper(getApplicationContext());
        JObject = new JSONObject();
        jsonArray = new JSONArray();
        cartItemList = new ArrayList();

        cart = db.getAllCart();
        totalItemCart = 0;
        for(Cart c: cart){

            FetchedListOfCartItem flp = new FetchedListOfCartItem(c.getId(), c.getItem_id(),c.getItem_name(),c.getItem_image(),
                    c.getItem_saleType(),c.getItem_quantity(),c.getItem_price(), c.getItem_total());
            listItems.add(flp);
            totalItemCart = totalItemCart + (Double.parseDouble(c.getItem_total()));
            try {
                JObject.put("cartId", c.getId());
                JObject.put("id",c.getItem_id());
                JObject.put("name", c.getItem_name());
                JObject.put("Image", c.getItem_image());
                JObject.put("SaleType", c.getItem_saleType());
                JObject.put("qty", c.getItem_quantity());
                JObject.put("price", c.getItem_price());
                JObject.put("total", c.getItem_total());
                jsonArray.put(JObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cartItemList.add(String.valueOf(JObject));
            Log.d("DATA", cartItemList.toString());
        }

        subtotal.setText("Rs. "+String.valueOf(totalItemCart));
        Log.d("tip1", String.valueOf(tip));
        grandtotal.setText("Rs. "+String.valueOf(totalItemCart+tip));
        Log.d("total", String.valueOf(grandtotal));

        setItemToCart();

    }

    private void addToCart() {
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"add_to_cart.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    String code;
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            code = JO.getString("code");
                            if(code.equals("200")){
                                progressDialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                db.deleteCart(new Cart());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", mobile);
            params.put("order", cartItemList.toString());
            params.put("total", String.valueOf(totalItemCart));
            params.put("address", address);
            params.put("order_id", mobile+String.valueOf(oid));

            return params;
        }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void setItemToCart() {

        try {

            JSONArray JA = jsonArray;

            for(int i=0; i<JA.length(); i++){
                JSONObject JO1 = JA.getJSONObject(i);
                FetchedListOfCartItem flp = new FetchedListOfCartItem(JO1.getInt("cartId"),
                        JO1.getString("itemId"),
                        JO1.getString("itemName"),
                        JO1.getString("itemImage"),
                        JO1.getString("itemSType"),
                        JO1.getString("itemQuantity"),
                        JO1.getString("itemPrice"),
                        JO1.getString("itemTotal"));
                listItems.add(flp);
            }
            adapter = new ContactAdapter(listItems,getApplicationContext());
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
