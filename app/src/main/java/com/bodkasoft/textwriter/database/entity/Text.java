package com.bodkasoft.textwriter.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "text_data")
public class Text {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String textTitle;

    @ColumnInfo(name = "size")
    private int textSize;

    public Text(String textTitle, int textSize) {
        this.textTitle = textTitle;
        this.textSize = textSize;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
