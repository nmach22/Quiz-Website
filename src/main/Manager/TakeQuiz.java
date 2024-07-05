package main.Manager;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeQuiz {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private List<Map<String, Object>> questions;

    public TakeQuiz(String quizId) throws SQLException, ClassNotFoundException {
        con = DataBaseConnection.getConnection();
        String query = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY question_id asc";
        ps = con.prepareStatement(query);
        ps.setInt(1, Integer.parseInt(quizId));
        rs = ps.executeQuery();

        questions = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> question = new HashMap<>();
            question.put("question_id", rs.getInt("question_id"));
            question.put("question_type", rs.getString("question_type"));
            questions.add(question);
        }
    }

    public List<Map<String, Object>> getQuestions() {
        return questions;
    }

    public List<Map<String, Object>> fetchMultipleChoiceQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        String query = "SELECT * FROM questionMultipleChoiceResponseAnswers WHERE question_id = ?";
        List<Map<String, Object>> multipleChoice = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            if(question.get("question_type") != "questionMultipleChoice") continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            String choice = "A";
            while (rs.next()) {
                question.put(choice , rs.getString("answer"));
                question.put("question", rs.getString("question"));
                if(rs.getBoolean("is_correct"))
                    question.put("correct_option", rs.getString("answer"));
                char ch = choice.charAt(0);
                ch += 1;
                choice = "" + ch;
            }
            multipleChoice.add(question);
        }
        return multipleChoice;
    }
    public List<Map<String, Object>> fetchFillInTheBlankQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        String query = "SELECT * FROM questionFillInTheBlankAnswers WHERE question_id = ?";
        List<Map<String, Object>> fillInTheBlank = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            if(question.get("question_type") != "questionFillInTheBlank") continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            String option = "option1";
            while (rs.next()) {
                question.put("question", rs.getString("question"));
                question.put(option , rs.getString("answer"));
                char ch = option.charAt(option.length() - 1);
                ch += 1;
                option = option.substring(0, option.length() - 1) + ch;
            }
            fillInTheBlank.add(question);
        }
        return fillInTheBlank;
    }
    public List<Map<String, Object>> fetchResponseQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        String query = "SELECT * FROM questionResponseAnswers WHERE question_id = ?";
        List<Map<String, Object>> responses = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            if(question.get("question_type") != "questionResponse") continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            String option = "option1";
            while (rs.next()) {
                question.put("question", rs.getString("question"));
                question.put(option , rs.getString("answer"));
                char ch = option.charAt(option.length() - 1);
                ch += 1;
                option = option.substring(0, option.length() - 1) + ch;
            }
            responses.add(question);
        }
        return responses;
    }
    public List<Map<String, Object>> fetchPictureResponseQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        String query = "SELECT * FROM questionPictureResponseAnswers WHERE question_id = ?";
        List<Map<String, Object>> pictureResponses = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            if(question.get("question_type") != "questionPictureResponse") continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            String option = "option1";
            while (rs.next()) {
                question.put("question", rs.getString("question"));
                question.put(option , rs.getString("answer"));
                char ch = option.charAt(option.length() - 1);
                ch += 1;
                option = option.substring(0, option.length() - 1) + ch;
            }
            pictureResponses.add(question);
        }
        return pictureResponses;
    }
}
