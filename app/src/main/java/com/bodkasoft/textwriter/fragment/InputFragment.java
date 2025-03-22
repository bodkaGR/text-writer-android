package com.bodkasoft.textwriter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.bodkasoft.textwriter.databinding.FragmentInputBinding;
import com.bodkasoft.textwriter.viewmodel.MainViewModel;

public class InputFragment extends Fragment {
    private FragmentInputBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        setupObservers();
        setupListeners(view);
    }

    private void setupListeners(View view) {
        // Handle button click
        binding.buttonOkView.setOnClickListener(v -> {
            String inputText = binding.textInputView.getText().toString();
            int selectedRadioId = binding.textSizeRadioGroup.getCheckedRadioButtonId();
            viewModel.onButtonOkClicked(inputText, selectedRadioId);
        });

        // Handle text size radio button
        binding.textSizeRadioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton selectedRadioButton = view.findViewById(checkedId);
            if (selectedRadioButton != null) {
                viewModel.onTextSizeSelected(selectedRadioButton.getText().toString());
            }
        }));
    }

    private void setupObservers() {
        // Set input and text size observers
        viewModel.getInputText().observe(requireActivity(), binding.textInputView::setText);
        viewModel.getSelectedTextSize().observe(requireActivity(), size -> {
            if (size == -1) {
                binding.textSizeRadioGroup.clearCheck();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}