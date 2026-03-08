package com.example.reports;

/**
 * Proxy: Controls access and lazy-loads the real report.
 * 
 * Responsibilities:
 * - Access control: check if user is authorized
 * - Lazy loading: only create RealReport when needed
 * - Caching: reuse the loaded report for subsequent calls
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    // Lazy-loaded real subject (cached)
    private RealReport realReport;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
        System.out.println("[ReportProxy] Proxy created for " + reportId + " (lightweight, no load yet)");
    }

    @Override
    public void display(User user) {
        // Access control
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("[ReportProxy] ACCESS DENIED for user " + user.getName() 
                    + " (role=" + user.getRole() + ") to report " + reportId 
                    + " (classification=" + classification + ")");
            return;
        }

        System.out.println("[ReportProxy] Access granted for " + user.getName());

        // Lazy loading with caching
        if (realReport == null) {
            System.out.println("[ReportProxy] Lazy-loading real report for the first time...");
            realReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[ReportProxy] Reusing cached real report (no reload)");
        }

        // Delegate to real subject
        realReport.display(user);
    }
}
