package com.balaji.easy_rags;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CartDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "cartManager";
    private static final String TABLE_NAME = "cart";
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM_ID = "itemId";
    private static final String KEY_ITEM_NAME = "itemName";
    private static final String KEY_ITEM_IMAGE = "itemImage";
    private static final String KEY_ITEM_SALE_TYPE = "itemSType";
    private static final String KEY_ITEM_QUANTITY = "itemQuantity";
    private static final String KEY_ITEM_PRICE = "itemPrice";
    private static final String KEY_ITEM_TOTAL = "itemTotal";


    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ITEM_ID + " TEXT," +KEY_ITEM_NAME + " TEXT," +
                KEY_ITEM_IMAGE + " TEXT," + KEY_ITEM_SALE_TYPE + " TEXT," +KEY_ITEM_QUANTITY + " TEXT," +KEY_ITEM_PRICE + " TEXT,"
                +KEY_ITEM_TOTAL + " TEXT" + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    void addCart(Cart cart){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cart.getItem_id());
        values.put(KEY_ITEM_NAME, cart.getItem_name());
        values.put(KEY_ITEM_IMAGE, cart.getItem_image());
        values.put(KEY_ITEM_SALE_TYPE, cart.getItem_saleType());
        values.put(KEY_ITEM_QUANTITY, cart.getItem_quantity());
        values.put(KEY_ITEM_PRICE, cart.getItem_price());
        values.put(KEY_ITEM_TOTAL, cart.getItem_total());
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Cart> getAllCart(){

        List<Cart> cartList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Cart cart = new Cart();
                cart.setId(cursor.getInt(0));
                cart.setItem_id(cursor.getString(1));
                cart.setItem_name(cursor.getString(2));
                cart.setItem_image(cursor.getString(4));
                cart.setItem_saleType(cursor.getString(3));
                cart.setItem_quantity(cursor.getString(5));
                cart.setItem_price(cursor.getString(6));
                cart.setItem_total(cursor.getString(7));
                cartList.add(cart);

            } while (cursor.moveToNext());
        }

        return cartList;
    }

    public void deleteCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null,null);
        db.close();
    }

    public void deleteEntry(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID+"="+id, null);
        db.close();
    }

    public int updateCart(String i, String q, String t){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_QUANTITY, q);
        values.put(KEY_ITEM_TOTAL, t);

        return db.update(TABLE_NAME, values, KEY_ID + "=" +i, null);
    }

    public int getCartItemCount(){
        String count = "SELECT *FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count,null);
        int i = cursor.getCount();
        cursor.close();

        return i;
    }


}
