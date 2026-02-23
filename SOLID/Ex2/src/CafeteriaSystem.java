import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceRepository repo;
    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;
    private final InvoicePrinter printer;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(InvoiceRepository repo, TaxPolicy taxPolicy, DiscountPolicy discountPolicy, InvoicePrinter printer) {
        this.repo = repo;
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.printer = printer;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // Orchestrates pricing, formatting and persistence. Rules are injected.
    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        StringBuilder out = new StringBuilder();
        out.append(printer.formatInvoiceHeader(invId));

        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            out.append(printer.formatLine(item.name, l.qty, lineTotal));
        }

        double taxPct = taxPolicy.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountPolicy.discountAmount(customerType, subtotal, lines.size());

        double total = subtotal + tax - discount;

        out.append(printer.formatSubtotal(subtotal));
        out.append(printer.formatTax(taxPct, tax));
        out.append(printer.formatDiscount(discount));
        out.append(printer.formatTotal(total));

        String printable = InvoiceFormatter.identityFormat(out.toString());
        System.out.print(printable);

        repo.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + repo.countLines(invId) + ")");
    }
}
