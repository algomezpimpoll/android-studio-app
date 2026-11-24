package com.example.practica1.data;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class QuestionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String text;
    @Nullable
    private final String imageName;
    @Nullable
    private final String textOptions;
    @Nullable
    private final String imageOptions;
    private final int correctIndex;
    private final String controlType;

    public QuestionEntity(String text, @Nullable String imageName, @Nullable String textOptions, @Nullable String imageOptions, int correctIndex, String controlType) {
        this.text = text;
        this.imageName = imageName;
        this.textOptions = textOptions;
        this.imageOptions = imageOptions;
        this.correctIndex = correctIndex;
        this.controlType = controlType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    @Nullable
    public String getImageName() {
        return imageName;
    }

    @Nullable
    public String getTextOptions() {
        return textOptions;
    }

    @Nullable
    public String getImageOptions() {
        return imageOptions;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getControlType() {
        return controlType;
    }
}