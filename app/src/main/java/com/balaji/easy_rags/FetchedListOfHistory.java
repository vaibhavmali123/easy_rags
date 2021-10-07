package com.balaji.easy_rags;

public class FetchedListOfHistory {

    private String order_id;
    private String invoice_id;
    private String order_total;
    private String order_date;
    private String order_status;
    private String image;

    public FetchedListOfHistory(String order_id, String invoice_id, String order_total, String order_date, String order_status) {
        this.order_id = order_id;
        this.invoice_id = invoice_id;
        this.order_total = order_total;
        this.order_date = order_date;
        this.order_status = order_status;
        this.image=image;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
