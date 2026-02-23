public class DisciplinaryRule implements EligibilityRule {
    @Override
    public String validate(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) return "disciplinary flag present";
        return null;
    }
}
