package com.balaji.easy_rags;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapterHistory extends RecyclerView.Adapter<ContactAdapterHistory.ViewHolder> {


    private List<FetchedListOfHistory> listItems;
    private Context context;
    public Bundle bundle;



    public ContactAdapterHistory(List<FetchedListOfHistory> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }



//    @NonNull
//    @Override
//    public ContactAdapterBanner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_banner_image, parent, false);
//
//        context = parent.getContext();
//        return new ViewHolder(v);
//
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_history, parent, false);

        bundle=new Bundle();
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ContactAdapterHistory.ViewHolder holder, int position) {

        final FetchedListOfHistory listItem = listItems.get(position);

        holder.order_id.setText("Order Id: "+listItem.getOrder_id());
        holder.order_total.setText("Total Bill: Rs. "+listItem.getOrder_total());

        Picasso.with(context).load(listItem.getImage()).into(holder.imageViewProduct);
        Toast.makeText(context, listItem.getImage(), Toast.LENGTH_SHORT).show();
        if(listItem.getOrder_status().equals("1")){
            holder.order_date.setTextColor(R.color.colorPrimaryDark);
            holder.order_date.setText("Your order is placed on "+listItem.getOrder_date());
        }else if(listItem.getOrder_status().equals("2")){
            holder.order_date.setTextColor(R.color.colorPrimaryDark);
            holder.order_date.setText("Your order is dispatched on "+listItem.getOrder_date());
        }else if(listItem.getOrder_status().equals("3")){
            holder.order_date.setText("Your order is delivered on "+listItem.getOrder_date());
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OrderDetailsActivity.class);
                bundle.putString("orderId",listItem.getOrder_id());
                bundle.putString("image",listItem.getImage());
                bundle.putString("orderStatus",listItem.getOrder_status());
                bundle.putString("orderTotal",listItem.getOrder_total());
                bundle.putString("orderDate",listItem.getOrder_date());
                bundle.putString("invoiceId",listItem.getInvoice_id());

                intent.putExtras(bundle);

                context.startActivity(intent);
                /*Intent i = new Intent(context, InvoiceActivity.class);
                i.putExtra("order_id", listItem.getInvoice_id());
                context.startActivity(i);*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView order_id;
        public TextView order_total;
        public TextView order_date;
        public LinearLayout layout;
        public ImageView imageViewProduct;

        public ViewHolder(View itemView) {
            super(itemView);

            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_total = (TextView) itemView.findViewById(R.id.order_total);
            order_date = (TextView) itemView.findViewById(R.id.order_date);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            imageViewProduct=itemView.findViewById(R.id.imageProduct);
        }
    }

}
