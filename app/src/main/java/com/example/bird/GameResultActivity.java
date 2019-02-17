package com.example.bird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResultActivity extends AppCompatActivity {

    TextView scoreView;
    Button startAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        scoreView = findViewById(R.id.score);
        startAgainButton = findViewById(R.id.start_again);

        scoreView.setText(String.valueOf(getIntent().getExtras().getInt("score")));

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameResultActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
