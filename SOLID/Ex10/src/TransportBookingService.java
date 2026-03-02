public class TransportBookingService {
    private final DistanceService distanceService;
    private final DriverAllocationService driverAllocationService;
    private final PaymentService paymentService;
    private final FarePolicy farePolicy;

    public TransportBookingService(
            DistanceService distanceService,
            DriverAllocationService driverAllocationService,
            PaymentService paymentService,
            FarePolicy farePolicy) {
        this.distanceService = distanceService;
        this.driverAllocationService = driverAllocationService;
        this.paymentService = paymentService;
        this.farePolicy = farePolicy;
    }

    public void book(TripRequest req) {
        double km = distanceService.km(req.from, req.to);
        System.out.println("DistanceKm=" + km);

        String driver = driverAllocationService.allocate(req.studentId);
        System.out.println("Driver=" + driver);

        double fare = farePolicy.fareForKm(km);

        String txn = paymentService.charge(req.studentId, fare);
        System.out.println("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        System.out.println("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
