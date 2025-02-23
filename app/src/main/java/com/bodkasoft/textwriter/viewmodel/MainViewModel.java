package com.bodkasoft.textwriter.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> inputText = new MutableLiveData<>("");
    private final MutableLiveData<String> resultText = new MutableLiveData<>("");
    private final MutableLiveData<Integer> selectedTextSize = new MutableLiveData<>(-1);
    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>(null);

    public void clearSnackBarMessage() {
        snackBarMessage.setValue(null);
    }

    public void onButtonOkClicked(String inputText, int selectedRadioId) {
        if (inputText.isBlank() || selectedRadioId == -1) {
            snackBarMessage.setValue("Fill in all the fields");
            return;
        }
        resultText.setValue(inputText);
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

    public LiveData<String> getInputText() {
        return inputText;
    }

    public LiveData<String> getResultText() {
        return resultText;
    }

    public LiveData<Integer> getSelectedTextSize() {
        return selectedTextSize;
    }

    public LiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }
}
