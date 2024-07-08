package main.Manager;

import java.sql.*;
import java.util.ArrayList;

public class Notifications {

    public String from;
    public int quiz_id;
    public int score;
    public int challenge_id;

    public Notifications(String from, int quiz_id, int score, int challenge_id){
        this.from = from;
        this.quiz_id = quiz_id;
        this.score = score;
        this.challenge_id = challenge_id;
    }

    public static ArrayList<Notifications> getChallenges(String user) throws SQLException, ClassNotFoundException {
        ArrayList<Notifications> res = new ArrayList<>();
        String sql = "SELECT * FROM quizChallenges WHERE user_to = \""+ user + "\"AND status = 'pending' ORDER BY sentDate";
        Connection con = DataBaseConnection.getConnection();
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            res.add(new Notifications(rs.getString("user_from"), rs.getInt("quiz_id"), rs.getInt("highest_score"), rs.getInt("challenge_id")));
        }
        return res;
    }

    public static ArrayList<String> getFriendRequests(String name) throws SQLException, ClassNotFoundException {
        ArrayList<String> requests = new ArrayList<>();

        String sql = "SELECT * FROM friendRequests WHERE user_to = \""+ name +"\" ORDER BY requestDate";
        Connection con = DataBaseConnection.getConnection();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            requests.add(rs.getString("user_from"));
        }

        return requests;
    }
}
