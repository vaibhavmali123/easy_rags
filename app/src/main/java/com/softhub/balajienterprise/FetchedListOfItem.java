package com.softhub.balajienterprise;

public class FetchedListOfItem {

    private String itemId;
    private String itemName;
    private String itemImage;
    private String itemSType;
    private String itemPrice;
    private String itemDiscount;
    private String itemTotal;
    private String itemDescription;

    public FetchedListOfItem(String itemId, String itemName, String itemImage, String itemSType, String itemPrice, String itemDiscount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSType = itemSType;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
    }

    public FetchedListOfItem(String itemId, String itemName, String itemImage, String itemSType, String itemPrice, String itemDiscount, String itemDescription) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSType = itemSType;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
        this.itemDescription = itemDescription;
    }

    public FetchedListOfItem(String itemId, String itemName, String itemImage, String itemSType, String itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSType = itemSType;
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(String itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemSType() {
        return itemSType;
    }

    public void setItemSType(String itemSType) {
        this.itemSType = itemSType;
    }


    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }
}
