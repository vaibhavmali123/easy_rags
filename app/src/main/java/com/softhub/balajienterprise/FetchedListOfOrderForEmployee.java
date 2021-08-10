package com.softhub.balajienterprise;

public class FetchedListOfOrderForEmployee {

    private String order_id;
    private String order_date;
    private String order_customer;
    private String order_mobile;
    private String order_address;
    private String order_lat;
    private String order_long;
    private String order_amount;

    public FetchedListOfOrderForEmployee(String order_id, String order_date, String order_customer, String order_mobile, String order_address, String order_lat, String order_long, String order_amount) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_customer = order_customer;
        this.order_mobile = order_mobile;
        this.order_address = order_address;
        this.order_lat = order_lat;
        this.order_long = order_long;
        this.order_amount = order_amount;
    }

    public String getOrder_mobile() {
        return order_mobile;
    }

    public void setOrder_mobile(String order_mobile) {
        this.order_mobile = order_mobile;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_customer() {
        return order_customer;
    }

    public void setOrder_customer(String order_customer) {
        this.order_customer = order_customer;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_lat() {
        return order_lat;
    }

    public void setOrder_lat(String order_lat) {
        this.order_lat = order_lat;
    }

    public String getOrder_long() {
        return order_long;
    }

    public void setOrder_long(String order_long) {
        this.order_long = order_long;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }
}
