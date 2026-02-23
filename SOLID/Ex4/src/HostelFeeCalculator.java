import java.util.*;

public class HostelFeeCalculator {
    private final BookingRepository repo;
    private final RoomPricer roomPricer;
    private final AddOnPricer addOnPricer;
    private final HostelPrinter printer;

    public HostelFeeCalculator(BookingRepository repo, RoomPricer roomPricer, AddOnPricer addOnPricer, HostelPrinter printer) {
        this.repo = repo;
        this.roomPricer = roomPricer;
        this.addOnPricer = addOnPricer;
        this.printer = printer;
    }

    // Orchestrates calculation and persistence; pricing rules are injected.
    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        printer.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        double base = roomPricer.monthlyFor(req.roomType);

        double add = 0.0;
        for (AddOn a : req.addOns) {
            add += addOnPricer.priceFor(a);
        }

        return new Money(base + add);
    }
}
