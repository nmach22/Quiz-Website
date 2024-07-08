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
}
