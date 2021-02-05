package com.audiokontroller.homework.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audiokontroller.homework.R;
import com.audiokontroller.homework.databinding.ReviewOrderFragmentBinding;
import com.audiokontroller.homework.main.adapter.ReviewMenuListAdapter;
import com.audiokontroller.homework.main.viewmodel.MainViewModel;

/**
 *This fragment is used when th user submits his/her order. It contains two buttons for
 *either confirming the order and sending it to the server or canceling and clearing the selections.
 */
public class ReviewOrderFragment extends Fragment {

    private static final String TAG = ReviewOrderFragment.class.getSimpleName();

    private MainViewModel mViewModel;

    private ReviewOrderFragmentBinding binding;

    public static ReviewOrderFragment newInstance() {
        return new ReviewOrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = ReviewOrderFragmentBinding.inflate(getLayoutInflater());

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = binding.reviewOrderListView;
        Button submitReviewButton = binding.submitReviewButton;
        Button cancelReviewButton = binding.cancelReviewButton;

        ReviewMenuListAdapter adapter = new ReviewMenuListAdapter(
                getContext(),
                R.layout.review_list_item,
                mViewModel.getCustomerOrderMutableLiveData().getValue().getOrder());

        listView.setAdapter(adapter);

        //<------------------------------- USER ACTIONS ----------------------------->

        submitReviewButton.setOnClickListener(listener ->{
            mViewModel.submitOrder();
            getFragmentManager().beginTransaction().remove(this).commitNow();
        });

        cancelReviewButton.setOnClickListener(listener -> {
            mViewModel.clearOrder();
            getFragmentManager().beginTransaction().remove(this).commitNow();
        });
    }
}
