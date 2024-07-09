package main.test;
import main.Manager.DataBaseConnection;
import main.Manager.Statistics;
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
public class StatisticsTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private PreparedStatement mockPreparedStatement2;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private ResultSet mockResultSet2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement).thenReturn(mockPreparedStatement2);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement2.executeQuery()).thenReturn(mockResultSet2);

        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testGetUserStatisticsForDay() throws SQLException, ClassNotFoundException {
        testGetUserStatistics("day");
    }

    @Test
    public void testGetUserStatisticsForWeek() throws SQLException, ClassNotFoundException {
        testGetUserStatistics("week");
    }

    @Test
    public void testGetUserStatisticsForMonth() throws SQLException, ClassNotFoundException {
        testGetUserStatistics("month");
    }

    @Test
    public void testGetUserStatisticsForYear() throws SQLException, ClassNotFoundException {
        testGetUserStatistics("year");
    }

    @Test
    public void testGetUserStatisticsForAllTime() throws SQLException, ClassNotFoundException {
        testGetUserStatistics("all_time");
    }

    private void testGetUserStatistics(String time) throws SQLException, ClassNotFoundException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet2.next()).thenReturn(true, false);
        when(mockResultSet.getInt("total_users")).thenReturn(5);
        when(mockResultSet2.getInt("total_quizzes")).thenReturn(10);
        ArrayList<Statistics> results = Statistics.getUserStatistics(time);
        assertEquals(1, results.size());
        assertEquals(5, results.get(0).users);
        assertEquals(10, results.get(0).quizzes);
    }

}
