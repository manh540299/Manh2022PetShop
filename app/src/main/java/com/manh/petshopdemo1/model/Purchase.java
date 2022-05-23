package com.manh.petshopdemo1.model;

public class Purchase {
    private int id;
    private String image;
    private String name;
    private String size;
    private int quantity;
    private long price;
    private long total;
    private int quantityAllItem;

    public Purchase(int id, String image, String name, String size, int quantity, long price, long total, int quantityAllItem) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.quantityAllItem = quantityAllItem;
    }

    public Purchase() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantityAllItem() {
        return quantityAllItem;
    }

    public void setQuantityAllItem(int quantityAllItem) {
        this.quantityAllItem = quantityAllItem;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
