package main.test;

import main.Manager.DataBaseConnection;
import main.Manager.History;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class HistoryTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);


        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testHistoryConstructor() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Call the constructor
        new History(1, "user1", 100, 120);

        // Verify that the prepared statement was executed once
        verify(mockPreparedStatement, times(1)).executeUpdate();

        // Verify that the prepared statement parameters were set correctly
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, "user1");
        verify(mockPreparedStatement, times(1)).setInt(3, 100);
        verify(mockPreparedStatement, times(1)).setInt(4, 120);
    }
}
