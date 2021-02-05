package com.audiokontroller.homework.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audiokontroller.homework.R;
import com.audiokontroller.homework.data.models.CustomerOrder;
import com.audiokontroller.homework.data.models.Snack;
import com.audiokontroller.homework.main.viewmodel.MainViewModel;

import java.util.ArrayList;

//Adapter used in the MainFragment to build the Menu
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private static final String TAG = MenuListAdapter.class.getSimpleName();

    //This context is used for getting color resources for setting text color.
    private final Context context;

    //This list is used to populate the ListView
    public ArrayList<Snack> snackList;

    //This reference to the MainViewModel is to add snack items to the CustomerOrder
    private final MainViewModel mainViewModel;

    //<--------------------------------- VIEW HOLDER ---------------------------------->

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemName;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.menu_snack_name);
            checkBox = itemView.findViewById(R.id.select_item_check_box);
        }
    }

    //<--------------------- CONSTRUCTOR ------------------------------------>

    public MenuListAdapter(@NonNull Context context, @NonNull MainViewModel mainViewModel){

        this.context = context;
        this.mainViewModel = mainViewModel;
        this.snackList = mainViewModel.menuDataUI;

    };

    //<------------------------------------------------------------------------------>

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        Log.d(TAG,"onCreateViewHolder called");
        //Set the snackList to the menuDataUI from the viewModel to make sure the adapter always has recent data;
        this.snackList = mainViewModel.menuDataUI;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Set to avoid state changes when recycling items
        holder.setIsRecyclable(false);

        Snack currentSnack = snackList.get(position);

        //Set the text to the Snack name
        holder.itemName.setText(currentSnack.getName());

        //Set the text color
        holder.itemName.setTextColor(
                currentSnack.isVegetarian() ?
                        context.getResources().getColor(R.color.green) :
                        context.getResources().getColor(R.color.red)
        );

        //set the initial state of the check box to not checked
        holder.checkBox.setChecked(false);

        //check if the user has already selected this snack in the past
        if(mainViewModel.getCustomerOrderMutableLiveData().getValue().getOrder().contains(currentSnack)){
            //set the checkBox to checked
            holder.checkBox.setChecked(true);
        }

        //Set the checkbox listener
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            CustomerOrder order = mainViewModel.getCustomerOrderMutableLiveData().getValue();
            //Log.d(TAG, order.getOrder().toString());

            //if the Checkbox is Checked add the the snack to the customerOrder if not remove it from the order
            if(isChecked){
                order.addItemToOrder(currentSnack);
                //Log.d(TAG, "isChecked");
            } else if (!isChecked) {
                //Log.d(TAG, "!isChecked");
                order.removeItemFromOrder(currentSnack);
            }

        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return snackList.size();
    }

}
