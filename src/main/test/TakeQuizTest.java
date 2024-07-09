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
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private ResultSetMetaData mockMetaData;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);


        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testGetQuestions() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("question_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("question_type")).thenReturn("questionMultipleChoice").thenReturn("questionResponse");

        TakeQuiz takeQuiz = new TakeQuiz("1");

        List<Map<String, Object>> questions = takeQuiz.getQuestions();
        assertEquals(2, questions.size());
        assertEquals(1, questions.get(0).get("question_id"));
        assertEquals("questionMultipleChoice", questions.get(0).get("question_type"));
        assertEquals(2, questions.get(1).get("question_id"));
        assertEquals("questionResponse", questions.get(1).get("question_type"));
    }

    @Test
    public void testFetchFillInTheBlankQuestions() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("question_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("question_type")).thenReturn("questionFillInTheBlank").thenReturn("questionResponse");

        TakeQuiz takeQuiz = new TakeQuiz("1");

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getMetaData()).thenReturn(mockMetaData);
        when(mockMetaData.getColumnCount()).thenReturn(2);
        when(mockResultSet.getString(2)).thenReturn("Answer 1");

        List<Map<String, Object>> fillInTheBlankQuestions = takeQuiz.fetchFillInTheBlankQuestions();
        assertEquals(1, fillInTheBlankQuestions.size());
        assertTrue(((Set<String>) fillInTheBlankQuestions.get(0).get("correct_answers")).contains("Answer 1"));
    }

    @Test
    public void testFetchResponseQuestions() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("question_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("question_type")).thenReturn("questionResponse").thenReturn("questionMultipleChoice");

        TakeQuiz takeQuiz = new TakeQuiz("1");

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getMetaData()).thenReturn(mockMetaData);
        when(mockMetaData.getColumnCount()).thenReturn(2);
        when(mockResultSet.getString(2)).thenReturn("Response 1");

        List<Map<String, Object>> responseQuestions = takeQuiz.fetchResponseQuestions();
        assertEquals(1, responseQuestions.size());
        assertTrue(((Set<String>) responseQuestions.get(0).get("correct_answers")).contains("Response 1"));
    }

    @Test
    public void testFetchPictureResponseQuestions() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("question_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("question_type")).thenReturn("questionPictureResponse").thenReturn("questionMultipleChoice");

        TakeQuiz takeQuiz = new TakeQuiz("1");

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getMetaData()).thenReturn(mockMetaData);
        when(mockMetaData.getColumnCount()).thenReturn(2);
        when(mockResultSet.getString(2)).thenReturn("Image 1");

        List<Map<String, Object>> pictureResponseQuestions = takeQuiz.fetchPictureResponseQuestions();
        assertEquals(1, pictureResponseQuestions.size());
        assertTrue(((Set<String>) pictureResponseQuestions.get(0).get("correct_answers")).contains("Image 1"));
    }


}
