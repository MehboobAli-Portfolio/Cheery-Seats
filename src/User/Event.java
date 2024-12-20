package User;
// Abstract class representing an event with ticket count and ticket price

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Event {
    protected String eventName;
    protected String eventDate;
    protected String eventDescription;
    protected String userId;
    protected int ticketCount;
    protected double ticketPrice; // New field for ticket price

    public Event(String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.userId = userId;
        this.ticketCount = ticketCount;
        this.ticketPrice = ticketPrice;
    }

    public abstract void saveEvent();
}

// Music Event
class MusicEvent extends Event {
    public MusicEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        super(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
    }

    @Override
    public void saveEvent() {
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, Ticket_Price, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Music");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setDouble(6, ticketPrice);
                pstmt.setString(7, userId);
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
    public SportsEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        super(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
    }

    @Override
    public void saveEvent() {
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, Ticket_Price, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Sports");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setDouble(6, ticketPrice);
                pstmt.setString(7, userId);
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
    public ArtEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        super(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
    }

    @Override
    public void saveEvent() {
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, Ticket_Price, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Art");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setDouble(6, ticketPrice);
                pstmt.setString(7, userId);
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
    public FamilyEvent(String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        super(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
    }

    @Override
    public void saveEvent() {
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String insertQuery = "INSERT INTO Events (Event_Name, Event_Type, Event_Date, Event_Description, Ticket_Count, Ticket_Price, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, eventName);
                pstmt.setString(2, "Family");
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDescription);
                pstmt.setInt(5, ticketCount);
                pstmt.setDouble(6, ticketPrice);
                pstmt.setString(7, userId);
                pstmt.executeUpdate();
                System.out.println("Family event saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving Family Event: " + e.getMessage());
        }
    }
}

