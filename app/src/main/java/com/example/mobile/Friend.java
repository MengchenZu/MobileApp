package com.example.mobile;

import java.io.Serializable;

public class Friend implements Serializable {
    private String name;
    private int imageId;

    public Friend(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() { return this.name; }

    public int getImageId() {
        return this.imageId;
    }
}
