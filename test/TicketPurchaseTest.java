import User.*;
import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketPurchaseTest {
    private Connection con;

    @Before
    public void setUp() throws Exception {
        con = DatabaseConnection.getInstance().getConnection();
        con.setAutoCommit(false); // Start transaction

        // Clean up any existing data
        con.createStatement().execute("DELETE FROM Tickets");
        con.createStatement().execute("DELETE FROM Events");
        con.createStatement().execute("DELETE FROM User");
        con.createStatement().execute("ALTER TABLE Events AUTO_INCREMENT = 1");
        con.createStatement().execute("ALTER TABLE User AUTO_INCREMENT = 1");

        // Insert test users
        String insertUserQuery = "INSERT INTO User (User_Name, User_Email, Password) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertUserQuery);

        pstmt.setString(1, "Mehboob12");
        pstmt.setString(2, "mehboob56ali78@gmail.com");
        pstmt.setString(3, "Mehboob12@");
        pstmt.executeUpdate();

        pstmt.setString(1, "Tayyab12");
        pstmt.setString(2, "tayyabarain929@gmail.com");
        pstmt.setString(3, "Tayyab12@");
        pstmt.executeUpdate();

        // Insert test events
        String insertEventQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, Ticket_Price, User_ID) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(insertEventQuery);

        // Event 1: Organizer's event (created by Mehboob12 - User_ID 1)
        pstmt.setString(1, "Organizer Event");
        pstmt.setString(2, "Sports");
        pstmt.setString(3, "2024-12-31");
        pstmt.setString(4, "Organizer Event Description");
        pstmt.setInt(5, 5); // Ticket_Count
        pstmt.setDouble(6, 75.00); // Ticket_Price
        pstmt.setInt(7, 1); // User_ID (Mehboob12)
        pstmt.executeUpdate();

        // Event 2: Sold out event (created by Tayyab12 - User_ID 2)
        pstmt.setString(1, "Sold Out Event");
        pstmt.setString(2, "Music");
        pstmt.setString(3, "2024-12-30");
        pstmt.setString(4, "This event is sold out");
        pstmt.setInt(5, 0); // Ticket_Count
        pstmt.setDouble(6, 50.00); // Ticket_Price
        pstmt.setInt(7, 2); // User_ID (Tayyab12)
        pstmt.executeUpdate();

        // Event 3: Available event (created by Tayyab12 - User_ID 2)
        pstmt.setString(1, "Available Event");
        pstmt.setString(2, "Family");
        pstmt.setString(3, "2024-12-31");
        pstmt.setString(4, "This event has available tickets");
        pstmt.setInt(5, 10); // Ticket_Count
        pstmt.setDouble(6, 25.00); // Ticket_Price
        pstmt.setInt(7, 2); // User_ID (Tayyab12)
        pstmt.executeUpdate();
    }

    @After
    public void tearDown() throws Exception {
        con.rollback(); // Rollback changes to maintain a clean state
    }

    @Test
    public void testPurchaseByOrganizer() throws Exception {
        TicketPurchase ticketPurchase = new TicketPurchase();
        boolean result = ticketPurchase.purchaseTicket(1, 1); // Organizer ID 1 trying to buy their own event ticket
        assertFalse("Organizer should not be able to purchase tickets for their own event.", result);
    }

    @Test
    public void testPurchaseSoldOutEvent() throws Exception {
        TicketPurchase ticketPurchase = new TicketPurchase();
        boolean result = ticketPurchase.purchaseTicket(2, 1); // Event 2 is sold out, User_ID 1 trying to buy
        assertFalse("Purchase should fail for a sold-out event.", result);
    }

    @Test
    public void testSuccessfulTicketPurchase() throws Exception {
        TicketPurchase ticketPurchase = new TicketPurchase();
        boolean result = ticketPurchase.purchaseTicket(3, 1); // Event 3 has available tickets
        assertTrue("Ticket purchase should succeed for an available event.", result);

        // Validate ticket count update
        PreparedStatement pstmt = con.prepareStatement("SELECT Ticket_Count FROM Events WHERE Event_ID = ?");
        pstmt.setInt(1, 3);
        ResultSet rs = pstmt.executeQuery();
        assertTrue("Event should exist.", rs.next());
        assertEquals("Ticket count should decrease by 1.", 9, rs.getInt("Ticket_Count"));

        // Validate ticket entry in the Tickets table
        pstmt = con.prepareStatement("SELECT COUNT(*) AS Count FROM Tickets WHERE Event_ID = ? AND User_ID = ?");
        pstmt.setInt(1, 3);
        pstmt.setInt(2, 1);
        rs = pstmt.executeQuery();
        assertTrue("Ticket entry should exist.", rs.next());
        assertEquals("One ticket should be recorded.", 1, rs.getInt("Count"));
    }

    @Test
    public void testPurchaseNonExistentEvent() throws Exception {
        TicketPurchase ticketPurchase = new TicketPurchase();
        boolean result = ticketPurchase.purchaseTicket(99, 1); // Non-existent event ID
        assertFalse("Purchase should fail for a non-existent event.", result);
    }
}
