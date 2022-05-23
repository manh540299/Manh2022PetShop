package com.manh.petshopdemo1.model;

public class Order {
    private int id;
    private String gmail;
    private String name;
    private String phone;
    private String address;
    private long total;

    public Order(int id, String gmail, String name, String phone, String address, long total) {
        this.id = id;
        this.gmail = gmail;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
