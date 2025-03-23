package com.bodkasoft.textwriter.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bodkasoft.textwriter.database.entity.Text;
import com.bodkasoft.textwriter.database.repository.TextRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextViewModel extends AndroidViewModel {
    private final TextRepository repository;
    private final LiveData<List<Text>> allTexts;
    private final MutableLiveData<String> inputText = new MutableLiveData<>("");
    private final MutableLiveData<String> resultText = new MutableLiveData<>("");
    private final MutableLiveData<Integer> selectedTextSize = new MutableLiveData<>(-1);
    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>(null);

    public TextViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TextRepository(application);
        this.allTexts = repository.getAllTexts();
    }

    public void clearSnackBarMessage() {
        snackBarMessage.setValue(null);
    }

    public void onButtonOkClicked(String inputText, int selectedRadioId) {
        if (inputText.isBlank() || selectedRadioId == -1) {
            snackBarMessage.setValue("Fill in all the fields");
            return;
        }
        resultText.setValue(inputText);
        this.inputText.setValue(inputText);

        Text newText = new Text(inputText, selectedTextSize.getValue());
        insertText(newText);
    }

    private void insertText(Text text) {
        try {
            if (repository.saveText(text).get()) {
                Log.i("INSERT", "Text: [" + text.getTextTitle() + "] successfully saved");
                snackBarMessage.setValue("Text saved successfully :)");
            } else {
                snackBarMessage.setValue("Something went wrong, try again");
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onCancelButtonClick() {
        resultText.setValue("");
        inputText.setValue("");
        selectedTextSize.setValue(-1);
    }

    public void onTextSizeSelected(String textSizeStr) {
        int textSize = extractTextSize(textSizeStr);
        this.selectedTextSize.setValue(textSize);
    }

    private int extractTextSize(String textSizeStr) {
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(textSizeStr);
        return matcher.find() ? Integer.parseInt(matcher.group()) : 14;
    }

    public void deleteText(int position) {
        List<Text> currentTexts = allTexts.getValue();
        if (currentTexts != null && position >= 0 && position < currentTexts.size()) {
            Text textToDelete = currentTexts.get(position);
            repository.delete(textToDelete);
        }
    }

    public LiveData<List<Text>> getAllTexts() {return allTexts;}
    public LiveData<String> getInputText() {return inputText;}
    public LiveData<String> getResultText() {return resultText;}
    public LiveData<Integer> getSelectedTextSize() {return selectedTextSize;}
    public LiveData<String> getSnackBarMessage() {return snackBarMessage;}
}
