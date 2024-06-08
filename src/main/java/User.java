import java.sql.*;
import java.util.Vector;

public class User {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String name = "root";
    public static final String pas = "";
    private Connection con;

    public User() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,name,pas);
    }

    public Vector<String> getFriends(String name){
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

    public Vector<String> getSentFriendRequests(String name){
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

    public Vector<String> getFriendRequests(String name){
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

    public Vector<String> getCreatedQuizzes(String name){
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

    public Vector<Vector<String>> takenQuizzesByDate(String name){
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

    public Vector<Vector<String>> takenQuizzesByTime(String name){
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

    public Vector<Vector<String>> takenQuizzesByScore(String name){
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

    public Vector<String> getAchievements(String name){
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

    public Vector<String> getSentMessages(String user_from, String user_to){
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

    public Vector<String> getMessageWithID(int chatID){
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

    public Vector<String> getAllSentMessages(String user_from){
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

    public Vector<String> getAllReceivedMessages(String user_to){
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

    public Vector<String> getAllSentChallenges(String user_from){
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

    public Vector<String> getAllReceivedChallenges(String user_to){
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

    public Vector<String> getChallenge(String user_from, String user_to){
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

    public Vector<String> getChallengeWithID(String id){
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
    }


}
