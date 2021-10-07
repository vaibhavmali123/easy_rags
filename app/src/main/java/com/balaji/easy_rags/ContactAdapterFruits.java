package com.balaji.easy_rags;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapterFruits extends RecyclerView.Adapter<ContactAdapterFruits.ViewHolder> {

    private List<FetchedListOfItem> listItems;
    private Context context;

    String rupee;
    double total;



    public ContactAdapterFruits(List<FetchedListOfItem> listItems, Context context) {
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

        Log.d("IMAGE URL",listItem.getItemImage());

        holder.itemName.setText(listItem.getItemName());
        holder.itemPrice.setText("Rs "+listItem.getItemPrice());
        holder.itemUnit.setText(listItem.getItemSType());
        Picasso.with(context).load(listItem.getItemImage()).into(holder.itemImage);
        Log.d("TTTTT",listItem.getItemPrice()+"       "+holder.itemQuantity.getText());

        double price=Double.parseDouble(listItem.getItemPrice().toString());
        int qty=Integer.parseInt(holder.itemQuantity.getText().toString().trim());
        total=price*qty;
        //        total = Integer.valueOf(listItem.getItemPrice())*Integer.valueOf(holder.itemQuantity.getText().toString());
        holder.itemTotal.setText("Total: Rs "+String.valueOf(total));
        int i = Integer.valueOf(listItem.getItemDiscount());
        double j = Double.valueOf(listItem.getItemPrice());
        double k = (j*i)/100;
        j = j+k;
        holder.itemDiscount.setText("Rs "+j);
        holder.itemDiscount.setPaintFlags(holder.itemDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if(i==0){
            holder.itemDiscount.setVisibility(View.GONE);
        }

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
                ((Home) context).notification.setText(String.valueOf(s));

            }
        });
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullImageIntent = new Intent(context, FullScreenImage.class);
                fullImageIntent.putExtra("image", listItem.getItemImage());
                fullImageIntent.putExtra("name", listItem.getItemName());
                fullImageIntent.putExtra("description", listItem.getItemDescription());
                context.startActivity(fullImageIntent);
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
        public TextView itemUnit;
        public TextView itemTotal;
        public TextView increment;
        public TextView decrement;
        public AppCompatButton itemAdd;
        public Fruits f;
        CartDatabaseHelper db;



        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemDiscount = (TextView) itemView.findViewById(R.id.itemDiscount);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemUnit = (TextView) itemView.findViewById(R.id.itemUnit);
            itemTotal = (TextView) itemView.findViewById(R.id.itemTotal);
            increment = (TextView) itemView.findViewById(R.id.increment);
            decrement = (TextView) itemView.findViewById(R.id.decrement);
            itemAdd = (AppCompatButton) itemView.findViewById(R.id.itemAdd);
            f = new Fruits();
            Resources res = itemView.getResources();
            db = new CartDatabaseHelper(context);

            //rupee = res.getString(R.string.rupees_symbol);

        }
    }
}

