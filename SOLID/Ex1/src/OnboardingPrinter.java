public class OnboardingPrinter {
    public void printInput(String raw) { System.out.println("INPUT: " + raw); }

    public void printErrors() {
        System.out.println("ERROR: cannot register");
    }

    public void printErrorItem(String e) { System.out.println("- " + e); }

    public void printOkCreated(String id) { System.out.println("OK: created student " + id); }

    public void printSavedTotal(int total) { System.out.println("Saved. Total students: " + total); }

    public void printConfirmationHeader() { System.out.println("CONFIRMATION:"); }

    public void printRecord(StudentRecord r) { System.out.println(r); }
}
