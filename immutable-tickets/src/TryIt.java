import java.util.List;

/**
 * Starter demo that shows why mutability is risky.
 *
 * After refactor:
 * - direct mutation should not compile (no setters)
 * - external modifications to tags should not affect the ticket
 * - service "updates" should return a NEW ticket instance
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter immutable updates (new objects): " + escalated);
        System.out.println("Original still unchanged            : " + t);

        List<String> tags = escalated.getTags();
        tags.add("HACKED_FROM_OUTSIDE");
        System.out.println("\nAfter external list mutation attempt: " + escalated);

        System.out.println("\nObservation: ticket did not change because getter returns a defensive copy.");
    }
}
