package com.example.covid_19vaccination;

public class YesNo {
    private int mQuestionID;
    private boolean mAnswer;

    public YesNo(int questionResourceID, boolean YesOrNo) {
        mQuestionID = questionResourceID;
        mAnswer = YesOrNo;
    }
    public int getQuestionID() {
        return mQuestionID;
    }

    public void setQuestionID(int questionID) {
        mQuestionID = questionID;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
