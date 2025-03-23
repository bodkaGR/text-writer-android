package com.bodkasoft.textwriter.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bodkasoft.textwriter.database.AppDatabase;
import com.bodkasoft.textwriter.database.dao.TextDao;
import com.bodkasoft.textwriter.database.entity.Text;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TextRepository {
    private final TextDao textDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TextRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.textDao = database.textDao();
    }

    public LiveData<List<Text>> getAllTexts() {
        return textDao.getAll();
    }

    public Text getTextById(int id) {
        return textDao.getById(id);
    }

    public Future<Boolean> saveText(Text text) {
        return executorService.submit(() -> {
            try {
                return textDao.insert(text) > 0;
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public void delete(Text text) {
        executorService.submit(() -> textDao.delete(text));
    }
}
