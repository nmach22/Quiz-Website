package main.Manager;

import main.Manager.Announcement;
import main.Manager.DataBaseConnection;
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

public class AnnouncementTest {

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

        // Mock the database connection
        DataBaseConnection.setMockConnection(mockConnection);
    }

    @Test
    public void testGetAnnouncementsNoLimit() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Only one result for simplicity
        when(mockResultSet.getString("username")).thenReturn("user1");
        when(mockResultSet.getString("title")).thenReturn("Title");
        when(mockResultSet.getString("announcement")).thenReturn("Description");
        when(mockResultSet.getString("creation_date")).thenReturn("2023-01-01");

        ArrayList<Announcement> announcements = Announcement.getAnnouncements(0);
        assertEquals(1, announcements.size());
        assertEquals("user1", announcements.get(0).user);
        assertEquals("Title", announcements.get(0).title);
        assertEquals("Description", announcements.get(0).description);
        assertEquals("2023-01-01", announcements.get(0).created);
    }

    @Test
    public void testGetAnnouncementsWithLimit() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Only one result for simplicity
        when(mockResultSet.getString("username")).thenReturn("user1");
        when(mockResultSet.getString("title")).thenReturn("Title");
        when(mockResultSet.getString("announcement")).thenReturn("Description");
        when(mockResultSet.getString("creation_date")).thenReturn("2023-01-01");

        ArrayList<Announcement> announcements = Announcement.getAnnouncements(1);
        assertEquals(1, announcements.size());
        assertEquals("user1", announcements.get(0).user);
        assertEquals("Title", announcements.get(0).title);
        assertEquals("Description", announcements.get(0).description);
        assertEquals("2023-01-01", announcements.get(0).created);
    }

    @Test
    public void testMakeAnnouncement() throws SQLException, ClassNotFoundException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Announcement.makeAnnouncement("Description", "Title", "user1");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
