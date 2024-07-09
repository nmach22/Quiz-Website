package main.Manager;

import main.Manager.DataBaseConnection;
import main.Manager.QuizManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class QuizManagerTest {
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
        QuizManager.con = mockConnection;
    }

    @Test
    public void testGetQuizName() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(1)).thenReturn("Sample Quiz");
        String name = QuizManager.getQuizName("1");
        assertEquals("Sample Quiz", name);
    }
    @Test
    public void testGetQuizCreationDate() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(1)).thenReturn("2023-01-01");
        String date = QuizManager.getQuizCreationDate("1");
        assertEquals("2023-01-01",date);
    }

    @Test
    public void testRemoveQuiz() throws SQLException {
        doNothing().when(mockConnection).setAutoCommit(false);
        doNothing().when(mockConnection).commit();
        doNothing().when(mockConnection).rollback();
        QuizManager.removeQuiz(1);
        verify(mockConnection,times(1)).setAutoCommit(false);
        verify(mockConnection, times(1)).commit();
        verify(mockPreparedStatement, times(12)).executeUpdate();
        verify(mockPreparedStatement, times(12)).setInt(1, 1);
    }

    @Test
    public void testRemoveQuizHistory() throws SQLException {
        QuizManager.removeQuizHistory(1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
    }

}
