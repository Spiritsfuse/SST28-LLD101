import java.util.List;

public interface DiscountPolicy {
    double discountAmount(String customerType, double subtotal, int distinctLines);
}
