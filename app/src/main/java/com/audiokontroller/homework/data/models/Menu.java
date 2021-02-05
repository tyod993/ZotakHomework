package com.audiokontroller.homework.data.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Menu {

    private static final String TAG = Menu.class.getSimpleName();

    private ArrayList<Snack> menuList;

    //------------------------------------- CONSTRUCTORS -------------------------------------->

    //Constructor for creating a basic empty Menu object -trevor
    public Menu(){
        this.menuList = new ArrayList<>();
    }

    //Constructor for creating Menu object from a list of Snack objects -trevor
    public Menu(@NonNull ArrayList<Snack> menuList){
        this.menuList = new ArrayList<>(menuList);
    }

    //Constructor for creating a Menu Object from JSON -trevor
    public Menu(@NonNull String json) throws JSONException {

        JSONObject jsonObject = null;

        try{jsonObject = new JSONObject(json);}
        catch (JSONException exception){
            Log.e(TAG, "There was an problem parsing JSON data. Exception = " + exception.getMessage());
        }

        if(jsonObject != null){
            JSONArray jsonArray = jsonObject.getJSONArray("menuList");

            //Check if the jsonArray is empty. If the array is empty set the menu to an empty array. -trevor
            if(jsonArray.length() > 0) {

                //Iterate through the jsonArray creating Snack objects for each entry. -trevor
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject menuItem = jsonArray.getJSONObject(i);
                    Snack newSnack = new Snack(menuItem.getString("name"), menuItem.getBoolean("isVegetarian"));
                    menuList.add(newSnack);
                }
            } else {
                menuList = new ArrayList<>();
            }
        }
    }

    //------------------------------ GETTERS & SETTERS -------------------------------------->

    //
    public ArrayList<Snack> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<Snack> menuList) {
        this.menuList = menuList;
    }

    public void addMenuItem(Snack newItem) {
        menuList.add(newItem);
    }

    //------------------------------ UTILITIES -------------------------------------------->

    //returns this Menu object in JSON format
    public String menuToJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void removeVegFromMenu(){
        menuList.removeIf(Snack::isVegetarian);
    }

    public void removeMeatFromMenu(){
        menuList.removeIf(snack -> (!snack.isVegetarian()));
    }

    public void addVegToMenu(ArrayList<Snack> fullMenu){
        for(Snack snack : fullMenu){
            if(snack.isVegetarian()){
                menuList.add(snack);
            }
        }
        //Log.d(TAG, menuList.toString());
    }
    public void addMeatToMenu(ArrayList<Snack> fullMenu){
        for(Snack snack : fullMenu){
            if(!snack.isVegetarian()){
                menuList.add(snack);
            }
        }
    }
}

