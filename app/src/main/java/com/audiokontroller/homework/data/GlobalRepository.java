package com.audiokontroller.homework.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.audiokontroller.homework.data.models.CustomerOrder;
import com.audiokontroller.homework.data.models.Menu;
import com.audiokontroller.homework.data.models.Snack;

import java.util.ArrayList;

public class GlobalRepository {

    private static final String TAG = GlobalRepository.class.getSimpleName();

    private static volatile GlobalRepository instance;

    private Menu menu;

    private GlobalRepository(){
    }

    //Call to retrieve the SINGLETON instance of the GLobarRepository
    public static GlobalRepository getInstance(){
        if(instance == null){
            instance = new GlobalRepository();
        }
        return instance;
    }

    //Creates a GET request and calls the REST endpoint to retrieve the menu data
    public Menu getMenuData(){
        if(menu == null){
            //TODO If you have time build a quick REST endpoint to call
            //This is where I would make the network call.

            //This is just Dummy data for the time being
            ArrayList<Snack> menuData = new ArrayList<>();
            menuData.add(new Snack("French Fries 🍟", true));
            menuData.add(new Snack("Veggieburger 🥪", true));
            menuData.add(new Snack("Carrots 🥕", true));
            menuData.add(new Snack("Apple 🍎", true));
            menuData.add(new Snack("Banana 🍌", true));
            menuData.add(new Snack("Milkshake 🍦", true));
            menuData.add(new Snack("Cheeseburger 🍔", false));
            menuData.add(new Snack("Hamburger 🍔", false));
            menuData.add(new Snack("Hot Dog 🌭", false));

            menu = new Menu(menuData);
        }

        return menu;
    };

    public void saveMenu(@NonNull Menu newMenu){
        menu = newMenu;
        //TODO save the new menu using the Rest Endpoint if you have time
        //Here is where I would save the menu state so it could update all other apps

    }

    public void sendOrder(CustomerOrder order){
        Log.d(TAG, order.getOrder().toString());
        //This should send the order via the Rest Endpoint and update the order list on the main device
    }

}

