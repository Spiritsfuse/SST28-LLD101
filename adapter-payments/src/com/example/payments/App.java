package com.example.payments;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Demo app showing Adapter pattern in action.
 * 
 * OrderService now depends only on PaymentGateway interface.
 * Adapters translate between the PaymentGateway interface and the specific SDK APIs.
 */
public class App {
    public static void main(String[] args) {
        Map<String, PaymentGateway> gateways = new HashMap<>();
        
        // Register adapters that wrap the incompatible SDKs
        gateways.put("fastpay", new FastPayAdapter(new FastPayClient()));
        gateways.put("safecash", new SafeCashAdapter(new SafeCashClient()));
        
        OrderService svc = new OrderService(gateways);

        System.out.println("=== Payment Adapter Demo ===\n");
        
        String id1 = svc.charge("fastpay", "cust-1", 1299);
        System.out.println("FastPay transaction: " + id1);
        
        String id2 = svc.charge("safecash", "cust-2", 1299);
        System.out.println("SafeCash transaction: " + id2);
        
        System.out.println("\nBoth payments processed successfully through unified PaymentGateway interface!");
    }
}

class FastPayAdapter implements PaymentGateway {

    private final FastPayClient fastPayClient;

    FastPayAdapter(FastPayClient fastPayClient) {
        this.fastPayClient = Objects.requireNonNull(fastPayClient, "fastPayClient");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId");
        return fastPayClient.payNow(customerId, amountCents);
    }
}

class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient safeCashClient;

    SafeCashAdapter(SafeCashClient safeCashClient) {
        this.safeCashClient = Objects.requireNonNull(safeCashClient, "safeCashClient");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId");
        SafeCashPayment payment = safeCashClient.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}
