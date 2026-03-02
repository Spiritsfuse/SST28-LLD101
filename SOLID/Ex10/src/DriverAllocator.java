public class DriverAllocator implements DriverAllocationService {
    @Override
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}
