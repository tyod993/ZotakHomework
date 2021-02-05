package com.audiokontroller.homework.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.homework.data.models.Snack;
import com.audiokontroller.homework.databinding.AddMenuItemFragmentBinding;
import com.audiokontroller.homework.main.viewmodel.MainViewModel;

import java.util.HashMap;

public class AddMenuItemFragment extends Fragment {

    private MainViewModel mViewModel;

    private AddMenuItemFragmentBinding binding;

    private EditText nameInputField;
    private SwitchCompat vegetarianSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //The binding must be inflated here or it wont work. DO NOT MOVE.
        binding = AddMenuItemFragmentBinding.inflate(getLayoutInflater());

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.initState();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button submitButton = binding.submitItemButton;
        Button cancelButton = binding.cancelItemButton;

        nameInputField = binding.itemNameInput;
        vegetarianSwitch = binding.vegetarianSwitch;

        //<----------------------------- LISTENERS --------------------------------->

        submitButton.setOnClickListener(listener-> {
            String snackName = nameInputField.getText().toString();
            boolean vegetarian = vegetarianSwitch.isChecked();
            Snack newSnack = new Snack(snackName, vegetarian);
            getFragmentManager().beginTransaction().remove(this).commitNow();
            mViewModel.addMenuItem(newSnack);
        });

        cancelButton.setOnClickListener(listener -> {
            getFragmentManager().beginTransaction().remove(this).commitNow();
            mViewModel.clearAddItemState();
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        //<---------------------------- SAVE STATE ------------------------------------>

        String snackName = nameInputField.getText().toString();
        boolean vegetarian = vegetarianSwitch.isChecked();
        mViewModel.saveAddItemState(snackName, vegetarian);

    }

    @Override
    public void onResume() {
        super.onResume();

        //<----------------------------- SET STATE --------------------------------->

        HashMap<String, Object> state = mViewModel.getAddItemFragState();

        nameInputField.setText((String)state.get("name_input"));
        vegetarianSwitch.setChecked((boolean)state.get("vegetarian"));
    }
}
