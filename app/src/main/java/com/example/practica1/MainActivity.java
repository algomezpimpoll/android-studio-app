package com.example.practica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CORRECT_POINTS = 3;
    private static final int WRONG_POINTS = 2;

    private static final String STATE_INDEX = "current_index";
    private static final String STATE_SCORE = "score";
    private static final String STATE_WRONG = "wrong_screen_showing";

    private List<Question> questions;
    private int currentIndex;
    private int score;
    private boolean wrongScreenShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        questions = QuestionBank.getQuestions();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(STATE_INDEX);
            score = savedInstanceState.getInt(STATE_SCORE);
            wrongScreenShowing = savedInstanceState.getBoolean(STATE_WRONG);
        }

        if (wrongScreenShowing) {
            showWrongScreen();
        } else {
            showQuestion();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_INDEX, currentIndex);
        outState.putInt(STATE_SCORE, score);
        outState.putBoolean(STATE_WRONG, wrongScreenShowing);
    }

    private void setLayout(int layout) {
        setContentView(layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showQuestion() {
        Question question = questions.get(currentIndex);
        setLayout(question.getLayout());

        TextView tvProgress = findViewById(R.id.tvProgress);
        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress.setText(getString(R.string.progress) + " " + (currentIndex + 1) + "/" + questions.size());
        tvScore.setText(getString(R.string.score) + ": " + score);
        tvQuestion.setText(question.getText());

        if (question.hasImage()) {
            ImageView ivQuestion = findViewById(R.id.ivQuestion);
            ivQuestion.setImageResource(question.getImage());
        }

        switch (question.getControlType()) {
            case LIST_VIEW:
                ListView lvOptions = findViewById(R.id.lvOptions);
                lvOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, question.getTextOptions()));
                lvOptions.setOnItemClickListener((parent, view, position, id) -> checkAnswer(position));
                break;
            case SPINNER:
                Spinner spOptions = findViewById(R.id.spOptions);
                String[] spinnerItems = new String[question.getTextOptions().length + 1];
                spinnerItems[0] = getString(R.string.select_option);
                for (int i = 0; i < question.getTextOptions().length; i++) {
                    spinnerItems[i + 1] = question.getTextOptions()[i];
                }
                spOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems));
                spOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            checkAnswer(position - 1);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case IMAGE_BUTTONS:
                int[] imageButtonIds = {R.id.ibOption1, R.id.ibOption2, R.id.ibOption3, R.id.ibOption4};
                for (int i = 0; i < imageButtonIds.length; i++) {
                    ImageButton imageButton = findViewById(imageButtonIds[i]);
                    imageButton.setImageResource(question.getImageOptions()[i]);
                }
                break;
            default:
                int[] radioButtonIds = {R.id.rbOption1, R.id.rbOption2, R.id.rbOption3, R.id.rbOption4};
                for (int i = 0; i < radioButtonIds.length; i++) {
                    RadioButton radioButton = findViewById(radioButtonIds[i]);
                    radioButton.setText(question.getTextOptions()[i]);
                }
                break;
        }
    }

    private void showWrongScreen() {
        wrongScreenShowing = true;
        setLayout(R.layout.activity_main_wrong);
    }

    public void onOptionClicked(View view) {
        int id = view.getId();
        if (id == R.id.rbOption1 || id == R.id.ibOption1) {
            checkAnswer(0);
        } else if (id == R.id.rbOption2 || id == R.id.ibOption2) {
            checkAnswer(1);
        } else if (id == R.id.rbOption3 || id == R.id.ibOption3) {
            checkAnswer(2);
        } else if (id == R.id.rbOption4 || id == R.id.ibOption4) {
            checkAnswer(3);
        }
    }

    public void onContinueClicked(View view) {
        wrongScreenShowing = false;
        nextQuestion();
    }

    public void onRestartClicked(View view) {
        wrongScreenShowing = false;
        currentIndex = 0;
        score = 0;
        showQuestion();
    }

    private void checkAnswer(int selection) {
        if (selection == questions.get(currentIndex).getCorrectIndex()) {
            score += CORRECT_POINTS;
            Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
            nextQuestion();
        } else {
            score -= WRONG_POINTS;
            showWrongScreen();
        }
    }

    private void nextQuestion() {
        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            finishGame();
        }
    }

    private void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_SCORE, score);
        intent.putExtra(ResultActivity.EXTRA_MAX_SCORE, questions.size() * CORRECT_POINTS);
        startActivity(intent);
        finish();
    }
}