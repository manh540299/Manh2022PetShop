package com.manh.petshopdemo1.model;

import java.util.List;

public class SizeItem {
    private int id;
    private String name;
    private List<TypeItem> type;

    public SizeItem(int id, String name, List<TypeItem> type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeItem> getType() {
        return type;
    }

    public void setType(List<TypeItem> type) {
        this.type = type;
    }
}
