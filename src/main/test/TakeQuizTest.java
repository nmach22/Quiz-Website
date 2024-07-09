package main.Manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TakeQuizTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPS;

    @Mock
    private ResultSet mockRS;

    @Mock
    private ResultSetMetaData mockMetaData;

    private TakeQuiz takeQuiz;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS);
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.getMetaData()).thenReturn(mockMetaData);

        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testConstructor() throws SQLException, ClassNotFoundException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getInt("question_id")).thenReturn(1);
        when(mockRS.getString("question_type")).thenReturn("questionMultipleChoice");

        takeQuiz = new TakeQuiz("1");

        verify(mockConnection).prepareStatement("SELECT * FROM questions WHERE quiz_id = ? ORDER BY question_id asc");
        verify(mockPS).setInt(1, 1);
        verify(mockPS).executeQuery();
        assertEquals(1, takeQuiz.getQuestions().size());
    }

    @Test
    public void testFetchMultipleChoiceQuestions() throws SQLException, ClassNotFoundException {
        setupMockForQuestions("questionMultipleChoice");
        takeQuiz = new TakeQuiz("1");

        when(mockRS.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockRS.getString(anyInt())).thenReturn("Option A");
        when(mockRS.getInt(anyInt())).thenReturn(1);
        when(mockMetaData.getColumnCount()).thenReturn(2);

        List<Map<String, Object>> result = takeQuiz.fetchMultipleChoiceQuestions();

        assertEquals(1, result.size());
        assertTrue(((Set<?>) result.get(0).get("multipleChoices")).contains("Option A"));
        assertTrue(((Set<?>) result.get(0).get("correct_answers")).contains("Option A"));
    }

    @Test
    public void testFetchFillInTheBlankQuestions() throws SQLException, ClassNotFoundException {
        setupMockForQuestions("questionFillInTheBlank");
        takeQuiz = new TakeQuiz("1");

        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString(anyInt())).thenReturn("answer");
        when(mockMetaData.getColumnCount()).thenReturn(1);

        List<Map<String, Object>> result = takeQuiz.fetchFillInTheBlankQuestions();

        assertEquals(1, result.size());
        assertTrue(((Set<?>) result.get(0).get("correct_answers")).contains("answer"));
    }

    @Test
    public void testFetchResponseQuestions() throws SQLException, ClassNotFoundException {
        setupMockForQuestions("questionResponse");
        takeQuiz = new TakeQuiz("1");

        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString(anyInt())).thenReturn("answer");
        when(mockMetaData.getColumnCount()).thenReturn(1);

        List<Map<String, Object>> result = takeQuiz.fetchResponseQuestions();

        assertEquals(1, result.size());
        assertTrue(((Set<?>) result.get(0).get("correct_answers")).contains("answer"));
    }

    @Test
    public void testFetchPictureResponseQuestions() throws SQLException, ClassNotFoundException {
        setupMockForQuestions("questionPictureResponse");
        takeQuiz = new TakeQuiz("1");

        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getString("picture_link")).thenReturn("http://example.com/image.jpg");
        when(mockRS.getString("question")).thenReturn("What's in this picture?");
        when(mockRS.getString(anyInt())).thenReturn("answer");
        when(mockMetaData.getColumnCount()).thenReturn(1);

        List<Map<String, Object>> result = takeQuiz.fetchPictureResponseQuestions();

        assertEquals(1, result.size());
    }

    @Test
    public void testFetchSettings() throws SQLException, ClassNotFoundException {
        takeQuiz = new TakeQuiz("1");

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
    public void testEmptyQuestions() throws SQLException, ClassNotFoundException {
        when(mockRS.next()).thenReturn(false);
        takeQuiz = new TakeQuiz("1");

        assertTrue(takeQuiz.fetchMultipleChoiceQuestions().isEmpty());
        assertTrue(takeQuiz.fetchFillInTheBlankQuestions().isEmpty());
        assertTrue(takeQuiz.fetchResponseQuestions().isEmpty());
        assertTrue(takeQuiz.fetchPictureResponseQuestions().isEmpty());
    }

    private void setupMockForQuestions(String questionType) throws SQLException {
        when(mockRS.next()).thenReturn(true).thenReturn(false);
        when(mockRS.getInt("question_id")).thenReturn(1);
        when(mockRS.getString("question_type")).thenReturn(questionType);
    }
}