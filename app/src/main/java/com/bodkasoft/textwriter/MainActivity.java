package com.bodkasoft.textwriter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonOkView, buttonCancelView;
    private EditText textInputView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Retrieving all View components from Activity
        buttonOkView = findViewById(R.id.buttonOkView);
        buttonCancelView = findViewById(R.id.buttonCancelView);
        textInputView = findViewById(R.id.textInputView);
        resultTextView = findViewById(R.id.resultTextView);

        // Add action listener on "OK" button
        buttonOkView.setOnClickListener(view -> {
            String resultText = textInputView.getText().toString();
            resultTextView.setText(resultText);
        });

        // Add action listener on "Cancel" button
        buttonCancelView.setOnClickListener(view -> {
            resultTextView.setText("");
            textInputView.setText("");
        });
    }
}