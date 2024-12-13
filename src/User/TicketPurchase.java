package User;
import java.sql.*;
import javax.swing.*;
public class TicketPurchase {
    public boolean purchaseTicket(int eventId, int userId) {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            con.setAutoCommit(false); // Start transaction

            // Check if the user is the event organizer
            String organizerQuery = "SELECT User_ID FROM Events WHERE Event_ID = ?";
            PreparedStatement organizerStmt = con.prepareStatement(organizerQuery);
            organizerStmt.setInt(1, eventId);
            ResultSet organizerRs = organizerStmt.executeQuery();

            if (organizerRs.next() && organizerRs.getInt("User_ID") == userId) {
                JOptionPane.showMessageDialog(null, "You cannot purchase tickets for your own event!", "Purchase Denied", JOptionPane.ERROR_MESSAGE);
                return false; // Organizer cannot buy tickets
            }

            // Check ticket availability
            String checkQuery = "SELECT Ticket_Count FROM Events WHERE Event_ID = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, eventId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int ticketCount = rs.getInt("Ticket_Count");
                if (ticketCount <= 0) {
                    JOptionPane.showMessageDialog(null, "All tickets for this event have been purchased!", "Sold Out", JOptionPane.WARNING_MESSAGE);
                    return false; // No tickets available
                }

                // Decrement ticket count
                String updateQuery = "UPDATE Events SET Ticket_Count = Ticket_Count - 1 WHERE Event_ID = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                updateStmt.setInt(1, eventId);
                updateStmt.executeUpdate();

                // Add ticket purchase to the Tickets table
                String insertQuery = "INSERT INTO Tickets (Event_ID, User_ID) VALUES (?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                insertStmt.setInt(1, eventId);
                insertStmt.setInt(2, userId);
                insertStmt.executeUpdate();

                con.commit(); // Commit transaction

                // Notify organizer
                Notification notification = new Notification();
                notification.notifyOrganizer(eventId);

                return true; // Purchase successful
            } else {
                JOptionPane.showMessageDialog(null, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            try {
                Connection con = DatabaseConnection.getInstance().getConnection();
                con.rollback(); // Rollback transaction in case of failure
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
}


