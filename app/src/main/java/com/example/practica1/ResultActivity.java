package com.example.practica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "score";
    public static final String EXTRA_MAX_SCORE = "max_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        int maxScore = getIntent().getIntExtra(EXTRA_MAX_SCORE, 0);

        TextView tvFinalScore = findViewById(R.id.tvFinalScore);
        tvFinalScore.setText(getString(R.string.result_score) + ": " + score + "/" + maxScore);
    }

    public void onNewGameClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}