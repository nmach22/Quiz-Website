package main.test;

import main.Manager.DataBaseConnection;
import main.Manager.Notifications;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationTest {

    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockPS;
    @Mock
    Statement mockST;

    @Mock
    ResultSet mockRS;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS);
        when(mockConnection.createStatement()).thenReturn(mockST);
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockST.executeQuery(anyString())).thenReturn(mockRS);

        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testGetChallenges() throws SQLException, ClassNotFoundException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("user_from")).thenReturn("user1");
        when(mockRS.getInt("quiz_id")).thenReturn(10);
        when(mockRS.getInt("highest_score")).thenReturn(100);
        when(mockRS.getInt("challenge_id")).thenReturn(1);

        DataBaseConnection.setMockConnection(mockConnection);
        ArrayList<Notifications> Challenges = Notifications.getChallenges("testUser");
        assertEquals(1,Challenges.size());
        Notifications not = Challenges.get(0);
        assertEquals("user1", not.from);
        assertEquals(10, not.quiz_id);
        assertEquals(100, not.score);
        assertEquals(1,not.challenge_id);

    }

    @Test
    public void testGetFriendRequests() throws SQLException, ClassNotFoundException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("user_from")).thenReturn("friend1");

        ArrayList<String> friendRequests = Notifications.getFriendRequests("testUser");
        assertEquals(1, friendRequests.size());
        assertEquals("friend1", friendRequests.get(0));
    }
}
