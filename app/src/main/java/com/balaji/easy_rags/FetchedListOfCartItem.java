package com.balaji.easy_rags;

public class FetchedListOfCartItem {

    private Integer cartId;
    private String itemId;
    private String itemName;
    private String itemImage;
    private String itemSType;
    private String itemQuantity;
    private String itemPrice;
    private String itemTotal;

    public FetchedListOfCartItem(Integer cartId, String itemId, String itemName, String itemImage, String itemSType, String itemQuantity, String itemPrice, String itemTotal) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSType = itemSType;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemTotal = itemTotal;
    }

    public FetchedListOfCartItem(String itemId, String itemName, String itemImage, String itemSType, String itemQuantity, String itemPrice, String itemTotal) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSType = itemSType;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemTotal = itemTotal;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
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
