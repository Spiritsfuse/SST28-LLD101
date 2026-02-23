import java.util.*;

public class OnboardingService {
    private final StudentRepository repo;
    private final Parser parser;
    private final Validator validator;
    private final OnboardingPrinter printer;

    public OnboardingService(StudentRepository repo, Parser parser, Validator validator, OnboardingPrinter printer) {
        this.repo = repo;
        this.parser = parser;
        this.validator = validator;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        StudentInput in = parser.parse(raw);
        List<String> errors = validator.validate(in);

        if (!errors.isEmpty()) {
            printer.printErrors();
            for (String e : errors) printer.printErrorItem(e);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());
        StudentRecord rec = new StudentRecord(id, in.name, in.email, in.phone, in.program);
        repo.save(rec);

        printer.printOkCreated(id);
        printer.printSavedTotal(repo.count());
        printer.printConfirmationHeader();
        printer.printRecord(rec);
    }
}
