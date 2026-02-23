import java.util.*;

import java.util.*;

public class EligibilityEngine {
    private final List<EligibilityRule> rules;
    private final ReportPrinter printer;
    private final EligibilityStore store;

    public EligibilityEngine(List<EligibilityRule> rules, ReportPrinter printer, EligibilityStore store) {
        this.rules = rules;
        this.printer = printer;
        this.store = store;
    }

    public void runAndPrint(StudentProfile s) {
        EligibilityEngineResult r = evaluate(s);
        printer.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (EligibilityRule rule : rules) {
            String reason = rule.validate(s);
            if (reason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(reason);
                break; // preserve original behaviour: stop at first failing rule
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
