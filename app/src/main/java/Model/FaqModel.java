package Model;

public class FaqModel {
    private String questionTitle, answerDescription;
    private boolean expandable;

    public FaqModel(String questionTitle, String answerDescription) {
        this.questionTitle = questionTitle;
        this.answerDescription = answerDescription;
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    @Override
    public String toString() {
        return "FaqModel{" +
                "questionTitle='" + questionTitle + '\'' +
                ", answerDescription='" + answerDescription + '\'' +
                '}';
    }
}
