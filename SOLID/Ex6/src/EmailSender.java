public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Notification n) throws NotificationException {
        // Avoid silently changing meaning; if body is long, truncate for transport but log it.
        String body = n.body == null ? "" : n.body;
        boolean truncated = false;
        if (body.length() > 40) {
            body = body.substring(0, 40);
            truncated = true;
        }
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + body);
        audit.add("email sent");
        if (truncated) audit.add("email truncated");
    }
}
