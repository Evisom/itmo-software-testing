package org.example.task3;

import org.example.task3.enams.ClothingType;

public class Clothing {
    private ClothingType type;
    private String color;

    public Clothing(ClothingType type, String color) {
        this.type = type;
        this.color = color;
    }

    public ClothingType getType() { return type; }
    public String getColor() { return color; }
}
