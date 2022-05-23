package com.manh.petshopdemo1.model;

public class OrderDetail {
    private int id;
    private String itemName;
    private String image;
    private int quantity;
    private String size;
    private long price;
    private int idOrder;

    public OrderDetail(int id, String itemName, String image, int quantity, String size, long price, int idOrder) {
        this.id = id;
        this.itemName = itemName;
        this.image = image;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.idOrder = idOrder;
    }

    public OrderDetail(String itemName, String image, int quantity, String size, long price) {
        this.itemName = itemName;
        this.image = image;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
