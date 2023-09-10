package com.example.geoquiz;

public class Question {
    private String mText;
    private boolean mAnswerTrue;
    private boolean mAlreadyAnswered;
    private boolean mCheatedOn;

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        mAlreadyAnswered = alreadyAnswered;
    }

    public boolean isAlreadyAnswered() {
        return mAlreadyAnswered;
    }

    public String getText() {
        return mText;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(String text, boolean answerTrue) {
        mText = text;
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
