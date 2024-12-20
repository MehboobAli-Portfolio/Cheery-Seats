package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Notification {
    public void notifyOrganizer(int eventId) {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();

            // Query to get the organizer's email based on the event ID
            String query = "SELECT u.User_Email FROM User u JOIN Events e ON u.User_ID = e.User_ID WHERE e.Event_ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, eventId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String organizerEmail = rs.getString("User_Email");
                JOptionPane.showMessageDialog(null,"Notification sent to organizer: " + organizerEmail);
                // Integrate actual email service here for real notifications (e.g., using JavaMail API)
            } else {
                JOptionPane.showMessageDialog(null,"No organizer found for the event ID: " + eventId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

