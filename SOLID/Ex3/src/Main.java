import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);

        // Wire rules in order matching original checks
        List<EligibilityRule> rules = List.of(
            new DisciplinaryRule(),
            new CgrRule(8.0),
            new AttendanceRule(75),
            new CreditsRule(20)
        );

        ReportPrinter printer = new ReportPrinter();
        EligibilityStore store = new FakeEligibilityStore();

        EligibilityEngine engine = new EligibilityEngine(rules, printer, store);
        engine.runAndPrint(s);
    }
}
