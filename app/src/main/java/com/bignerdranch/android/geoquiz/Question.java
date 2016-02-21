package com.bignerdranch.android.geoquiz;

/**
 * Created by MCS on 2/15/2016.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mDidCheat;


    public Question (int textResId, boolean answerTrue, boolean DidCheat) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mDidCheat = DidCheat;



    }


    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isDidCheat() {
        return mDidCheat;
    }

    public void setDidCheat(boolean didCheat) {
        mDidCheat = didCheat;
    }


}
