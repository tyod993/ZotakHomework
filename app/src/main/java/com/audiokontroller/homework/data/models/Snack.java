package com.audiokontroller.homework.data.models;

public class Snack {

    private final String name;

    private final boolean isVegetarian;

    public Snack(String name, boolean isVegetarian){
        this.name = name;
        this.isVegetarian = isVegetarian;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }
}
