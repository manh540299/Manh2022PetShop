package com.manh.petshopdemo1.model;

public class Cart {
    private int id;
    private String name;
    private String image;
    private int quantity;
    private String size;
    private long price;

    public Cart() {
    }

    public Cart(int id, String name, String image, int quantity, String size, long price) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
