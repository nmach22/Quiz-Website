package main.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Achievement {
    public String type;
    public String created;
    public String description;
    public String badge;

    public Achievement(String type, String created, String description, String badge) {
        this.type = type;
        this.created = created;
        this.description = description;
        this.badge = badge;
    }

    public static ArrayList<Achievement> getAchievements(String user) throws SQLException, ClassNotFoundException {
        ArrayList<Achievement> achievements = new ArrayList<>();
        String sql = "SELECT * FROM achievements WHERE username = ? ORDER BY dateAchieved";
        String sql2 = "SELECT * FROM achievementTypes WHERE achievement_type = ?";

        Connection con = DataBaseConnection.getConnection();
        PreparedStatement ps1 = con.prepareStatement(sql);
        PreparedStatement ps2 = con.prepareStatement(sql2);
        ps1.setString(1, user);
        try (ResultSet rs = ps1.executeQuery()) {
            while (rs.next()) {
                String type = rs.getString("achievement_type");

                ps2.setString(1, type);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        achievements.add(new Achievement(type, rs.getString("dateAchieved"),
                                rs2.getString("achievement_description"), rs2.getString("achievement_badge")));
                    }
                }
            }
        }
        return achievements;
    }
}
