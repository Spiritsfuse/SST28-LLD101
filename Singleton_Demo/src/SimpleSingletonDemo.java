package com.example.singleton;

/**
 * SimpleSingletonDemo demonstrates the Bill Pugh (Initialization-on-Demand Holder) pattern.
 * This is the recommended approach: lazy-loaded, thread-safe, no synchronization overhead.
 */
public class SimpleSingletonDemo {
    
    // Private constructor prevents direct instantiation
    private SimpleSingletonDemo() {
        System.out.println("  [Constructor] SimpleSingletonDemo instance created");
    }
    
    /**
     * Static inner class that holds the singleton instance.
     * This inner class is loaded ONLY when getInstance() is first called.
     */
    private static class Holder {
        // JVM guarantees thread-safe class initialization
        static final SimpleSingletonDemo INSTANCE = new SimpleSingletonDemo();
    }
    
    /**
     * Get the singleton instance. Thread-safe without explicit synchronization.
     */
    public static SimpleSingletonDemo getInstance() {
        return Holder.INSTANCE;
    }
    
    public void doWork(String taskName) {
        System.out.println("  Executing: " + taskName);
    }
    
    @Override
    public String toString() {
        return "SimpleSingletonDemo@" + Integer.toHexString(System.identityHashCode(this));
    }
    
    public static void main(String[] args) {
        System.out.println("\n=== SimpleSingletonDemo: Bill Pugh Pattern ===\n");
        
        System.out.println("1. Calling getInstance() for the first time...");
        SimpleSingletonDemo instance1 = SimpleSingletonDemo.getInstance();
        System.out.println("   Got instance: " + instance1);
        
        System.out.println("\n2. Calling getInstance() a second time...");
        SimpleSingletonDemo instance2 = SimpleSingletonDemo.getInstance();
        System.out.println("   Got instance: " + instance2);
        
        System.out.println("\n3. Are they the same object?");
        boolean same = (instance1 == instance2);
        System.out.println("   instance1 == instance2: " + same);
        System.out.println("   (Expected: true)");
        
        System.out.println("\n4. Using the singleton...");
        instance1.doWork("Task A");
        instance2.doWork("Task B");
        
        System.out.println("\n✓ Singleton pattern working correctly!");
        System.out.println("  Only one instance across all getInstance() calls.\n");
    }
}
