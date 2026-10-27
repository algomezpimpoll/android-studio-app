package com.example.practica1;

public class Question {

    public enum ControlType {
        RADIO_GROUP,
        LIST_VIEW,
        IMAGE_BUTTONS,
        SPINNER
    }

    private final int layout;
    private final String text;
    private final int image;
    private final String[] textOptions;
    private final int[] imageOptions;
    private final int correctIndex;
    private final ControlType controlType;

    public Question(int layout, String text, int image, String[] textOptions, int[] imageOptions, int correctIndex, ControlType controlType) {
        this.layout = layout;
        this.text = text;
        this.image = image;
        this.textOptions = textOptions;
        this.imageOptions = imageOptions;
        this.correctIndex = correctIndex;
        this.controlType = controlType;
    }

    public int getLayout() {
        return layout;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }

    public String[] getTextOptions() {
        return textOptions;
    }

    public int[] getImageOptions() {
        return imageOptions;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public boolean hasImage() {
        return image != 0;
    }
}