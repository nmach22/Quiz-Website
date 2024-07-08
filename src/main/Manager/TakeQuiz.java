package main.Manager;

import java.sql.*;
import java.util.*;

public class TakeQuiz {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private List<Map<String, Object>> questions;
    private String quizId;

    public TakeQuiz(String quizId) throws SQLException, ClassNotFoundException {
        con = DataBaseConnection.getConnection();
        this.quizId = quizId;
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
        List<Map<String, Object>> multipleChoice = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            String query = "SELECT * FROM questionMultipleChoiceResponseAnswers WHERE question_id = ?";
            if(!question.get("question_type").equals("questionMultipleChoice")) continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            Set<String> choices = new HashSet<>();
            Set<String> correctOptions = new HashSet<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();

            while (rs.next()) {
                choices.add(rs.getString(numColumns - 1));
                if(rs.getInt(numColumns) == 1)
                    correctOptions.add(rs.getString(numColumns - 1));
            }
            List<String> choicesList = new ArrayList<>(choices);
            List<String> correctoptions = new ArrayList<>(correctOptions);
            Collections.shuffle(choicesList);
            question.put("multipleChoices", new HashSet<>(choicesList));
            question.put("correct_answers", new HashSet<>(correctoptions));
            query = "SELECT * FROM questionMultipleChoice WHERE question_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            if(rs.next()) {
                question.put("question", rs.getString("question"));
            }
            question.put("index", multipleChoice.size());
            multipleChoice.add(new HashMap<>(question));
        }
        return multipleChoice;
    }
    public List<Map<String, Object>> fetchFillInTheBlankQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> fillInTheBlank = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            String query = "SELECT * FROM questionFillInTheBlankAnswers WHERE question_id = ?";
            if(!question.get("question_type").equals("questionFillInTheBlank")) continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            Set<String> options = new HashSet<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (rs.next()) {
                options.add(rs.getString(numColumns));
            }
            question.put("correct_answers", options);
            query = "SELECT * FROM questionFillInTheBlank WHERE question_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            if(rs.next()) {
                question.put("question", rs.getString("question"));
            }
            question.put("index", fillInTheBlank.size());
            fillInTheBlank.add(new HashMap<>(question));
        }
        return fillInTheBlank;
    }
    public List<Map<String, Object>> fetchResponseQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> responses = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            String query = "SELECT * FROM questionResponseAnswers WHERE question_id = ?";
            if(!question.get("question_type").equals("questionResponse")) continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            Set<String> options = new HashSet<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (rs.next()) {
                options.add(rs.getString(numColumns));
            }
            question.put("correct_answers", options);
            query = "SELECT * FROM questionResponse WHERE question_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();;
            if(rs.next()) {
                question.put("question", rs.getString("question"));
            }
            question.put("index", responses.size());
            responses.add(new HashMap<>(question));
        }
        return responses;
    }
    public List<Map<String, Object>> fetchPictureResponseQuestions() throws SQLException {
        if (questions.isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> pictureResponses = new ArrayList<>();
        for (Map<String, Object> question : questions) {
            String query = "SELECT * FROM questionPictureResponseAnswers WHERE question_id = ?";
            if(!question.get("question_type").equals("questionPictureResponse")) continue;
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            Set<String> options = new HashSet<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (rs.next()) {
                options.add(rs.getString(numColumns));
            }
            question.put("correct_answers", options);
            query = "SELECT * FROM questionPictureResponse WHERE question_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, (int) question.get("question_id"));
            rs = ps.executeQuery();
            if(rs.next()) {
                question.put("picture_link", rs.getString("picture_link"));
                question.put("question", rs.getString("question"));
            }
            question.put("index", pictureResponses.size());
            pictureResponses.add(new HashMap<>(question));
        }
        return pictureResponses;
    }
    public Map<String, Object> fetchSettings() throws SQLException {
        Map<String, Object> settings = new HashMap<>();
        String query = "SELECT * FROM quizzes WHERE quiz_id = ? ";
        ps = con.prepareStatement(query);
        ps.setInt(1, Integer.parseInt(quizId));
        rs = ps.executeQuery();
        while (rs.next()) {
            settings.put("is_random", rs.getInt("is_random"));
            settings.put("one_page", rs.getInt("one_page"));
            settings.put("immediate_correction", rs.getInt("immediate_correction"));
            settings.put("practice_mode", rs.getInt("practice_mode"));
            settings.put("duration", rs.getInt("duration"));
        }
        return settings;
    }
}
