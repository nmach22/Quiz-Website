package main.test;

import main.Manager.DataBaseConnection;
import main.Manager.TakeQuiz;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TakeQuizTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPS;

    @Mock
    private ResultSet mockRS;

    private TakeQuiz takeQuiz;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS);
        when(mockPS.executeQuery()).thenReturn(mockRS);

        DataBaseConnection.setMockConnection(mockConnection);
        takeQuiz = new TakeQuiz("1");
    }

    @Test
    public void testConstructor() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY question_id asc";
        verify(mockConnection).prepareStatement(query);
        verify(mockPS).setInt(1, 1);
    }

    @Test
    public void testGetQuestions() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getInt("question_id")).thenReturn(1);
        when(mockRS.getString("question_type")).thenReturn("questionMultipleChoice");

        List<Map<String, Object>> questions = takeQuiz.getQuestions();
        assertEquals(1, questions.size());
        Map<String, Object> question = questions.get(0);
        assertEquals(1, question.get("question_id"));
        assertEquals("questionMultipleChoice", question.get("question_type"));
    }

    @Test
    public void testFetchMultipleChoiceQuestions() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("choice")).thenReturn("Option A");
        when(mockRS.getInt("is_correct")).thenReturn(1);
        when(mockRS.getString("question")).thenReturn("What is 2+2?");

        List<Map<String, Object>> multipleChoiceQuestions = takeQuiz.fetchMultipleChoiceQuestions();
        assertEquals(1, multipleChoiceQuestions.size());
        Map<String, Object> question = multipleChoiceQuestions.get(0);
        assertEquals("What is 2+2?", question.get("question"));
        assertTrue(((Set<?>) question.get("multipleChoices")).contains("Option A"));
        assertTrue(((Set<?>) question.get("correct_answers")).contains("Option A"));
    }

    @Test
    public void testFetchFillInTheBlankQuestions() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("answer")).thenReturn("answer");

        List<Map<String, Object>> fillInTheBlankQuestions = takeQuiz.fetchFillInTheBlankQuestions();
        assertEquals(1, fillInTheBlankQuestions.size());
        Map<String, Object> question = fillInTheBlankQuestions.get(0);
        assertTrue(((Set<?>) question.get("correct_answers")).contains("answer"));
    }

    @Test
    public void testFetchResponseQuestions() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("answer")).thenReturn("answer");

        List<Map<String, Object>> responseQuestions = takeQuiz.fetchResponseQuestions();
        assertEquals(1, responseQuestions.size());
        Map<String, Object> question = responseQuestions.get(0);
        assertTrue(((Set<?>) question.get("correct_answers")).contains("answer"));
    }

    @Test
    public void testFetchPictureResponseQuestions() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("picture_link")).thenReturn("http://example.com/pic.jpg");
        when(mockRS.getString("answer")).thenReturn("answer");

        List<Map<String, Object>> pictureResponseQuestions = takeQuiz.fetchPictureResponseQuestions();
        assertEquals(1, pictureResponseQuestions.size());
        Map<String, Object> question = pictureResponseQuestions.get(0);
        assertEquals("http://example.com/pic.jpg", question.get("picture_link"));
        assertTrue(((Set<?>) question.get("correct_answers")).contains("answer"));
    }

    @Test
    public void testFetchSettings() throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getInt("is_random")).thenReturn(1);
        when(mockRS.getInt("one_page")).thenReturn(0);
        when(mockRS.getInt("immediate_correction")).thenReturn(1);
        when(mockRS.getInt("practice_mode")).thenReturn(0);
        when(mockRS.getInt("duration")).thenReturn(60);

        Map<String, Object> settings = takeQuiz.fetchSettings();
        assertEquals(1, settings.get("is_random"));
        assertEquals(0, settings.get("one_page"));
        assertEquals(1, settings.get("immediate_correction"));
        assertEquals(0, settings.get("practice_mode"));
        assertEquals(60, settings.get("duration"));
    }

    @Test
    public void testFetchMultipleChoiceQuestionsWithException() throws SQLException {
        when(mockRS.next()).thenThrow(new SQLException("Database error"));

        try {
            takeQuiz.fetchMultipleChoiceQuestions();
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testFetchFillInTheBlankQuestionsWithException() throws SQLException {
        when(mockRS.next()).thenThrow(new SQLException("Database error"));

        try {
            takeQuiz.fetchFillInTheBlankQuestions();
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testFetchResponseQuestionsWithException() throws SQLException {
        when(mockRS.next()).thenThrow(new SQLException("Database error"));

        try {
            takeQuiz.fetchResponseQuestions();
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testFetchPictureResponseQuestionsWithException() throws SQLException {
        when(mockRS.next()).thenThrow(new SQLException("Database error"));

        try {
            takeQuiz.fetchPictureResponseQuestions();
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testFetchSettingsWithException() throws SQLException {
        when(mockPS.executeQuery()).thenThrow(new SQLException("Database error"));

        try {
            takeQuiz.fetchSettings();
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testConstructorWithInvalidQuery() throws SQLException, ClassNotFoundException {
        when(mockPS.executeQuery()).thenThrow(new SQLException("Invalid query"));

        try {
            new TakeQuiz("1");
        } catch (SQLException e) {
            assertEquals("Invalid query", e.getMessage());
        }
    }
}
