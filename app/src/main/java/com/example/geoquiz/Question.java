package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAlreadyAnswered;
    private boolean mCheatedOn;

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        mAlreadyAnswered = alreadyAnswered;
    }

    public boolean isAlreadyAnswered() {
        return mAlreadyAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAlreadyAnswered = false;
    }

    public boolean isCheatedOn() {
        return mCheatedOn;
    }

    public void setCheatedOn(boolean cheatedOn) {
        mCheatedOn = cheatedOn;
    }

}
