import java.util.ArrayList;

public class Quiz {
    private static final int QUESTION_RESPONSE = 1;
    private static final int FILL_IN_THE_BLANK = 2;
    private static final int MULTIPLE_CHOICE = 3;
    private static final int PICTURE_RESPONSE = 4;
    private String description;
    private int type;
    private ArrayList<Object> questions;
    private ArrayList<Object> correctAnswers;
    private ArrayList<Object> incorrectMultipleChoices;

    public Quiz(int type, String desc) {
        this.type = type;
        this.description = desc;
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        incorrectMultipleChoices = new ArrayList<>();
    }
    public void insertQuestion(Object question, Object correctAnswers, Object incorrectChoices) {
        questions.add(question);
        this.correctAnswers.add(correctAnswers);
        incorrectMultipleChoices.add(incorrectChoices);
    }
    public void insertQuestion(Object question, Object correctAnswers) {
        questions.add(question);
        this.correctAnswers.add(correctAnswers);
    }
    public Object loadQuestion(int ind) {
        Object res = questions.get(ind);
        return res;
    }
    public Object loadAnswers(int ind) {
        Object res = correctAnswers.get(ind);
        return res;
    }
    public Object loadMultipleChoices(int ind) {
        Object res = incorrectMultipleChoices.get(ind);
        return res;
    }

}
