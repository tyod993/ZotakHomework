package com.audiokontroller.homework.data.models;

import java.util.ArrayList;

public class CustomerOrder {

    private ArrayList<Snack> order;

    //<--------------------------- CONSTRUCTORS ------------------------------------->

    //Basic constructor creating an empty CustomerOrder
    public CustomerOrder(){
        this.order = new ArrayList<>();
    }

    public CustomerOrder(ArrayList<Snack> order){
        this.order = order;
    }

    //<--------------------------- GETTERS & SETTERS ------------------------------->


    public void setOrder(ArrayList<Snack> order) {
        this.order = order;
    }

    public ArrayList<Snack> getOrder() {
        return order;
    }

    public void addItemToOrder(Snack orderItem){
        order.add(orderItem);
    }

    public void removeItemFromOrder(Snack orderItem){
        order.remove(orderItem);
    }
}
