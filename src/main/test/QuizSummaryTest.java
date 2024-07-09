package main.test;
import main.Manager.DataBaseConnection;
import main.Manager.QuizSummary;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class QuizSummaryTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        DataBaseConnection.setMockConnection(mockConnection);

    }

    @Test
    public void testFillQuizInfo() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("quiz_name")).thenReturn("Sample Quiz");
        when(mockResultSet.getString("description")).thenReturn("Sample Description");
        when(mockResultSet.getString("author")).thenReturn("Sample Author");
        when(mockResultSet.getBoolean("practice_mode")).thenReturn(true);
        when(mockResultSet.getBoolean("is_random")).thenReturn(false);
        when(mockResultSet.getBoolean("one_page")).thenReturn(true);
        when(mockResultSet.getBoolean("immediate_correction")).thenReturn(false);

        QuizSummary quizSummary = new QuizSummary("1", "testUser");

        assertEquals("Sample Quiz", quizSummary.getQuizName());
        assertEquals("Sample Description", quizSummary.getDescription());
        assertEquals("Sample Author", quizSummary.getAuthor());
        assertTrue(quizSummary.isPracticeMode());
        assertFalse(quizSummary.isRandom());
        assertTrue(quizSummary.isOnePage());
        assertFalse(quizSummary.isImmediateCorrection());
    }

    @Test
    public void testGetPastPerformances() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("score")).thenReturn(90);
        when(mockResultSet.getInt("time")).thenReturn(120);
        when(mockResultSet.getTimestamp("date_taken")).thenReturn(new java.sql.Timestamp(System.currentTimeMillis()));
        QuizSummary quizSummary = new QuizSummary("1", "testUser");
        List<Map<String, Object>> pastPerformances = quizSummary.getPastPerformances();
        assertEquals(1, pastPerformances.size());
        assertEquals(90, pastPerformances.get(0).get("score"));
        assertEquals(120, pastPerformances.get(0).get("time"));
    }

    @Test
    public void testGetTopAllTime() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("username")).thenReturn("topUser");
        when(mockResultSet.getInt("score")).thenReturn(95);

        // Call the method
        QuizSummary quizSummary = new QuizSummary("1", "testUser");
        List<Map<String, Object>> topAllTime = quizSummary.getTopAllTime();

        // Verify the results
        assertEquals(1, topAllTime.size());
        assertEquals("topUser", topAllTime.get(0).get("username"));
        assertEquals(95, topAllTime.get(0).get("score"));

        // Verify that the prepared statement was executed once
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
    }

    @Test
    public void testGetTopLastDay() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("username")).thenReturn("lastDayUser");
        when(mockResultSet.getInt("score")).thenReturn(92);

        // Call the method
        QuizSummary quizSummary = new QuizSummary("1", "testUser");
        List<Map<String, Object>> topLastDay = quizSummary.getTopLastDay();

        // Verify the results
        assertEquals(1, topLastDay.size());
        assertEquals("lastDayUser", topLastDay.get(0).get("username"));
        assertEquals(92, topLastDay.get(0).get("score"));

        // Verify that the prepared statement was executed once
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
    }

    @Test
    public void testGetRecentTestTakers() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("username")).thenReturn("recentUser");
        when(mockResultSet.getInt("score")).thenReturn(85);


        QuizSummary quizSummary = new QuizSummary("1", "testUser");
        List<Map<String, Object>> recentTestTakers = quizSummary.getRecentTestTakers();

        assertEquals(1, recentTestTakers.size());
        assertEquals("recentUser", recentTestTakers.get(0).get("username"));
        assertEquals(85, recentTestTakers.get(0).get("score"));


    }

    @Test
    public void testGetSummarySats() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("average_score")).thenReturn(88);
        when(mockResultSet.getInt("max_score")).thenReturn(100);
        when(mockResultSet.getInt("min_score")).thenReturn(70);


        QuizSummary quizSummary = new QuizSummary("1", "testUser");
        Map<String, Object> summaryStats = quizSummary.getSummarySats();


        assertEquals(88, summaryStats.get("average_score"));
        assertEquals(100, summaryStats.get("max_score"));
        assertEquals(70, summaryStats.get("min_score"));

    }
}
