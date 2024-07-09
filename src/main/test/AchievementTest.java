package main.Manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AchievementTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testGetAchievements() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("achievement_type")).thenReturn("type1");
        when(mockResultSet.getString("dateAchieved")).thenReturn("2023-07-09");

        ResultSet mockResultSet2 = mock(ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet).thenReturn(mockResultSet2);
        when(mockResultSet2.next()).thenReturn(true);
        when(mockResultSet2.getString("achievement_description")).thenReturn("desc1");
        when(mockResultSet2.getString("achievement_badge")).thenReturn("badge1");

        DataBaseConnection.setMockConnection(mockConnection);

        ArrayList<Achievement> achievements = Achievement.getAchievements("user1");

        assertEquals(1, achievements.size());
        assertEquals("type1", achievements.get(0).type);
        assertEquals("2023-07-09", achievements.get(0).created);
        assertEquals("desc1", achievements.get(0).description);
        assertEquals("badge1", achievements.get(0).badge);
    }

    @Test
    public void testGetUnreadAchievements() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(5);

        DataBaseConnection.setMockConnection(mockConnection);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        int unreadCount = Achievement.getUnreadAchievements("user1");

        assertEquals(5, unreadCount);
    }

    @Test
    public void testUpdateUnreads() throws SQLException, ClassNotFoundException {

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        DataBaseConnection.setMockConnection(mockConnection);

        Achievement.updateUnreads("user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
