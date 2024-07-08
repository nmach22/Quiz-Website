package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class SearchManager {

    public String quizName;
    public int quiz_id;
    public SearchManager(String quizName, int quiz_id){
        this.quizName = quizName;
        this.quiz_id = quiz_id;
    }

    public static ArrayList<SearchManager> searchQuizzes(String search) throws SQLException, ClassNotFoundException {
        ArrayList<SearchManager> res = new ArrayList<>();
        String query = "SELECT * FROM quizzes WHERE quiz_name = ?";

        Connection con = DataBaseConnection.getConnection();
        PreparedStatement st  = con.prepareStatement(query);
        st.setString(1, search);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            res.add(new SearchManager(rs.getString("quiz_name"), rs.getInt("quiz_id")));
        }
        return res;
    }

}
