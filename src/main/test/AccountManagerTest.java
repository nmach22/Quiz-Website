package main.Manager;

import main.Manager.AccountManager;
import main.Manager.DataBaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountManagerTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private AccountManager accountManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock the database connection
        DataBaseConnection.setMockConnection(mockConnection);

        accountManager = new AccountManager();
    }


    @Test
    public void testGetFN() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn("John");

        String firstName = AccountManager.getFN("user1");
        assertEquals("John", firstName);
    }

    @Test
    public void testGetLN() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn("Doe");

        String lastName = AccountManager.getLN("user1");
        assertEquals("Doe", lastName);
    }

    @Test
    public void testIsCorrect() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        boolean isCorrect = accountManager.isCorrect("user1", "password");
        assertEquals(true, isCorrect);
    }

    @Test
    public void testHasAcc() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        boolean hasAcc = AccountManager.hasAcc("user1");
        assertEquals(true, hasAcc);
    }

    @Test
    public void testCreateAcc() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        accountManager.createAcc("user1", "password", "John", "Doe");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testChangeBio() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        accountManager.changeBio("New bio", "user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetBio() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn("Bio");

        String bio = AccountManager.getBio("user1");
        assertEquals("Bio", bio);
    }

    @Test
    public void testRemoveAcc() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = accountManager.removeAcc("user1");
        assertEquals(1, result);
    }

    @Test
    public void testIsAdmin() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(3)).thenReturn(1);

        boolean isAdmin = AccountManager.isAdmin("user1");
        assertEquals(true, isAdmin);
    }

    @Test
    public void testPromoteToAdmin() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = accountManager.promoteToAdmin("user1");
        assertEquals(1, result);
    }

    @Test
    public void testChangePas() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        accountManager.changePas("newpassword", "user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testChangeFN() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        accountManager.changeFN("NewFirstName", "user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testChangeLN() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        accountManager.changeLN("NewLastName", "user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
