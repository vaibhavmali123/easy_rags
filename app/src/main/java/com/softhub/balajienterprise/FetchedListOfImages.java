package com.softhub.balajienterprise;

public class FetchedListOfImages {

    private String item_image;
    private String item_desc;

    public FetchedListOfImages(String item_image, String item_desc) {
        this.item_image = item_image;
        this.item_desc = item_desc;
    }

    public FetchedListOfImages(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }
}
