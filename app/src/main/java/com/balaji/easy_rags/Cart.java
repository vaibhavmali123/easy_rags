package com.balaji.easy_rags;

public class Cart {

    int id;
    String item_id;
    String item_name;
    String item_image;
    String item_saleType;
    String item_quantity;
    String item_price;
    String item_total;

    public Cart(int id, String item_quantity, String item_total) {
        this.id = id;
        this.item_quantity = item_quantity;
        this.item_total = item_total;
    }

    public Cart(String item_id, String item_name, String item_image, String item_saleType, String item_quantity, String item_price, String item_total) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_image = item_image;
        this.item_saleType = item_saleType;
        this.item_quantity = item_quantity;
        this.item_price = item_price;
        this.item_total = item_total;
    }

    public Cart() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_saleType() {
        return item_saleType;
    }

    public void setItem_saleType(String item_saleType) {
        this.item_saleType = item_saleType;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }
}
