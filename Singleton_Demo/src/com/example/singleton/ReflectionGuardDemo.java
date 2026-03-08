package com.example.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * ReflectionGuardDemo shows how the singleton pattern can be attacked through reflection
 * and how to defend against it.
 * 
 * UNGUARDED: A reflected constructor allows creating a second instance (THREAT).
 * GUARDED: Constructor checks if instance already created and throws exception (SAFE).
 */
public class ReflectionGuardDemo {
    
    // Flag to track if an instance has already been created
    private static boolean instantiated = false;
    
    private ReflectionGuardDemo() {
        // GUARD: Check if instance was already created
        synchronized (ReflectionGuardDemo.class) {
            if (instantiated) {
                throw new IllegalStateException(
                    "Cannot create multiple instances of ReflectionGuardDemo. " +
                    "Singleton pattern violated via reflection!"
                );
            }
            instantiated = true;
        }
        System.out.println("  [Constructor] ReflectionGuardDemo instance created");
    }
    
    private static class Holder {
        static final ReflectionGuardDemo INSTANCE = new ReflectionGuardDemo();
    }
    
    public static ReflectionGuardDemo getInstance() {
        return Holder.INSTANCE;
    }
    
    public String getIdentity() {
        return "ReflectionGuardDemo@" + System.identityHashCode(this);
    }
    
    public static void main(String[] args) {
        System.out.println("\n=== ReflectionGuardDemo: Reflection Attack Protection ===\n");
        
        System.out.println("1. Getting singleton via normal getInstance()...");
        ReflectionGuardDemo instance1 = ReflectionGuardDemo.getInstance();
        System.out.println("   Got instance: " + instance1.getIdentity());
        
        System.out.println("\n2. Attempting to create a second instance via reflection...");
        try {
            Constructor<ReflectionGuardDemo> ctor = 
                ReflectionGuardDemo.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            System.out.println("   Constructor found and made accessible");
            
            System.out.println("   Calling newInstance()...");
            ReflectionGuardDemo instance2 = ctor.newInstance();
            
            // Should not reach here
            System.out.println("   ✗ FAILED: Reflection created a second instance!");
            System.out.println("   ✗ instance2: " + instance2.getIdentity());
        } catch (InstantiationException | InvocationTargetException e) {
            if (e.getCause() != null) {
                System.out.println("   ✓ BLOCKED: " + e.getCause().getMessage());
            } else {
                System.out.println("   ✓ BLOCKED: " + e.getClass().getSimpleName());
            }
        } catch (NoSuchMethodException | IllegalAccessException | SecurityException e) {
            System.out.println("   ✓ BLOCKED: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n3. Verifying singleton is still intact...");
        ReflectionGuardDemo instance3 = ReflectionGuardDemo.getInstance();
        System.out.println("   Current singleton: " + instance3.getIdentity());
        System.out.println("   Still same as original? " + (instance1 == instance3));
        
        System.out.println("\n✓ Singleton protected against reflection attacks!\n");
    }
}
