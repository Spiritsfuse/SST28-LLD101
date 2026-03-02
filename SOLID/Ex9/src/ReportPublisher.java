public interface ReportPublisher {
    String write(Submission submission, int plagiarismScore, int codeScore);
}