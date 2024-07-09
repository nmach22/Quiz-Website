package main.Manager;

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

public class NotificationsTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testGetChallenges() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("user_from")).thenReturn("user1");
        when(mockResultSet.getInt("quiz_id")).thenReturn(10);
        when(mockResultSet.getInt("highest_score")).thenReturn(100);
        when(mockResultSet.getInt("challenge_id")).thenReturn(1);

        ArrayList<Notifications> challenges = Notifications.getChallenges("testUser");
        assertEquals(1, challenges.size());
        Notifications challenge = challenges.get(0);
        assertEquals("user1", challenge.from);
        assertEquals(10, challenge.quiz_id);
        assertEquals(100, challenge.score);
        assertEquals(1, challenge.challenge_id);
    }

    @Test
    public void testGetChallengesWithEmptyResult() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<Notifications> challenges = Notifications.getChallenges("testUser");
        assertEquals(0, challenges.size());
    }

    @Test
    public void testGetChallengesSQLException() throws SQLException, ClassNotFoundException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        try {
            Notifications.getChallenges("testUser");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testGetFriendRequests() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("user_from")).thenReturn("friend1");

        ArrayList<String> friendRequests = Notifications.getFriendRequests("testUser");
        assertEquals(1, friendRequests.size());
        assertEquals("friend1", friendRequests.get(0));
    }

    @Test
    public void testGetFriendRequestsWithEmptyResult() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(false);

        ArrayList<String> friendRequests = Notifications.getFriendRequests("testUser");
        assertEquals(0, friendRequests.size());
    }

    @Test
    public void testGetFriendRequestsSQLException() throws SQLException, ClassNotFoundException {
        when(mockConnection.createStatement()).thenThrow(new SQLException("Database error"));

        try {
            Notifications.getFriendRequests("testUser");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }



}
