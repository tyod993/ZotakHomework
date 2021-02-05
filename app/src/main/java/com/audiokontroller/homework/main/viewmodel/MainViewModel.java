package com.audiokontroller.homework.main.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.homework.data.GlobalRepository;
import com.audiokontroller.homework.data.models.CustomerOrder;
import com.audiokontroller.homework.data.models.Menu;
import com.audiokontroller.homework.data.models.Snack;
import com.audiokontroller.homework.main.ReviewOrderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 This is the main ViewModel for the app as it currently stands. If the app grows in the future
 it would be a good idea to create new ViewModels for those features. This ViewModel is shared with
 both:
 @see MainViewModel
 @see ReviewOrderFragment

 */

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private GlobalRepository repository;

    public boolean isVegOnTheMenu = true;
    public boolean isMeatOnTheMenu = true;

    //This is used by the add menu item Fragment. It will be null until the edittext has data and onPause is called
    //When more features are created on the Admin side this should move to a separate viewModel
    private String addMenuItemNameState;
    private boolean vegetarianSwitchState;

    //This only holds the snacks that are currently visible in the menu. Do not use this var if you need the whole list as it changes
    public ArrayList<Snack> menuDataUI;

    private final MutableLiveData<CustomerOrder> customerOrderMutableLiveData = new MutableLiveData<>(new CustomerOrder());
    private final MutableLiveData<Menu> menuMutableLiveData = new MutableLiveData<>();

    //<------------------------- CONSTRUCTOR --------------------------------------->

    public MainViewModel(){
    }

    public void initState(){
        repository = GlobalRepository.getInstance();
        //Must use setValue
        menuMutableLiveData.setValue(new Menu (repository.getMenuData().getMenuList()));
        menuDataUI = new Menu (repository.getMenuData().getMenuList()).getMenuList();
    }

    //<--------------------------- GETTERS & SETTERS--------------------------------->


    public LiveData<CustomerOrder> getCustomerOrderMutableLiveData() {
        return customerOrderMutableLiveData;
    }

    public LiveData<Menu> getMenuMutableLiveData() {
        return menuMutableLiveData;
    }

    public HashMap<String, Object> getAddItemFragState(){
        HashMap<String, Object> returnValue = new HashMap<>(2);
        returnValue.put("name_input", addMenuItemNameState);
        returnValue.put("vegetarian", vegetarianSwitchState);
        return returnValue;
    }

    public void saveAddItemState(String name, boolean vegetarian){
        vegetarianSwitchState = vegetarian;
        addMenuItemNameState = name;
    }

    public void clearAddItemState() {
        addMenuItemNameState = null;
        vegetarianSwitchState = false;
    }


    //<---------------------------- USER ACTIONS ------------------------->

    public void addMenuItem(@NonNull Snack newItem){
        Log.d(TAG, "add menu item called");
        Menu menu =  menuMutableLiveData.getValue();
        menu.addMenuItem(newItem);
        menuMutableLiveData.setValue(menu);
        repository.saveMenu(menuMutableLiveData.getValue());

        menuDataUI.add(newItem);
    }

    public void addOrderItem(@NonNull Snack newItem){
        customerOrderMutableLiveData.getValue().addItemToOrder(newItem);
    }

    public void submitOrder(){
        repository.sendOrder(customerOrderMutableLiveData.getValue());
        clearOrder();
    }

    public void clearOrder(){
        customerOrderMutableLiveData.setValue(new CustomerOrder());
    }

    public void vegetarianCheckBoxToggle(boolean isChecked){
        isVegOnTheMenu = isChecked;
    }

    public void nonVegetarianCheckBoxToggle(boolean isChecked){
        isMeatOnTheMenu = isChecked;
    }
}
