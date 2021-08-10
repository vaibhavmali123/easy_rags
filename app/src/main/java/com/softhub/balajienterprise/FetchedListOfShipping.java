package com.softhub.balajienterprise;

public class FetchedListOfShipping {

    private String id;
    private String shipping;

    public FetchedListOfShipping(String id, String shipping) {
        this.id = id;
        this.shipping = shipping;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
