package davidrobles.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizOverActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int EXTRA_QUIZ_SCORE = 0;

    private TextView mEndScoreText;
    private Button mRestartButton;
    private Button mShareButton;
    private int mFinaleScore;
    private String textMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        mEndScoreText = (TextView) findViewById(R.id.end_score);
        mRestartButton = (Button) findViewById(R.id.restart_button);
        mShareButton = (Button) findViewById(R.id.share_button);

        //mFinaleScore = getIntent().getIntExtra("finalScore", -1);
        mEndScoreText.setText("Score: " + getIntent().getIntExtra("finalScore", -1));

        mRestartButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.restart_button)
        {
            Intent restart = MainActivity.newIntent(this);
            startActivity(restart);
        }
        if(view.getId() == R.id.share_button)
        {
            dispatchShareScore();
        }


    }

    private void dispatchShareScore()
    {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }


    }

    public static Intent newIntent(Context ctx, int finalScore) {
        Intent ret = new Intent(ctx, QuizOverActivity.class);
        ret.putExtra("finalScore", finalScore);
        return ret;
    }
}
