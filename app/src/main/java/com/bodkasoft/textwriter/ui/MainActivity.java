package com.bodkasoft.textwriter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bodkasoft.textwriter.R;
import com.bodkasoft.textwriter.databinding.ActivityMainBinding;
import com.bodkasoft.textwriter.fragment.InputFragment;
import com.bodkasoft.textwriter.fragment.OutputFragment;
import com.bodkasoft.textwriter.viewmodel.TextViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TextViewModel.class);

        InputFragment inputFragment = new InputFragment();
        OutputFragment outputFragment = new OutputFragment();

        setupFragment(inputFragment, R.id.controlsLayoutFrame);
        setupFragment(outputFragment, R.id.outputLayoutFrame);

        setupObserver();
        setupListeners();
    }

    private void setupListeners() {
        binding.viewTextsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TextListActivity.class);
            startActivity(intent);
        });
    }

    private void setupFragment(Fragment fragment, int id) {
        Fragment existingFragment = getSupportFragmentManager().findFragmentById(id);

        if (existingFragment == null){
            getSupportFragmentManager().beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
        }
    }

    private void setupObserver() {
        viewModel.getSnackBarMessage().observe(this, message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
                viewModel.clearSnackBarMessage();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}