package davidrobles.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class QuizOverActivity extends AppCompatActivity {

    public static final int EXTRA_QUIZ_SCORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);
    }
}
