public abstract class Exporter {
    // Contract: exporters must accept null request and not throw runtime exceptions.
    // Subclasses should override exportNormalized and Exporter will normalize input.
    public final ExportResult export(ExportRequest req) {
        ExportRequest r = normalize(req);
        return exportNormalized(r);
    }

    private ExportRequest normalize(ExportRequest req) {
        if (req == null) return new ExportRequest("", "");
        String title = req.title == null ? "" : req.title;
        String body = req.body == null ? "" : req.body;
        return new ExportRequest(title, body);
    }

    protected abstract ExportResult exportNormalized(ExportRequest req);
}
