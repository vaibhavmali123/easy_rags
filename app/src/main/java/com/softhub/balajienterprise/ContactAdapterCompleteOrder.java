package com.softhub.balajienterprise;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapterCompleteOrder extends RecyclerView.Adapter<ContactAdapterCompleteOrder.ViewHolder> {

    private List<FetchedListOfCompleteOrder> listItems;
    private Context context;

    String rupee;
    double total;



    public ContactAdapterCompleteOrder(List<FetchedListOfCompleteOrder> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_complete_history, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfCompleteOrder listItem = listItems.get(position);

        holder.name.setText(listItem.getItemName());
        holder.price.setText(listItem.getItemPrice()+"/"+listItem.getItemSType());
        holder.quantity.setText(listItem.getItemQuantity());
        holder.total.setText(listItem.getItemTotal());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView price;
        public TextView quantity;
        public TextView total;



        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            total = (TextView) itemView.findViewById(R.id.total);

            //rupee = res.getString(R.string.rupees_symbol);

        }
    }
}

