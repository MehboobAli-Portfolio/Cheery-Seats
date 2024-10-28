package User;
import java.sql.*;

// Abstract class representing an event with ticket count
abstract class Event {
    protected String eventName;
    protected String eventDate;
    protected String eventDescription;
    protected String userId;
    protected int ticketCount;  // New field for ticket count

    public Event(String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.userId = userId;
        this.ticketCount = ticketCount;
    }

    public abstract void saveEvent();
}

// Music Event
class MusicEvent extends Event {
    public MusicEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        super(eventName, eventDate, eventDescription, userId, ticketCount);
    }

    @Override
    public void saveEvent() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, User_ID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Music");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setString(6, userId);
                pstmt.executeUpdate();
                System.out.println("Music event saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving Music Event: " + e.getMessage());
        }
    }
}

// Sports Event
class SportsEvent extends Event {
    public SportsEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        super(eventName, eventDate, eventDescription, userId, ticketCount);
    }

    @Override
    public void saveEvent() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, User_ID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Sports");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setString(6, userId);
                pstmt.executeUpdate();
                System.out.println("Sports event saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving Sports Event: " + e.getMessage());
        }
    }
}

// Art Event
class ArtEvent extends Event {
    public ArtEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        super(eventName, eventDate, eventDescription, userId, ticketCount);
    }

    @Override
    public void saveEvent() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, User_ID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Art");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setString(6, userId);
                pstmt.executeUpdate();
                System.out.println("Art event saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving Art Event: " + e.getMessage());
        }
    }
}

// Family Event
class FamilyEvent extends Event {
    public FamilyEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        super(eventName, eventDate, eventDescription, userId, ticketCount);
    }

    @Override
    public void saveEvent() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, User_ID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Family");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setString(6, userId);
                pstmt.executeUpdate();
                System.out.println("Family event saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving Family Event: " + e.getMessage());
        }
    }
}

// Factory class
public class EventFactory {
    public static Event createEvent(String type, String eventName, String eventDate, String eventDescription, String userId, int ticketCount) {
        switch (type) {
            case "Music":
                return new MusicEvent(eventName, eventDate, eventDescription, userId, ticketCount);
            case "Sports":
                return new SportsEvent(eventName, eventDate, eventDescription, userId, ticketCount);
            case "Art":
                return new ArtEvent(eventName, eventDate, eventDescription, userId, ticketCount);
            case "Family":
                return new FamilyEvent(eventName, eventDate, eventDescription, userId, ticketCount);
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}

