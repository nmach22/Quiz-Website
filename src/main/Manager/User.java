package main.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class User {
    private static Connection con;

    public User() throws ClassNotFoundException, SQLException {con = DataBaseConnection.getConnection();}

    public static void setConnection(Connection connection) {
        con = connection;
    }

    public static ArrayList<String> getFriends(String name){
        ArrayList<String> friends = new ArrayList<>();
        String sql = "SELECT user2 FROM friends WHERE user1 = ? ORDER BY addDate";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString(1));
            }
        } catch (SQLException e) {throw new RuntimeException("Error retrieving friends from the database: " + e.getMessage());}
        return friends;
    }

    public static ArrayList<Pair>getRecentlyAddedQuizzes(){
        ArrayList<Pair>quizzes = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * \n" +
                    "FROM quizzes \n" +
                    "ORDER BY creation_date DESC \n" +
                    "LIMIT 10";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                quizzes.add(new Pair(rs.getString("quiz_name"), rs.getString("quiz_id")));
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
        return quizzes;
    }

    public static ArrayList<Pair>getPopularQuizzes(){
        ArrayList<Pair>quizzes = new ArrayList<>();
        try {
            Statement statement = con.createStatement();

            String sql = "SELECT q.quiz_name, h.quiz_id, COUNT(h.quiz_id) AS occurrence\n" +
                    "FROM history h\n" +
                    "JOIN quizzes q ON h.quiz_id = q.quiz_id\n" +
                    "GROUP BY h.quiz_id, q.quiz_name\n" +
                    "ORDER BY occurrence DESC\n" +
                    "LIMIT 10";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                quizzes.add(new Pair(rs.getString("quiz_name"), rs.getString("quiz_id")));
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
        return quizzes;
    }

    public static Vector<String> getCreatedQuizzes(String name){
        Vector<String> quizzes = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM quizzes WHERE author = \""+ name +"\" ORDER BY creation_date";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                quizzes.add(rs.getString("quiz_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }

    public static Vector<Vector<String>> takenQuizzesByDate(String name){
        Vector<Vector<String>> quizzes = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM history WHERE username = \""+ name +"\" ORDER BY date_taken";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Vector<String> quiz = new Vector<>();
                quiz.add(rs.getString("quiz_id"));
                quiz.add(rs.getString("score"));
                quiz.add(Integer.toString(rs.getInt("time")));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
        return quizzes;
    }

    public static void acceptChallenge(int id) throws SQLException {
        String sql = "UPDATE quizChallenges SET status = ? where challenge_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, "accepted");
        st.setInt(2, id);
        st.executeUpdate();
    }

    public static void rejectChallenge(int id) throws SQLException {
        String sql = "UPDATE quizChallenges SET status = ? where challenge_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, "rejected");
        st.setInt(2, id);
        st.executeUpdate();
    }

    public static void addFriend(String name, String friend) throws SQLException {
        String sql = "INSERT INTO friends (user1, user2) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , name);
        st.setString(2 , friend);
        st.executeUpdate();
        sql = "INSERT INTO friends (user1, user2) VALUES (?, ?)";
        st = con.prepareStatement(sql);
        st.setString(1 , friend);
        st.setString(2 , name);
        st.executeUpdate();
        sql = "DELETE FROM friendRequests where (user_from = ? AND user_to = ?)";
        st = con.prepareStatement(sql);
        st.setString(1, friend);
        st.setString(2, name);
        st.executeUpdate();
    }

    public static void rejectFriendRequest(String name, String friend) throws SQLException {
        String sql = "DELETE FROM friendRequests where (user_from = ? AND user_to = ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, friend);
        st.setString(2, name);
        st.executeUpdate();
    }

    public static void sendFriendRequest(String name, String friend) throws SQLException {
        String sql = "INSERT INTO friendRequests (user_from, user_to) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , name);
        st.setString(2 , friend);
        st.executeUpdate();
    }

    public static void removeFriend(String name, String friend) throws SQLException {
        if(getFriends(name).contains(friend)){
            String sql = "DELETE FROM friends WHERE user1 = ? AND user2 = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, friend);
            st.executeUpdate();
            st.setString(1, friend);
            st.setString(2, name);
            st.executeUpdate();
        }
    }

    public static boolean doesFriendRequestExist(String name, String friend) throws SQLException {
        String sql = "SELECT * FROM friendRequests WHERE user_from = ? AND user_to = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, name);
        st.setString(2, friend);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public static void removeFriendRequest(String name, String friend) throws SQLException {
        String sql = "DELETE FROM friendRequests WHERE user_from = ? AND user_to = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, name);
        st.setString(2, friend);
        st.executeUpdate();
    }

    public static void sendChallenge(String from, String to, int quizID, int highestScrore) throws SQLException {
        String sql = "INSERT INTO quizChallenges (user_from, user_to, quiz_id,highest_score) VALUES (?, ?, ?,?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , from);
        st.setString(2 , to);
        st.setInt(3 , quizID);
        st.setInt(4,highestScrore);
        st.executeUpdate();
    }

    public static void addAchievement(String name, String achievement) throws SQLException {
        String sql = "INSERT INTO achievements (username, achievement_type) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , name);
        st.setString(2 , achievement);
        st.executeUpdate();
    }

    public static int highestScore(int quiz_id) throws SQLException {
        int highest = 0;
        String sql = "SELECT * FROM history WHERE quiz_id = ? ORDER BY score DESC";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, quiz_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) highest = rs.getInt("score");
        return highest;
    }

    public static int getUsersHighestScoreOnQuiz(String username, int quiz_id) throws SQLException {
        String query = "SELECT score FROM history where username = ? AND quiz_id = ? order by score desc";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,username);
        st.setInt(2,quiz_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) return rs.getInt(1);
        return 0;
    }

    public static int takenQuizzesAmount(String name, int quiz_id){
        int res = 0;
        try {
            String sql = "SELECT COUNT(*) FROM history WHERE (username = ? AND quiz_id = ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, quiz_id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getInt(1);
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
        return res;
    }
    public static int getUsersScore(String user) throws SQLException {
        String query = "SELECT SUM(score)   from history where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, user);
        ResultSet rs = st.executeQuery();
        if(rs.next()) return rs.getInt(1);
        return 0;
    }



}
