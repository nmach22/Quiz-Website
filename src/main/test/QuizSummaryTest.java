package main.Manager;

import main.Manager.DataBaseConnection;
import main.Manager.QuizSummary;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuizSummaryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPS;

    @Mock
    private ResultSet mockRS;

    private QuizSummary quizSummary;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS);
        when(mockPS.executeQuery()).thenReturn(mockRS);

        DataBaseConnection.setMockConnection(mockConnection);
        quizSummary = new QuizSummary("1", "testUser");
    }

    @Test
    public void testConstructor() throws SQLException {
        String query = "SELECT * FROM quizzes WHERE quiz_id = ?";
        verify(mockConnection).prepareStatement(query);
        verify(mockPS).setInt(1, 1);
    }

    @Test
    public void testFillQuizInfo() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("quiz_name")).thenReturn("Quiz Name");
        when(mockRS.getString("description")).thenReturn("Description");
        when(mockRS.getString("author")).thenReturn("Author");
        when(mockRS.getBoolean("practice_mode")).thenReturn(true);
        when(mockRS.getBoolean("is_random")).thenReturn(true);
        when(mockRS.getBoolean("one_page")).thenReturn(true);
        when(mockRS.getBoolean("immediate_correction")).thenReturn(true);

        quizSummary = new QuizSummary("1", "testUser");

        assertEquals("Quiz Name", quizSummary.getQuizName());
        assertEquals("Description", quizSummary.getDescription());
        assertEquals("Author", quizSummary.getAuthor());
        assertEquals(true, quizSummary.isPracticeMode());
        assertEquals(true, quizSummary.isRandom());
        assertEquals(true, quizSummary.isOnePage());
        assertEquals(true, quizSummary.isImmediateCorrection());
    }

    @Test
    public void testGetPastPerformances() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getInt("score")).thenReturn(90);
        when(mockRS.getInt("time")).thenReturn(30);
        when(mockRS.getTimestamp("date_taken")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<Map<String, Object>> pastPerformances = quizSummary.getPastPerformances();
        assertEquals(1, pastPerformances.size());
        Map<String, Object> performance = pastPerformances.get(0);
        assertEquals(90, performance.get("score"));
        assertEquals(30, performance.get("time"));
    }

    @Test
    public void testGetTopAllTime() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("username")).thenReturn("user1");
        when(mockRS.getInt("score")).thenReturn(100);

        List<Map<String, Object>> topAllTime = quizSummary.getTopAllTime();
        assertEquals(1, topAllTime.size());
        Map<String, Object> topPerformance = topAllTime.get(0);
        assertEquals("user1", topPerformance.get("username"));
        assertEquals(100, topPerformance.get("score"));
    }

    @Test
    public void testGetTopLastDay() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("username")).thenReturn("user1");
        when(mockRS.getInt("score")).thenReturn(95);

        List<Map<String, Object>> topLastDay = quizSummary.getTopLastDay();
        assertEquals(1, topLastDay.size());
        Map<String, Object> topPerformance = topLastDay.get(0);
        assertEquals("user1", topPerformance.get("username"));
        assertEquals(95, topPerformance.get("score"));
    }

    @Test
    public void testGetRecentTestTakers() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("username")).thenReturn("user1");
        when(mockRS.getInt("score")).thenReturn(85);

        List<Map<String, Object>> recentTakers = quizSummary.getRecentTestTakers();
        assertEquals(1, recentTakers.size());
        Map<String, Object> recentPerformance = recentTakers.get(0);
        assertEquals("user1", recentPerformance.get("username"));
        assertEquals(85, recentPerformance.get("score"));
    }

    @Test
    public void testGetSummaryStats() throws SQLException {
        when(mockRS.next()).thenReturn(true);
        when(mockRS.getInt("average_score")).thenReturn(80);
        when(mockRS.getInt("max_score")).thenReturn(100);
        when(mockRS.getInt("min_score")).thenReturn(50);

        Map<String, Object> summaryStats = quizSummary.getSummarySats();
        assertEquals(80, summaryStats.get("average_score"));
        assertEquals(100, summaryStats.get("max_score"));
        assertEquals(50, summaryStats.get("min_score"));
    }

}
