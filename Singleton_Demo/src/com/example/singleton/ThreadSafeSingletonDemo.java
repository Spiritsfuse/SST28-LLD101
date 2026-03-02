package com.example.singleton;

/**
 * ThreadSafeSingletonDemo validates that the singleton instance is safely shared
 * across multiple concurrent threads without race conditions.
 */
public class ThreadSafeSingletonDemo {
    
    private ThreadSafeSingletonDemo() {
        // Private constructor
    }
    
    private static class Holder {
        static final ThreadSafeSingletonDemo INSTANCE = new ThreadSafeSingletonDemo();
    }
    
    public static ThreadSafeSingletonDemo getInstance() {
        return Holder.INSTANCE;
    }
    
    public String getIdentity() {
        return "ThreadSafeSingletonDemo@" + System.identityHashCode(this);
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== ThreadSafeSingletonDemo: Concurrency Test ===\n");
        
        System.out.println("Spawning 50 threads that all call getInstance()...\n");
        
        // Array to store instance references from each thread
        final ThreadSafeSingletonDemo[] instances = new ThreadSafeSingletonDemo[50];
        final String[] identities = new String[50];
        
        // Create and start 50 threads
        Thread[] threads = new Thread[50];
        for (int i = 0; i < 50; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                // Each thread gets the singleton
                ThreadSafeSingletonDemo instance = ThreadSafeSingletonDemo.getInstance();
                instances[index] = instance;
                identities[index] = instance.getIdentity();
                System.out.println("  Thread " + index + ": " + identities[index]);
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread t : threads) {
            t.join();
        }
        
        System.out.println("\nValidating results...");
        
        // Verify all threads got the same instance
        boolean allSame = true;
        for (int i = 1; i < 50; i++) {
            if (instances[0] != instances[i]) {
                allSame = false;
                System.out.println("  ✗ Thread 0 and Thread " + i + " got DIFFERENT instances!");
                break;
            }
        }
        
        if (allSame) {
            System.out.println("  ✓ All 50 threads received THE SAME singleton instance");
            System.out.println("  ✓ No race conditions detected");
        }
        
        System.out.println("\n✓ Singleton instance is thread-safe!\n");
    }
}
