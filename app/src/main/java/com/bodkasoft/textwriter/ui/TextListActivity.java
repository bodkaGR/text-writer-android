package com.bodkasoft.textwriter.ui;

import android.os.Bundle;

import com.bodkasoft.textwriter.ui.adapter.TextAdapter;
import com.bodkasoft.textwriter.viewmodel.TextViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;


import com.bodkasoft.textwriter.databinding.ActivityTextListBinding;

import java.util.ArrayList;

public class TextListActivity extends AppCompatActivity {
    private TextViewModel viewModel;
    private TextAdapter textAdapter;
    private ActivityTextListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTextListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(TextViewModel.class);

        textAdapter = new TextAdapter(new ArrayList<>(), position -> viewModel.deleteText(position));
        binding.textsList.setAdapter(textAdapter);

        setupObservers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupObservers() {
        viewModel.getAllTexts().observe(this, texts -> {
            if (texts == null || texts.isEmpty()) {
                binding.emptyMessage.setVisibility(View.VISIBLE);
                binding.textsList.setVisibility(View.GONE);
            } else {
                binding.emptyMessage.setVisibility(View.GONE);
                binding.textsList.setVisibility(View.VISIBLE);
                textAdapter.updateDataList(texts);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}