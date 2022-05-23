package com.manh.petshopdemo1.model;

import android.os.Parcel;
import android.os.Parcelable;
public class Cart implements Parcelable {
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
    public Cart(String name, String image, int quantity, String size, long price) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
    }

    protected Cart(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        quantity = in.readInt();
        size = in.readString();
        price = in.readLong();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(quantity);
        parcel.writeString(size);
        parcel.writeLong(price);
    }
}
