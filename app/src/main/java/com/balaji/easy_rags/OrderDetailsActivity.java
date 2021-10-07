package com.balaji.easy_rags;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView txtOrderId;
    TextView txtTotal;
    TextView txtDate;
    ImageView imageView;
    Button buttonInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getIds();

        final Bundle bundle=getIntent().getExtras();
        txtOrderId.setText("Order Id: "+bundle.getString("orderId"));
        txtTotal.setText("Total: "+bundle.getString("orderTotal"));
        txtDate.setText("Order Date: "+bundle.getString("orderDate"));
        Picasso.with(this).load(bundle.getString("image")).into(imageView);
        buttonInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvoiceActivity.class);
                i.putExtra("order_id", bundle.getString("orderId"));
                startActivity(i);
            }
        });
    }

    private void getIds() {
        txtOrderId=findViewById(R.id.order_id);
        txtTotal=findViewById(R.id.order_total);
        txtDate=findViewById(R.id.order_date);
        imageView=findViewById(R.id.imageProduct);
        buttonInvoice=findViewById(R.id.buttonInvoice);
    }
}