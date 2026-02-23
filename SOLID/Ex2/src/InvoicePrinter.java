public class InvoicePrinter {
    public String formatInvoiceHeader(String invId) {
        return "Invoice# " + invId + "\n";
    }

    public String formatLine(String name, int qty, double lineTotal) {
        return String.format("- %s x%d = %.2f\n", name, qty, lineTotal);
    }

    public String formatSubtotal(double subtotal) { return String.format("Subtotal: %.2f\n", subtotal); }
    public String formatTax(double pct, double tax) { return String.format("Tax(%.0f%%): %.2f\n", pct, tax); }
    public String formatDiscount(double discount) { return String.format("Discount: -%.2f\n", discount); }
    public String formatTotal(double total) { return String.format("TOTAL: %.2f\n", total); }
}
