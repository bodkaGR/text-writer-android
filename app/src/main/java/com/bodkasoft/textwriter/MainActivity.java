package com.bodkasoft.textwriter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bodkasoft.textwriter.databinding.ActivityMainBinding;
import com.bodkasoft.textwriter.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupObserver();
        setupListeners();
    }

    private void setupObserver() {
        viewModel.getResultText().observe(this, binding.resultTextView::setText);
        viewModel.getInputText().observe(this, binding.textInputView::setText);

        viewModel.getSelectedTextSize().observe(this, size -> {
            if (size == -1) {
                binding.textSizeRadioGroup.clearCheck();
            }
            binding.resultTextView.setTextSize(size);
        });

        viewModel.getSnackBarMessage().observe(this, message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                viewModel.clearSnackBarMessage();
            }
        });
    }

    private void setupListeners() {
        binding.buttonOkView.setOnClickListener(view -> {
            String inputText = binding.textInputView.getText().toString();
            int selectedRadioId = binding.textSizeRadioGroup.getCheckedRadioButtonId();
            viewModel.onButtonOkClicked(inputText, selectedRadioId);
        });

        binding.buttonCancelView.setOnClickListener(view -> viewModel.onCancelButtonClick());

        binding.textSizeRadioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                viewModel.onTextSizeSelected(selectedRadioButton.getText().toString());
            }
        }));
    }
}