public class EvaluationPipeline {
    private final Rubric rubric;
    private final PlagiarismScorer plagiarismScorer;
    private final CodeScorer codeScorer;
    private final ReportPublisher reportPublisher;

    public EvaluationPipeline(
            Rubric rubric,
            PlagiarismScorer plagiarismScorer,
            CodeScorer codeScorer,
            ReportPublisher reportPublisher) {
        this.rubric = rubric;
        this.plagiarismScorer = plagiarismScorer;
        this.codeScorer = codeScorer;
        this.reportPublisher = reportPublisher;
    }

    public void evaluate(Submission sub) {
        int plag = plagiarismScorer.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = codeScorer.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reportPublisher.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
