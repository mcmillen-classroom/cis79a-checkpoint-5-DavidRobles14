package davidrobles.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView mTextView;
    private TextView mScoreText;
    private String mOgScoreText;
    private EditText mEditText;


    private LinearLayout mTrueFalseContainer;
    private LinearLayout mFillTheBlankContainer;
    private LinearLayout mMultipleChoiceContainer;


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheckButton;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private Button mHintButton;
    private Button mCheatButton;
    private Button mRestartButton;


    private Question[] mQuestions;
    private int mIndex;
    private int mHintIndex;
    private int mScore;
    private boolean mCheated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheckButton = (Button) findViewById(R.id.check_button);
        mButton1 = (Button) findViewById(R.id.button_1);
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton3 = (Button) findViewById(R.id.button_3);
        mButton4 = (Button) findViewById(R.id.button_4);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setVisibility(View.GONE);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mHintButton = (Button) findViewById(R.id.hint_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mRestartButton = (Button) findViewById(R.id.restart_button);
        mRestartButton.setVisibility(View.GONE);


        mTrueFalseContainer = (LinearLayout) findViewById(R.id.true_false_container);
        mFillTheBlankContainer = (LinearLayout) findViewById(R.id.fill_the_blank_container);
        mMultipleChoiceContainer = (LinearLayout) findViewById(R.id.multiple_choice_container);

        mEditText = (EditText) findViewById(R.id.edit_text);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mHintButton.setOnClickListener(this);
        mCheatButton.setOnClickListener(this);
        mRestartButton.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.text_view);
        mScoreText = (TextView) findViewById(R.id.text_score);

        mQuestions = new Question[7];
        mIndex = 0;
        mHintIndex = 1;

        mQuestions[0] = new TrueFalseQuestion(R.string.question_1, R.string.question_1_hint, false);
        mQuestions[1] = new TrueFalseQuestion(R.string.question_2, R.string.question_2_hint, false);
        mQuestions[2] = new TrueFalseQuestion(R.string.question_3, R.string.question_3_hint, true);
        mQuestions[3] = new TrueFalseQuestion(R.string.question_4, R.string.question_4_hint, false);
        mQuestions[4] = new TrueFalseQuestion(R.string.question_5, R.string.question_5_hint, true);

        String[] q4Answers = getResources().getStringArray(R.array.question_6_answers);
        mQuestions[5] = new FillTheBlankQuestion(R.string.question_6,R.string.question_6_hint,q4Answers);

        mQuestions[6] = new MultipleChoiceQuestion(R.string.question_7, R.string.question_7_hint,R.array.question_7_answers, 2);

        mOgScoreText = "Score: " ;

        //Setup the first Question
        setupQuestion();
        mScoreText.setText(mOgScoreText);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        if(resultCode!= RESULT_OK)
        {
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT && resultData != null)
        {
            mCheated = CheatActivity.didCheat(resultData);
        }
    }


    @Override
    public void onClick(View view)
    {

        if (view.getId() == R.id.true_button)
        {
            checkAnswer(true);
        }
        else if(view.getId() == R.id.false_button)
        {
            checkAnswer(false);
        }
        else if(view.getId() == R.id.button_1)
        {
            checkAnswer(0);
        }
        else if(view.getId() == R.id.button_2)
        {
            checkAnswer(1);
        }
        else if(view.getId() == R.id.button_3)
        {
            checkAnswer(2);
        }
        else if(view.getId() == R.id.button_4)
        {
            checkAnswer(3);
        }
        else if(view.getId() == R.id.check_button)
        {
            checkAnswer(mEditText.getText().toString());
        }
        else if(view.getId() == R.id.hint_button)
        {
            Toast hintToast = Toast.makeText(this,mQuestions[mIndex].getHintTextResId(), Toast.LENGTH_LONG);
            hintToast.show();
        }
        else if(view.getId() == R.id.prev_button) {
            mIndex--;
            mHintIndex--;

            if(mQuestions[mIndex].isTrueFalseQuestion())
            {
                mTrueFalseContainer.setVisibility(View.VISIBLE);
                mFillTheBlankContainer.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
            }
            else if(mQuestions[mIndex].isFillTheBlankQuestion())
            {
                mTrueFalseContainer.setVisibility(View.GONE);
                mFillTheBlankContainer.setVisibility(View.VISIBLE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
            }
            else if(mQuestions[mIndex].isMultipleChoiceQuestion())
            {
                mTrueFalseContainer.setVisibility(View.GONE);
                mFillTheBlankContainer.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.VISIBLE);
            }

            if(mIndex >= 0)
            {
                mTextView.setText(mQuestions[mIndex].getTextResId());
                if(mIndex == 0)
                {
                    mPrevButton.setVisibility(View.GONE);
                }
            }
            else if(mIndex < 5)
            {
                mCheckButton.setVisibility(View.GONE);
//                mTrueFalseContainer.setVisibility(View.VISIBLE);
//                mFillTheBlankContainer.setVisibility(View.GONE);
//                mMultipleChoiceContainer.setVisibility(View.GONE);
            }
        }
        else if(view.getId() == R.id.next_button)
        {
            mIndex++;
            mHintIndex++;
            mPrevButton.setVisibility(View.VISIBLE);

            if(mIndex < mQuestions.length)
            {
                //mTextView.setText(mQuestions[mIndex].getTextResId());
                setupQuestion();
                mScoreText.setText(mOgScoreText + mScore);
            }
            else
            {
                mTextView.setText("You are done! Your Score Was " + mScore);
                mScoreText.setText(mOgScoreText + mScore);
                mRestartButton.setVisibility(View.VISIBLE);
                mTrueButton.setVisibility(View.GONE);
                mFalseButton.setVisibility(View.GONE);
                mPrevButton.setVisibility(View.GONE);
                mNextButton.setVisibility(View.GONE);
                mHintButton.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
            }
            //DO IF STATEMENT HERE:
        }
        else if(view.getId() == R.id.restart_button)
        {
            mIndex = 0;
            mHintIndex = 1;
            mScore = 0;
            mTextView.setText(mQuestions[mIndex].getTextResId());
            mScoreText.setText(mOgScoreText);
            mRestartButton.setVisibility(View.GONE);
            mTrueButton.setVisibility(View.VISIBLE);
            mFalseButton.setVisibility(View.VISIBLE);
            mPrevButton.setVisibility(View.GONE);
            mNextButton.setVisibility(View.VISIBLE);
            mHintButton.setVisibility(View.VISIBLE);

        }
        else if(view.getId() == R.id.cheat_button)
        {
            //TODO: launch CheatActivity
            Intent cheatIntent = CheatActivity.newIntent(this, mQuestions[mIndex]);
            startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);
        }
    }


    public void setupQuestion()
    {
        mTextView.setText(mQuestions[mIndex].getTextResId());

        if(mQuestions[mIndex].isTrueFalseQuestion())
        {
            mTrueFalseContainer.setVisibility(View.VISIBLE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if(mQuestions[mIndex].isFillTheBlankQuestion())
        {
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.VISIBLE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if(mQuestions[mIndex].isMultipleChoiceQuestion())
        {
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.VISIBLE);

            int optionsResId = ((MultipleChoiceQuestion) mQuestions[mIndex]).getOptionsResId();
            String[] options = getResources().getStringArray(optionsResId);

            //TODO: use options array to set the text of each MC button
            // Index 0 is for button 1 ... index 3 is for button 4
            mButton1.setText(options[0]);
            mButton2.setText(options[1]);
            mButton3.setText(options[2]);
            mButton4.setText(options[3]);

        }
    }


    public boolean checkAnswer(boolean userInput)
    {
        if(mCheated)
        {
           Toast.makeText(this, R.string.cheat_shame, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(mQuestions[mIndex].checkAnswer(userInput))
        {
            mScore += 50;
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return true;
        }
        else
        {
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return false;
        }
    }


    public boolean checkAnswer(String userInput)
    {

        if(mCheated)
        {
            Toast.makeText(this, R.string.cheat_shame, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(mQuestions[mIndex].checkAnswer(userInput))
        {
            mScore += 50;
            mCheckButton.setBackgroundColor(Color.GREEN);
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return true;
        }
        else
        {
            mCheckButton.setBackgroundColor(Color.RED);
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return false;
        }
    }


    public boolean checkAnswer(int userInput)
    {
        if(mQuestions[mIndex].checkAnswer(userInput))
        {
            mScore += 50;
            if(mIndex == 6)
            {
                mButton3.setBackgroundColor(Color.GREEN);
            }
            Toast myToast = Toast.makeText(this, "You are correct", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return true;
        }
        else
        {
            if(mIndex == 6)
            {
                mButton1.setBackgroundColor(Color.RED);
                mButton2.setBackgroundColor(Color.RED);
                mButton3.setBackgroundColor(Color.GREEN);
                mButton4.setBackgroundColor(Color.RED);
            }
            Toast myToast = Toast.makeText(this, "You are incorrect", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP | Gravity.LEFT, 325, 150);
            myToast.show();
            return false;
        }
    }
}
