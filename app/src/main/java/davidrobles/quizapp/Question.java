package davidrobles.quizapp;

import android.content.Context;

public class Question
{
    private int mTextResId;
    private int mHintTextResId;

    public Question(int textResId, int hintTextResId)
    {
        mTextResId = textResId;
        mHintTextResId = hintTextResId;
    }

    public int getTextResId()
    {
        return mTextResId;
    }

    public void setTextResId(int textResId)
    {
        mTextResId = textResId;
    }

    public String getText(Context ctx)
    {
        return ctx.getString(mTextResId);
    }

    // stub
    public String getAnswerText(Context ctx)
    {
        return "";
    }

    public int getHintTextResId() {
        return mHintTextResId;
    }

    public void setHintTextResId(int hintTextResId)
    {
        mHintTextResId = hintTextResId;
    }

    //Stub method - intentionally does nothing
    //only applies to True false Questions
    public boolean checkAnswer(boolean boolResponse)
    {
        return false;
    }

    //Stub method
    //only applies to fill the blank question
    public boolean checkAnswer(String userAnswer)
    {
        return false;
    }

    //Stub
    public boolean checkAnswer(int ans)
    {
        return false;
    }

    // Stub
    public boolean isTrueFalseQuestion()
    {
        return false;
    }

    // Stub
    public boolean isFillTheBlankQuestion()
    {
        return false;
    }

    // Stub
    public boolean isMultipleChoiceQuestion()
    {
        return false;
    }

}

