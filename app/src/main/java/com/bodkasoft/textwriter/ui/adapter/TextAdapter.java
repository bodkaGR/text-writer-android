package com.bodkasoft.textwriter.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bodkasoft.textwriter.R;
import com.bodkasoft.textwriter.database.entity.Text;
import com.bodkasoft.textwriter.databinding.TextListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TextAdapter extends BaseAdapter {
    private final List<Text> texts;
    private final OnDeleteListener listener;

    public TextAdapter(List<Text> textsList, OnDeleteListener listener) {
        this.listener = listener;
        this.texts = (textsList != null) ? new ArrayList<>(textsList) : new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextListItemBinding binding;
        if (convertView == null) {
            binding = TextListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (TextListItemBinding) convertView.getTag();
        }

        Text text = texts.get(position);
        binding.textTitle.setText(text.getText());
        binding.textSize.setText(String.valueOf(text.getTextSize()));

        binding.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(position);
            }
        });

        return convertView;
    }

    public void updateDataList(List<Text> texts) {
        this.texts.clear();
        this.texts.addAll(texts);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public Object getItem(int position) {
        return texts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }
}
