package com.audiokontroller.homework.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.audiokontroller.homework.R;
import com.audiokontroller.homework.data.models.Snack;
import com.audiokontroller.homework.databinding.MainFragmentBinding;
import com.audiokontroller.homework.main.adapter.MenuListAdapter;
import com.audiokontroller.homework.main.viewmodel.MainViewModel;

/**
 *
 * This is the main screen that the users will see when ordering their food 🍕🍕🍕
 * The Fragment contains a list of the list of menu items , filters for vegetarian/meat
 * options and a button to submit their order.
 *
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private MainViewModel mViewModel;

    private MainFragmentBinding binding;

    private MenuListAdapter adapter;

    //<---------------------------- CONSTRUCTOR ---------------------------------------->

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    //<-------------------------------------------------------------------------------->

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //The binding must be inflated here or it wont work. DO NOT MOVE.
        binding = MainFragmentBinding.inflate(getLayoutInflater());

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.initState();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Get reference to all the UI components from the Fragment's binding class
        CheckBox vegetarianCheckBox = binding.vegetarianCheckBox;
        CheckBox nonVegetarianCheckBox = binding.nonVegetarianCheckBox;
        Button addMenuItemButton = binding.addMenuItemButton;
        Button submitButton = binding.submitButton;
        RecyclerView menuRecyclerView = binding.menuRecyclerView;

        //Set the checkboxes to checked initially
        vegetarianCheckBox.setChecked(true);
        nonVegetarianCheckBox.setChecked(true);

        //Populate the RecyclerView with the Menu
        adapter = new MenuListAdapter(getContext(), mViewModel);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        menuRecyclerView.setAdapter(adapter);
        menuRecyclerView.setLayoutManager(manager);


        //<----------------------------- LIVE DATA -------------------------------->

        //Observe the menu for changes
        mViewModel.getCustomerOrderMutableLiveData().observe(getViewLifecycleOwner(), order -> {
            adapter.notifyDataSetChanged();
        });

        //<---------------------------- LISTENERS ----------------------------->

        submitButton.setOnClickListener(listener -> {
            //Check if the customerOrder is empty. If its empty ignore the button press.
            if (mViewModel.getCustomerOrderMutableLiveData().getValue().getOrder().size() > 0) {
                vegetarianCheckBox.setChecked(true);
                nonVegetarianCheckBox.setChecked(true);
                getFragmentManager().beginTransaction().add(R.id.container, ReviewOrderFragment.newInstance(), "review_fragment").commitNow();
            }
        });

        addMenuItemButton.setOnClickListener(listener -> {
            getFragmentManager().beginTransaction().add(R.id.container, new AddMenuItemFragment(), "add_item_fragment").commitNow();
        });

        //Set the vegetarian filter listener and update the menu accordingly
        vegetarianCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.vegetarianCheckBoxToggle(isChecked);
            if (!isChecked) {
                adapter.snackList.removeIf(Snack::isVegetarian);
            } else {
                mViewModel.getMenuMutableLiveData().getValue().getMenuList()
                        .forEach((snack) -> {
                            if (snack.isVegetarian()) {
                                adapter.snackList.add(snack);
                            }
                        });
            }
            //update the UI so the change is expressed to the user
            adapter.notifyDataSetChanged();
        });

        //Set the meat filter listener and update the menu accordingly
        nonVegetarianCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.nonVegetarianCheckBoxToggle(isChecked);
            if (!isChecked) {
                adapter.snackList.removeIf((snack) -> (!snack.isVegetarian()));
            } else {
                mViewModel.getMenuMutableLiveData().getValue().getMenuList()
                        .forEach((snack) -> {
                            if (!snack.isVegetarian()) {
                                adapter.snackList.add(snack);
                            }
                        });
            }
            //update the UI so the change is expressed to the user
            adapter.notifyDataSetChanged();
        });

        getFragmentManager().addOnBackStackChangedListener(() -> {
            Log.d(TAG, "Back stack listener called");
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }


}
