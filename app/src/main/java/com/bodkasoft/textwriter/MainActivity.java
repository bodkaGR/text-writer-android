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

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private RadioGroup textSizeRadioGroup;
    private Button buttonOkView, buttonCancelView;
    private EditText textInputView;
    private TextView resultTextView;
    private String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Retrieving all View components from Activity
        initializeViews();

        setupButtonListeners();

        setupRadioGroupListener();
    }

    private void initializeViews() {
        buttonOkView = findViewById(R.id.buttonOkView);
        buttonCancelView = findViewById(R.id.buttonCancelView);
        textInputView = findViewById(R.id.textInputView);
        resultTextView = findViewById(R.id.resultTextView);
        textSizeRadioGroup = findViewById(R.id.textSizeRadioGroup);
    }

    private void setupButtonListeners() {
        Map<Button, View.OnClickListener> buttonConfigurator = Map.of(
                // Add action listener for "OK" button
                buttonOkView, (view) -> {
                    resultText = textInputView.getText().toString();
                    resultTextView.setText(resultText);
                },
                // Add action listener for "Cancel" button
                buttonCancelView, (view) -> {
                    resultTextView.setText("");
                    textInputView.setText("");
                }
        );

        buttonConfigurator.forEach(Button::setOnClickListener);
    }

    private void setupRadioGroupListener() {
        textSizeRadioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton textSizeRadioButton = findViewById(checkedId);
            int textSize = extractTextSize(textSizeRadioButton.getText().toString());
            resultTextView.setTextSize(textSize);
            Log.d("RADIO", "Selected text size: " + textSizeRadioButton.getText().toString());
            Snackbar.make(findViewById(android.R.id.content), "Selected text size: " + textSizeRadioButton.getText().toString(), Snackbar.LENGTH_SHORT).show();
        }));
    }

    private static int extractTextSize(String str) {
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
    }
}