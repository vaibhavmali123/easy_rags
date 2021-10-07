package com.balaji.easy_rags;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactAdapterOrderListForEmployee extends RecyclerView.Adapter<ContactAdapterOrderListForEmployee.ViewHolder> {

    private List<FetchedListOfOrderForEmployee> listItems;
    private Context context;

    String rupee;
    double total;



    public ContactAdapterOrderListForEmployee(List<FetchedListOfOrderForEmployee> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_list_item, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfOrderForEmployee listItem = listItems.get(position);

        holder.order_id.setText("Order Id: "+listItem.getOrder_id());
        holder.order_date.setText("Order Date: "+listItem.getOrder_date());
        holder.order_customer.setText(listItem.getOrder_customer());
        holder.order_address.setText(listItem.getOrder_address());
        holder.order_amount.setText("Bill Amount: "+listItem.getOrder_amount());

        holder.order_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!listItem.getOrder_lat().equals("")){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:"
                            + listItem.getOrder_lat() + "," + listItem.getOrder_long()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Only if initiating from a Broadcast Receiver
                    String mapsPackageName = "com.google.android.apps.maps";
                    if (isPackageExisted(context, mapsPackageName)) {
                        i.setClassName(mapsPackageName, "com.google.android.maps.MapsActivity");
                        i.setPackage(mapsPackageName);
                    }else{
                        Toast.makeText(context, "Google Maps Not Found", Toast.LENGTH_SHORT).show();
                    }
                    context.startActivity(i);
                }else{
                    Toast.makeText(context, "Location Not Attached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.order_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+listItem.getOrder_mobile()));
                context.startActivity(intent);
            }
        });

    }

    private static boolean isPackageExisted(Context context, String targetPackage){
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView order_id;
        public TextView order_date;
        public TextView order_customer;
        public TextView order_address;
        public TextView order_amount;



        public ViewHolder(View itemView) {
            super(itemView);

            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_date = (TextView) itemView.findViewById(R.id.order_date);
            order_customer = (TextView) itemView.findViewById(R.id.order_customer);
            order_address = (TextView) itemView.findViewById(R.id.order_address);
            order_amount = (TextView) itemView.findViewById(R.id.order_amount);

            //rupee = res.getString(R.string.rupees_symbol);

        }
    }
}

