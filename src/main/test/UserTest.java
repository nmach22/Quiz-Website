package main.Manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import main.Manager.User;
import main.Manager.Pair;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.*;


class UserTest {

    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockStatement = mock(Statement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        User.setConnection(mockConnection);
    }

    @Test
    void testGetFriends() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(1)).thenReturn("friend1");

        ArrayList<String> friends = User.getFriends("testUser");

        assertEquals(1, friends.size());
        assertEquals("friend1", friends.get(0));

        Mockito.verify(mockPreparedStatement).setString(1, "testUser");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockResultSet, Mockito.times(2)).next();
    }

    @Test
    void testGetRecentlyAddedQuizzes() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("quiz_name")).thenReturn("Quiz 1", "Quiz 2");
        when(mockResultSet.getString("quiz_id")).thenReturn("1", "2");

        ArrayList<Pair> quizzes = User.getRecentlyAddedQuizzes();

        assertEquals(2, quizzes.size());
        assertEquals(new Pair("Quiz 1", "1"), quizzes.get(0));
        assertEquals(new Pair("Quiz 2", "2"), quizzes.get(1));

        Mockito.verify(mockStatement).executeQuery(anyString());
        Mockito.verify(mockResultSet, Mockito.times(3)).next();
    }

    @Test
    void testGetPopularQuizzes() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("quiz_name")).thenReturn("Quiz 1", "Quiz 2");
        when(mockResultSet.getString("quiz_id")).thenReturn("1", "2");

        ArrayList<Pair> quizzes = User.getPopularQuizzes();

        assertEquals(2, quizzes.size());
        assertEquals(new Pair("Quiz 1", "1"), quizzes.get(0));
        assertEquals(new Pair("Quiz 2", "2"), quizzes.get(1));

        verify(mockStatement).executeQuery(anyString());
        verify(mockResultSet, times(3)).next();
    }

    @Test
    void testGetCreatedQuizzes() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("quiz_id")).thenReturn("1", "2");

        Vector<String> quizzes = User.getCreatedQuizzes("testuser");

        assertEquals(2, quizzes.size());
        assertEquals("1", quizzes.get(0));
        assertEquals("2", quizzes.get(1));

        verify(mockStatement).executeQuery(anyString());
        verify(mockResultSet, times(3)).next();
    }

    @Test
    void testGetCreatedQuizzesSQLException() throws SQLException {
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException());

        assertThrows(RuntimeException.class, () -> User.getCreatedQuizzes("testuser"));
    }

    @Test
    void testAcceptChallenge() throws SQLException {
        User.acceptChallenge(123);

        verify(mockConnection).prepareStatement("UPDATE quizChallenges SET status = ? where challenge_id = ?");

        verify(mockPreparedStatement).setString(1, "accepted");
        verify(mockPreparedStatement).setInt(2, 123);

        verify(mockPreparedStatement).executeUpdate();

        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }

    @Test
    void testAcceptChallengeSQLException() throws SQLException {
        doThrow(SQLException.class).when(mockPreparedStatement).executeUpdate();

        assertThrows(SQLException.class, () -> User.acceptChallenge(123));

        verify(mockConnection).prepareStatement("UPDATE quizChallenges SET status = ? where challenge_id = ?");
    }

    @Test
    void testRejectChallenge() throws SQLException {
        User.rejectChallenge(123);

        verify(mockConnection).prepareStatement("UPDATE quizChallenges SET status = ? where challenge_id = ?");

        verify(mockPreparedStatement).setString(1, "rejected");
        verify(mockPreparedStatement).setInt(2, 123);

        verify(mockPreparedStatement).executeUpdate();

        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }

    @Test
    void testRejectChallengeSQLException() throws SQLException {
        doThrow(SQLException.class).when(mockPreparedStatement).executeUpdate();

        assertThrows(SQLException.class, () -> User.rejectChallenge(123));

        verify(mockConnection).prepareStatement("UPDATE quizChallenges SET status = ? where challenge_id = ?");
    }

    @Test
    void testAddFriendSQLException() throws SQLException {
        doThrow(SQLException.class).when(mockPreparedStatement).executeUpdate();
        assertThrows(SQLException.class, () -> User.addFriend("user1", "user2"));
        verify(mockConnection).prepareStatement("INSERT INTO friends (user1, user2) VALUES (?, ?)");
    }

    @Test
    public void testRejectFriendRequest() throws SQLException {
        User.rejectFriendRequest("user1", "user2");

        verify(mockConnection).prepareStatement("DELETE FROM friendRequests where (user_from = ? AND user_to = ?)");
        verify(mockPreparedStatement).setString(1, "user2");
        verify(mockPreparedStatement).setString(2, "user1");
        verify(mockPreparedStatement).executeUpdate();

        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }


    @Test
    public void testSendFriendRequest() throws SQLException {
        User.sendFriendRequest("user1", "user2");

        verify(mockConnection).prepareStatement("INSERT INTO friendRequests (user_from, user_to) VALUES (?, ?)");
        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "user2");
        verify(mockPreparedStatement).executeUpdate();

        verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
    }

    @Test
    void testDoesFriendRequestExist() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);

        boolean result = User.doesFriendRequestExist("user1", "user2");

        assertTrue(result);
        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "user2");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void testDoesFriendRequestExist_NotExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        boolean result = User.doesFriendRequestExist("user1", "user2");

        assertFalse(result);
        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "user2");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void testRemoveFriendRequest() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);


        User.setConnection(mockConnection);

        User.removeFriendRequest("user1", "user2");

        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "user2");
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    void testSendChallenge() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);


        User.setConnection(mockConnection);

        User.sendChallenge("user1", "user2", 101, 200);

        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "user2");
        verify(mockPreparedStatement).setInt(3, 101);
        verify(mockPreparedStatement).setInt(4, 200);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testAddAchievement() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);


        User.setConnection(mockConnection);

        User.addAchievement("user1", "Top Scorer");

        verify(mockPreparedStatement).setString(1, "user1");
        verify(mockPreparedStatement).setString(2, "Top Scorer");
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    void testHighestScore() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("score")).thenReturn(95);


        User.setConnection(mockConnection);

        int highestScore = User.highestScore(101);

        assertEquals(95, highestScore);

        verify(mockPreparedStatement).setInt(1, 101);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt("score");
    }

    @Test
    void testGetUsersHighestScoreOnQuiz() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(95);


        User.setConnection(mockConnection);

        int highestScore = User.getUsersHighestScoreOnQuiz("testuser", 101);

        assertEquals(95, highestScore);

        verify(mockPreparedStatement).setString(1, "testuser");
        verify(mockPreparedStatement).setInt(2, 101);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(1);
    }

    @Test
    void testTakenQuizzesAmount() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(3); // Assuming there are 3 quizzes taken

        User.setConnection(mockConnection);

        int amount = User.takenQuizzesAmount("testuser", 101);

        assertEquals(3, amount);

        verify(mockPreparedStatement).setString(1, "testuser");
        verify(mockPreparedStatement).setInt(2, 101);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(1);
    }

    @Test
    void testGetUsersScore() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(150); // Assuming total score is 150

        User.setConnection(mockConnection);

        int totalScore = User.getUsersScore("testuser");

        assertEquals(150, totalScore);

        verify(mockPreparedStatement).setString(1, "testuser");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(1);
    }

    @Test
    void testTakenQuizzesByDate() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("quiz_id")).thenReturn("1", "2");
        when(mockResultSet.getString("score")).thenReturn("80", "90");
        when(mockResultSet.getInt("time")).thenReturn(30, 45);

        User.setConnection(mockConnection);

        Vector<Vector<String>> quizzes = User.takenQuizzesByDate("testuser");

        assertEquals(2, quizzes.size());

        assertEquals("1", quizzes.get(0).get(0)); // quiz_id
        assertEquals("80", quizzes.get(0).get(1)); // score
        assertEquals("30", quizzes.get(0).get(2)); // time

        assertEquals("2", quizzes.get(1).get(0)); // quiz_id
        assertEquals("90", quizzes.get(1).get(1)); // score
        assertEquals("45", quizzes.get(1).get(2)); // time

    }

    @Test
    void testAddFriend() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Assuming 1 row affected

        User.setConnection(mockConnection);

        User.addFriend("testuser", "friend");

        verify(mockPreparedStatement, times(3)).executeUpdate();
    }

    @AfterEach
    void tearDown() throws Exception {
        mockConnection.close();
    }
}