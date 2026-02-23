public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    /**
     * Send a notification. Implementations may throw NotificationException
     * to indicate channel-specific validation or delivery failures.
     */
    public abstract void send(Notification n) throws NotificationException;
}
