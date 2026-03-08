package com.example.reports;

/**
 * Client that uses the Report abstraction.
 * Now depends on the Report interface, not concrete implementation.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
