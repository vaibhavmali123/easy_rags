package com.softhub.balajienterprise;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<FetchedListOfCartItem> listItems;
    private Context context;

    String rupee;



    public ContactAdapter(List<FetchedListOfCartItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart_item, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfCartItem listItem = listItems.get(position);

        holder.itemName.setText(listItem.getItemName());
        holder.itemQuantity.setText(listItem.getItemQuantity());
        //holder.itemSaleType.setText(listItem.getItemSType());
        holder.itemPrice.setText("Rate: Rs. "+listItem.getItemPrice()+"/"+listItem.getItemSType());
        holder.itemTotal.setText("Total: Rs "+listItem.getItemTotal());
        Picasso.with(context).load(listItem.getItemImage()).into(holder.itemImage);

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.valueOf(holder.itemQuantity.getText().toString());
                if(x>1){
                    x--;
                    holder.itemQuantity.setText(String.valueOf(x));
                    int j = Integer.valueOf(listItem.getItemPrice());
                    double k = x*j;
                    //holder.itemTotal.setText(String.valueOf(k));
                    holder.db.updateCart(String.valueOf(listItem.getCartId()),String.valueOf(x),String.valueOf(k));
//                    Intent intent = new Intent(context,CartActivity.class);
//                    intent.putExtra("address", "abc");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    context.startActivity(intent);
                    ((CartActivity) context).updateCartList();

                }
            }
        });
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.valueOf(listItem.getItemQuantity());
                    i++;
                    holder.itemQuantity.setText(String.valueOf(i));
                int j = Integer.valueOf(listItem.getItemPrice());
                double k = i*j;
                //holder.itemTotal.setText(String.valueOf(k));

                holder.db.updateCart(String.valueOf(listItem.getCartId()),String.valueOf(i),String.valueOf(k));
//                Intent intent = new Intent(context,CartActivity.class);
//                intent.putExtra("address", "abc");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
                ((CartActivity) context).updateCartList();
            }
        });
        holder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = listItem.getCartId();
                holder.db.deleteEntry(String.valueOf(id));
//                Intent i = new Intent(context,CartActivity.class);
//                i.putExtra("address", "abc");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(i);
                ((CartActivity) context).updateCartList();
            }
        });

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullImageIntent = new Intent(context, FullScreenImage.class);
                fullImageIntent.putExtra("image", listItem.getItemImage());
                fullImageIntent.putExtra("name", listItem.getItemName());
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
        public ImageView itemImage;
        public CardView itemInfo;
        public String Id;
        public TextView itemQuantity;
        public TextView itemSaleType;
        public TextView itemTotal;
        public TextView increment;
        public TextView decrement;
        public TextView itemRemove;
        CartDatabaseHelper db = new CartDatabaseHelper(context);



        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            //itemSaleType = (TextView) itemView.findViewById(R.id.itemSaleType);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemTotal = (TextView) itemView.findViewById(R.id.itemTotal);
            increment = (TextView) itemView.findViewById(R.id.increment);
            decrement = (TextView) itemView.findViewById(R.id.decrement);
            itemRemove = (TextView) itemView.findViewById(R.id.itemRemove);

            Resources res = itemView.getResources();
            db = new CartDatabaseHelper(context);
            //rupee = res.getString(R.string.rupees_symbol);

        }
    }
}

