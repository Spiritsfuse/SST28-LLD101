public interface EligibilityRule {
    /**
     * Apply rule to profile. Return null if rule passes, otherwise return reason string.
     */
    String validate(StudentProfile s);
}
