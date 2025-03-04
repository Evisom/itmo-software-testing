package tpo.lab1.model;

import tpo.lab1.model.enums.ClothingType;

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
