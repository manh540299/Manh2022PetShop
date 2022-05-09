package com.manh.petshopdemo1.model;

public class Menu {
    private int id;
    private int image;
    private String tetx;

    public Menu() {
    }
    public Menu(int id, int image, String tetx) {
        this.id = id;
        this.image = image;
        this.tetx = tetx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTetx() {
        return tetx;
    }

    public void setTetx(String tetx) {
        this.tetx = tetx;
    }
}
