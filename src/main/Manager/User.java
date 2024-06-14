package main.Manager;

import java.sql.*;
import java.util.Vector;

public class User {

    private static Connection con;

    public User() throws ClassNotFoundException, SQLException {

        con = DataBaseConnection.getConnection();
    }

    public static Vector<String> getFriends(String name){
        Vector<String> friends = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM friends WHERE username = \""+ name +"\" ORDER BY addDate";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                friends.add(rs.getString("friend"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public static Vector<String> getAchievements(String name){
        Vector<String> achievements = new Vector<>();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM achievements WHERE username = \""+ name +"\" ORDER BY dateAchieved";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                achievements.add(rs.getString("achievement_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return achievements;
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

    public static boolean addFriend(String name, String friend) throws SQLException {
        if(!getFriends(name).contains(friend)){
            String sql = "INSERT INTO friends (username, friend) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1 , name);
            st.setString(2 , friend);
            st.executeUpdate();
            sql = "INSERT INTO friends (username, friend) VALUES (?, ?)";
            st = con.prepareStatement(sql);
            st.setString(1 , friend);
            st.setString(2 , name);
            st.executeUpdate();
            return true;
        }
        return false;
    }

    public static boolean sendFriendRequest(String name, String friend) throws SQLException {
        if(!getFriends(name).contains(friend) && !getSentFriendRequests(name).contains(friend)){
            String sql = "INSERT INTO friendRequests (user_from, user_to) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1 , name);
            st.setString(2 , friend);
            st.executeUpdate();
            return true;
        }
        return false;
    }

    public static boolean removeFriend(String name, String friend) throws SQLException {
        if(getFriends(name).contains(friend)){
            String sql = "DELETE FROM friends WHERE username = ? AND friend = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, friend);
            st.executeUpdate();
            st.setString(1, friend);
            st.setString(2, name);
            st.executeUpdate();
            return true;
        }
        return false;
    }

    public static boolean removeFriendRequest(String name, String friend) throws SQLException {
        if(getSentFriendRequests(name).contains(friend)){
            String sql = "DELETE FROM friendRequests WHERE user_from = ? AND user_to = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, friend);
            st.executeUpdate();
            return true;
        }
        return false;
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
