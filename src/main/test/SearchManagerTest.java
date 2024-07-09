package main.Manager;
import main.Manager.DataBaseConnection;
import main.Manager.SearchManager;
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
public class SearchManagerTest {
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
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);


        DataBaseConnection.setMockConnection(mockConnection);
    }
    @Test
    public void testSearchQuizzes() throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("quiz_name")).thenReturn("Sample Quiz");
        when(mockResultSet.getInt("quiz_id")).thenReturn(1);


        ArrayList<SearchManager> results = SearchManager.searchQuizzes("Sample Quiz");


        assertEquals(1, results.size());
        assertEquals("Sample Quiz", results.get(0).quizName);
        assertEquals(1, results.get(0).quiz_id);


        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockPreparedStatement, times(1)).setString(1, "Sample Quiz");
    }
}
