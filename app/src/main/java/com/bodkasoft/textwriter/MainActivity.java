package com.bodkasoft.textwriter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bodkasoft.textwriter.databinding.ActivityMainBinding;
import com.bodkasoft.textwriter.fragment.InputFragment;
import com.bodkasoft.textwriter.fragment.OutputFragment;
import com.bodkasoft.textwriter.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

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

        InputFragment inputFragment = new InputFragment();
        OutputFragment outputFragment = new OutputFragment();

        setupFragment(inputFragment, R.id.controlsLayoutFrame);
        setupFragment(outputFragment, R.id.outputLayoutFrame);

        setupObserver();
    }

    private void setupFragment(Fragment fragment, int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setupObserver() {
        viewModel.getSnackBarMessage().observe(this, message -> {
            if (message != null) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                viewModel.clearSnackBarMessage();
            }
        });
    }
}