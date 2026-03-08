package com.example.reports;

/**
 * Starter demo - NOW REFACTORED to use Proxy pattern.
 *
 * REFACTORED BEHAVIOR:
 * - Client code uses ReportProxy
 * - Unauthorized access is blocked
 * - Real report loads lazily (only when authorized user requests)
 * - Repeated views through same proxy reuse the cached real report
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        // Use ReportProxy instead of ReportFile
        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo (with Proxy Pattern) ===\n");

        System.out.println("--- Student accessing PUBLIC report ---");
        viewer.open(publicReport, student);
        System.out.println();

        System.out.println("--- Student accessing FACULTY report (should be denied) ---");
        viewer.open(facultyReport, student);
        System.out.println();

        System.out.println("--- Faculty accessing FACULTY report (should succeed) ---");
        viewer.open(facultyReport, faculty);
        System.out.println();

        System.out.println("--- Admin accessing ADMIN report (first time) ---");
        viewer.open(adminReport, admin);
        System.out.println();

        System.out.println("--- Admin accessing ADMIN report (second time - cached) ---");
        viewer.open(adminReport, admin);
    }
}
