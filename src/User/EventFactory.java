package User;
public class EventFactory {
    public static Event createEvent(String type, String eventName, String eventDate, String eventDescription, String userId, int ticketCount, double ticketPrice) {
        switch (type) {
            case "Music":
                return new MusicEvent(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
            case "Sports":
                return new SportsEvent(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
            case "Art":
                return new ArtEvent(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
            case "Family":
                return new FamilyEvent(eventName, eventDate, eventDescription, userId, ticketCount, ticketPrice);
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
