package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizManager {
    static Connection con;

    static {
        try {
            con = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public QuizManager() throws SQLException, ClassNotFoundException {
    }

    public static String getQuizName(String quiz_id) throws SQLException {

        String query = "Select quiz_name from quizzes WHERE quiz_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, quiz_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            return rs.getString(1);
        }
        return "";
    }
    public static String getQuizCreationDate(String quiz_id) throws SQLException {
        String query = "Select creation_date from quizzes WHERE quiz_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, quiz_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            return rs.getString(1);
        }
        return "";
    }

    public static void removeQuiz(int quiz_id) throws SQLException {
        PreparedStatement st = null;
        try {
            con.setAutoCommit(false);
            String[] queries = {
                    "DELETE FROM history WHERE quiz_id = ?",
                    "DELETE FROM quizChallenges WHERE quiz_id = ?",
                    "DELETE FROM questionResponseAnswers WHERE quiz_id = ?",
                    "DELETE FROM questionFillInTheBlankAnswers WHERE quiz_id = ?",
                    "DELETE FROM questionMultipleChoiceResponseAnswers WHERE quiz_id = ?",
                    "DELETE FROM questionPictureResponseAnswers WHERE quiz_id = ?",
                    "DELETE FROM questionResponse WHERE quiz_id = ?",
                    "DELETE FROM questionFillInTheBlank WHERE quiz_id = ?",
                    "DELETE FROM questionMultipleChoice WHERE quiz_id = ?",
                    "DELETE FROM questionPictureResponse WHERE quiz_id = ?",
                    "DELETE FROM questions WHERE quiz_id = ?",
                    "DELETE FROM quizzes WHERE quiz_id = ?"
            };
            for (String query : queries) {
                st = con.prepareStatement(query);
                st.setInt(1, quiz_id);
                st.executeUpdate();
                st.close();
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        }
    }


    public static void removeQuizHistory(int quiz_id) throws SQLException {
        String query = "DELETE FROM history WHERE quiz_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, quiz_id);
        st.executeUpdate();
    }
}
