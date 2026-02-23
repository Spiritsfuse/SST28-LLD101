import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    @Override
    protected ExportResult exportNormalized(ExportRequest req) {
        // Instead of throwing for long bodies, truncate to preserve LSP (no tightened preconditions).
        String body = req.body == null ? "" : req.body;
        if (body.length() > 20) body = body.substring(0, 20);
        String fakePdf = "PDF(" + req.title + "):" + body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
