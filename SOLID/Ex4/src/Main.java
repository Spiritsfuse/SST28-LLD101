import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));

        BookingRepository repo = new FakeBookingRepo();
        RoomPricer roomPricer = new DefaultRoomPricer();
        AddOnPricer addOnPricer = new DefaultAddOnPricer();
        HostelPrinter printer = new HostelPrinter();

        HostelFeeCalculator calc = new HostelFeeCalculator(repo, roomPricer, addOnPricer, printer);
        calc.process(req);
    }
}
