package main.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class User {
    private static Connection con;

    public User() throws ClassNotFoundException, SQLException {
        con = DataBaseConnection.getConnection();
    }

    public static ArrayList<String> getFriends(String name){
        ArrayList<String> friends = new ArrayList<>();

        try {
            if (con == null || con.isClosed()) {
                throw new SQLException("Database connection is not initialized or is closed.");
            }
            String sql = "SELECT user2 FROM friends WHERE user1 = \""+ name +"\" ORDER BY addDate";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                friends.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving friends from the database: " + e.getMessage());
        }
        return friends;
    }

    public static Vector<String> getSentFriendRequests(String name){
        Vector<String> requests = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM friendRequests WHERE user_from = \""+ name +"\" ORDER BY requestDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                requests.add(rs.getString("user_to"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public static Vector<String> getFriendRequests(String name){
        Vector<String> requests = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM friendRequests WHERE user_to = \""+ name +"\" ORDER BY requestDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                requests.add(rs.getString("user_from"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public static ArrayList<String> getPopularQuizzes(){
        ArrayList<String> quizzes = new ArrayList<>();
        try {
            Statement statement = con.createStatement();

            String sql = "SELECT quiz_id, COUNT(quiz_id) AS occurrence " +
                    "            FROM history " +
                    "            GROUP BY quiz_id " +
                    "            ORDER BY occurrence DESC " +
                    "            LIMIT 10";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                quizzes.add(rs.getString("quiz_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(quizzes);
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
                quiz.add(rs.getString("time"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }

    public static Vector<Vector<String>> takenQuizzesByTime(String name){
        Vector<Vector<String>> quizzes = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM history WHERE username = \""+ name +"\" ORDER BY time";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Vector<String> quiz = new Vector<>();
                quiz.add(rs.getString("quiz_id"));
                quiz.add(rs.getString("score"));
                quiz.add(rs.getString("time"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }

    public static Vector<Vector<String>> takenQuizzesByScore(String name){
        Vector<Vector<String>> quizzes = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM history WHERE username = \""+ name +"\" ORDER BY score";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Vector<String> quiz = new Vector<>();
                quiz.add(rs.getString("quiz_id"));
                quiz.add(rs.getString("score"));
                quiz.add(rs.getString("time"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }

    public static Vector<String> getSentMessages(String user_from, String user_to){
        Vector<String> chat = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chat WHERE user_from = \""+ user_from +"\" AND user_to = \"" + user_to + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                chat.add(rs.getString("chat_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chat;
    }

    public static Vector<String> getMessageWithID(int chatID){
        Vector<String> info = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chat WHERE chat_id = \""+ chatID + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                info.add(rs.getString("user_from"));
                info.add(rs.getString("user_to"));
                info.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    public static Vector<String> getAllSentMessages(String user_from){
        Vector<String> chat = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chat WHERE user_from = \""+ user_from +"\" ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                chat.add(rs.getString("chat_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chat;
    }

    public static Vector<String> getAllReceivedMessages(String user_to){
        Vector<String> chat = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chat WHERE user_to = \""+ user_to +"\" ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                chat.add(rs.getString("chat_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chat;
    }

    public static Vector<String> getAllSentChallenges(String user_from){
        Vector<String> requests = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM quizChallenges WHERE user_from = \""+ user_from + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                requests.add(rs.getString("challenge_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public static Vector<String> getAllReceivedChallenges(String user_to){
        Vector<String> requests = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM quizChallenges WHERE user_to = \""+ user_to + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                requests.add(rs.getString("challenge_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public static Vector<String> getChallenge(String user_from, String user_to){
        Vector<String> requests = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM quizChallenges WHERE user_from = \""+ user_from +"\" AND user_to = \"" + user_to + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                requests.add(rs.getString("challenge_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }

    public static Vector<String> getChallengeWithID(String id){
        Vector<String> info = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM quizChallenges WHERE challenge_id = \""+ id + "\"ORDER BY sentDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                info.add(rs.getString("user_from"));
                info.add(rs.getString("user_to"));
                info.add(rs.getString("link"));
                info.add(rs.getString("status"));
                info.add(rs.getString("quiz_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
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

    public static void sendChallenge(String from, String to, String link, int quizID) throws SQLException {
        String sql = "INSERT INTO quizChallenges (user_from, user_to, link, quiz_id) VALUES (?, ?, ?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , from);
        st.setString(2 , to);
        st.setString(3 , link);
        st.setInt(4 , quizID);
        st.executeUpdate();
    }

    public static void addAchievement(String name, String achievement) throws SQLException {
        String sql = "INSERT INTO achievements (username, achievement_type) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , name);
        st.setString(2 , achievement);
        st.executeUpdate();
    }

    public static void sendMessage(String from, String to, String message) throws SQLException {
        String sql = "INSERT INTO chat (user_from, user_to, message) VALUES (?, ?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1 , from);
        st.setString(2 , to);
        st.setString(3 , message);
        st.executeUpdate();
    }


}
