package com.balaji.easy_rags;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapterDryCutsSprouts extends RecyclerView.Adapter<ContactAdapterDryCutsSprouts.ViewHolder> {

    private List<FetchedListOfItem> listItems;
    private Context context;

    String rupee;
    double total;



    public ContactAdapterDryCutsSprouts(List<FetchedListOfItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfItem listItem = listItems.get(position);

        holder.itemName.setText(listItem.getItemName());
        holder.itemPrice.setText("Rs "+listItem.getItemPrice()+"/"+listItem.getItemSType());
        Picasso.with(context).load(listItem.getItemImage()).into(holder.itemImage);
        total = Integer.valueOf(listItem.getItemPrice())*Integer.valueOf(holder.itemQuantity.getText().toString());
        holder.itemTotal.setText("Total: Rs "+String.valueOf(total));
        int i = Integer.valueOf(listItem.getItemDiscount());
        double j = Double.valueOf(listItem.getItemPrice());
        double k = (j*i)/100;
        j = j+k;
        holder.itemDiscount.setText("Rs "+String.valueOf(j));
        holder.itemDiscount.setPaintFlags(holder.itemDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = Integer.valueOf(holder.itemQuantity.getText().toString());
                if(j==1){}
                else{
                    j--;
                    holder.itemQuantity.setText(String.valueOf(j));
                    total = Integer.valueOf(listItem.getItemPrice())*j;
                    holder.itemTotal.setText("Total: Rs "+String.valueOf(total));
                }
            }
        });
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = Integer.valueOf(holder.itemQuantity.getText().toString());
                j++;
                holder.itemQuantity.setText(String.valueOf(j));
                total = Integer.valueOf(listItem.getItemPrice())*j;
                holder.itemTotal.setText("Total: Rs "+String.valueOf(total));
            }
        });
        holder.itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = Integer.valueOf(listItem.getItemPrice())*Integer.valueOf(holder.itemQuantity.getText().toString());

                holder.db.addCart(new Cart(listItem.getItemId(),
                        listItem.getItemName(),
                        listItem.getItemSType(),
                        listItem.getItemImage(),
                        holder.itemQuantity.getText().toString(),
                        listItem.getItemPrice(),
                        String.valueOf(total)));
                int s = holder.db.getCartItemCount();
                Toast.makeText(context, listItem.getItemName()+" Added to Cart", Toast.LENGTH_SHORT).show();
                ((CutsandSprouts) context).notification.setText(String.valueOf(s));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemPrice;
        public TextView itemDiscount;
        public ImageView itemImage;
        public CardView itemInfo;
        public String Id;
        public TextView itemQuantity;
        public TextView itemTotal;
        public TextView increment;
        public TextView decrement;
        public TextView itemAdd;
        public Vegetables v;
        CartDatabaseHelper db;



        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemDiscount = (TextView) itemView.findViewById(R.id.itemDiscount);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemTotal = (TextView) itemView.findViewById(R.id.itemTotal);
            increment = (TextView) itemView.findViewById(R.id.increment);
            decrement = (TextView) itemView.findViewById(R.id.decrement);
            itemAdd = (TextView) itemView.findViewById(R.id.itemAdd);

            Resources res = itemView.getResources();
            db = new CartDatabaseHelper(context);
            v = new Vegetables();
            //rupee = res.getString(R.string.rupees_symbol);

        }
    }
}

