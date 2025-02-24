package com.bodkasoft.textwriter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bodkasoft.textwriter.databinding.FragmentOutputBinding;
import com.bodkasoft.textwriter.viewmodel.MainViewModel;

public class OutputFragment extends Fragment {
    private FragmentOutputBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOutputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        setupObservers();
        setupListeners();
    }

    private void setupListeners() {
        // Handle button cancel
        binding.buttonCancelView.setOnClickListener(v -> viewModel.onCancelButtonClick());
    }

    private void setupObservers() {
        // Set result text and text size observers
        viewModel.getResultText().observe(requireActivity(), binding.resultTextView::setText);
        viewModel.getSelectedTextSize().observe(requireActivity(), binding.resultTextView::setTextSize);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}