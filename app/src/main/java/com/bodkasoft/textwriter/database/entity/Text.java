package com.bodkasoft.textwriter.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "text_data")
public class Text {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "size")
    private int textSize;

    public Text(String text, int textSize) {
        this.text = text;
        this.textSize = textSize;
    }

    public String getText() {
        return text;
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
