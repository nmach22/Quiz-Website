package main.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Announcement {
    public String user;
    public String title;
    public String description;
    public String created;

    public Announcement(String user, String title, String description, String created) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.created = created;
    }
    public static ArrayList<Announcement> getAnnouncements(int amount) throws ClassNotFoundException, SQLException {
        String query = "";
        ArrayList<Announcement> res = new ArrayList<>();
        if(amount == 0){
            query = "SELECT * FROM announcements ORDER BY creation_date desc";
        }else {
            query = "SELECT * FROM announcements ORDER BY creation_date desc LIMIT " + amount;
        }
        Connection con = DataBaseConnection.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            res.add(new Announcement(rs.getString("username"), rs.getString("title"),rs.getString("announcement"),rs.getString("creation_date")));
        }
        return res;
    }

    public static void  makeAnnouncement(String description , String title, String user) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO announcements (username , announcement, title) VALUES " +
                "('" + user + "','" + description +"','" + title + "')";
        Connection con = DataBaseConnection.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.executeUpdate();
    }
}
