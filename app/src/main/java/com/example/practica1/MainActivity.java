package com.example.practica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private static final String STATE_IMAGE = "selected_image";
    private static final String STATE_DIALOG = "wrong_dialog_showing";

    private List<Question> questions;
    private int currentIndex;
    private int score;
    private int selectedImage = -1;
    private boolean wrongDialogShowing;

    private TextView tvProgress;
    private TextView tvScore;
    private TextView tvQuestion;
    private ImageView ivQuestion;
    private RadioGroup rgOptions;
    private RadioButton[] radioButtons;
    private ListView lvOptions;
    private View glImages;
    private ImageButton[] imageButtons;
    private Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvProgress = findViewById(R.id.tvProgress);
        tvScore = findViewById(R.id.tvScore);
        tvQuestion = findViewById(R.id.tvQuestion);
        ivQuestion = findViewById(R.id.ivQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        lvOptions = findViewById(R.id.lvOptions);
        glImages = findViewById(R.id.glImages);
        btnCheck = findViewById(R.id.btnCheck);

        radioButtons = new RadioButton[]{
                findViewById(R.id.rbOption1),
                findViewById(R.id.rbOption2),
                findViewById(R.id.rbOption3),
                findViewById(R.id.rbOption4)
        };
        imageButtons = new ImageButton[]{
                findViewById(R.id.ibOption1),
                findViewById(R.id.ibOption2),
                findViewById(R.id.ibOption3),
                findViewById(R.id.ibOption4)
        };

        for (int i = 0; i < imageButtons.length; i++) {
            final int position = i;
            imageButtons[i].setOnClickListener(v -> selectImage(position));
        }
        btnCheck.setOnClickListener(v -> checkAnswer());

        questions = QuestionBank.getQuestions();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(STATE_INDEX);
            score = savedInstanceState.getInt(STATE_SCORE);
            wrongDialogShowing = savedInstanceState.getBoolean(STATE_DIALOG);
        }

        showQuestion();

        if (savedInstanceState != null) {
            int savedImage = savedInstanceState.getInt(STATE_IMAGE, -1);
            if (savedImage != -1) {
                selectImage(savedImage);
            }
        }
        if (wrongDialogShowing) {
            showWrongDialog();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_INDEX, currentIndex);
        outState.putInt(STATE_SCORE, score);
        outState.putInt(STATE_IMAGE, selectedImage);
        outState.putBoolean(STATE_DIALOG, wrongDialogShowing);
    }

    private void showQuestion() {
        Question question = questions.get(currentIndex);
        tvProgress.setText(getString(R.string.progress, currentIndex + 1, questions.size()));
        tvScore.setText(getString(R.string.score, score));
        tvQuestion.setText(question.getText());

        if (question.hasImage()) {
            ivQuestion.setImageResource(question.getImage());
            ivQuestion.setVisibility(View.VISIBLE);
        } else {
            ivQuestion.setVisibility(View.GONE);
        }

        selectedImage = -1;
        rgOptions.clearCheck();
        rgOptions.setVisibility(View.GONE);
        lvOptions.setVisibility(View.GONE);
        glImages.setVisibility(View.GONE);

        switch (question.getControlType()) {
            case LIST_VIEW:
                lvOptions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, question.getTextOptions()));
                lvOptions.clearChoices();
                lvOptions.setVisibility(View.VISIBLE);
                break;
            case IMAGE_BUTTONS:
                for (int i = 0; i < imageButtons.length; i++) {
                    imageButtons[i].setImageResource(question.getImageOptions()[i]);
                    imageButtons[i].setSelected(false);
                }
                glImages.setVisibility(View.VISIBLE);
                break;
            default:
                for (int i = 0; i < radioButtons.length; i++) {
                    radioButtons[i].setText(question.getTextOptions()[i]);
                }
                rgOptions.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void selectImage(int position) {
        selectedImage = position;
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setSelected(i == position);
        }
    }

    private int getSelection() {
        switch (questions.get(currentIndex).getControlType()) {
            case LIST_VIEW:
                return lvOptions.getCheckedItemPosition();
            case IMAGE_BUTTONS:
                return selectedImage;
            default:
                int checkedId = rgOptions.getCheckedRadioButtonId();
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].getId() == checkedId) {
                        return i;
                    }
                }
                return -1;
        }
    }

    private void checkAnswer() {
        int selection = getSelection();
        if (selection == -1) {
            Toast.makeText(this, R.string.no_selection, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selection == questions.get(currentIndex).getCorrectIndex()) {
            score += CORRECT_POINTS;
            Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
            nextQuestion();
        } else {
            score -= WRONG_POINTS;
            showWrongDialog();
        }
    }

    private void showWrongDialog() {
        wrongDialogShowing = true;
        new AlertDialog.Builder(this)
                .setTitle(R.string.wrong_title)
                .setMessage(R.string.wrong_message)
                .setCancelable(false)
                .setPositiveButton(R.string.continue_game, (dialog, which) -> {
                    wrongDialogShowing = false;
                    nextQuestion();
                })
                .setNegativeButton(R.string.restart, (dialog, which) -> {
                    wrongDialogShowing = false;
                    restart();
                })
                .show();
    }

    private void nextQuestion() {
        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            finishGame();
        }
    }

    private void restart() {
        currentIndex = 0;
        score = 0;
        showQuestion();
    }

    private void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_SCORE, score);
        intent.putExtra(ResultActivity.EXTRA_MAX_SCORE, questions.size() * CORRECT_POINTS);
        startActivity(intent);
        finish();
    }
}