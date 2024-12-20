import java.sql.*;
import User.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {
    private Connection con;

    @Before
    public void setUp() throws Exception {
        con = DatabaseConnection.getInstance().getConnection();
        con.createStatement().execute("DELETE FROM Events WHERE Event_Name LIKE 'Test%'");
    }

    @After
    public void tearDown() throws Exception {
        con.createStatement().execute("DELETE FROM Events WHERE Event_Name LIKE 'Test%'");
    }

    @Test
    public void testSaveMusicEvent() throws Exception {
        Event musicEvent = EventFactory.createEvent("Music", "Test Music", "2024-12-25", "Description", "1", 50, 100.0);
        musicEvent.saveEvent();

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Events WHERE Event_Name = ?");
        pstmt.setString(1, "Test Music");
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            fail("Music event should exist in the database.");
        }

        assertEquals("Event type mismatch.","Music", rs.getString("Event_Type"));
        assertEquals( "Ticket count mismatch.",50, rs.getInt("Ticket_Count"));
        assertEquals( "Ticket price mismatch.",100.0, rs.getDouble("Ticket_Price"), 0.01);
    }


    @Test
    public void testInvalidEvent() {
        Event invalidEvent = EventFactory.createEvent("Music", "Invalid Event", "2024-12-25", "Description", "1", 0, 100.0); // Ticket count 0
        assertThrows( "Expected IllegalArgumentException for invalid ticket count.",IllegalArgumentException.class, invalidEvent::saveEvent);
    }



    @Test
    public void testSaveFamilyEvent() throws Exception {
        Event familyEvent = EventFactory.createEvent("Family", "Test Family", "2024-12-31", "Family Event Description", "1", 30, 75.0);
        familyEvent.saveEvent();

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Events WHERE Event_Name = ?");
        pstmt.setString(1, "Test Family");
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) {
            fail("Family event should exist in the database.");
        }

        assertEquals( "Event type mismatch.","Family", rs.getString("Event_Type"));
        assertEquals( "Ticket count mismatch.",30, rs.getInt("Ticket_Count"));
        assertEquals( "Ticket price mismatch.",75.0, rs.getDouble("Ticket_Price"), 0.01);
    }

}
