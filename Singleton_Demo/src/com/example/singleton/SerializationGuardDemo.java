package com.example.singleton;

import java.io.*;

/**
 * SerializationGuardDemo shows how singleton pattern can be broken via serialization
 * and how to defend against it using readResolve().
 */
public class SerializationGuardDemo implements Serializable {
    
    private SerializationGuardDemo() {
        System.out.println("  [Constructor] SerializationGuardDemo instance created");
    }
    
    private static class Holder {
        static final SerializationGuardDemo INSTANCE = new SerializationGuardDemo();
    }
    
    public static SerializationGuardDemo getInstance() {
        return Holder.INSTANCE;
    }
    
    /**
     * GUARD: When deserializing, return the existing singleton instead of creating a new one.
     * This method is called automatically by ObjectInputStream after reading the object.
     */
    private Object readResolve() {
        System.out.println("  [readResolve] Returning existing singleton on deserialization");
        return getInstance();
    }
    
    public String getIdentity() {
        return "SerializationGuardDemo@" + System.identityHashCode(this);
    }
    
    public static void main(String[] args) {
        System.out.println("\n=== SerializationGuardDemo: Serialization Attack Protection ===\n");
        
        System.out.println("1. Getting singleton and its identity...");
        SerializationGuardDemo original = SerializationGuardDemo.getInstance();
        System.out.println("   Original: " + original.getIdentity());
        
        System.out.println("\n2. Serializing the singleton to bytes...");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(original);
            System.out.println("   Serialized (" + baos.size() + " bytes)");
        } catch (IOException e) {
            System.out.println("   Error: " + e.getMessage());
            return;
        }
        
        System.out.println("\n3. Deserializing from bytes...");
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        SerializationGuardDemo deserialized;
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            deserialized = (SerializationGuardDemo) ois.readObject();
            System.out.println("   Deserialized: " + deserialized.getIdentity());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("   Error: " + e.getMessage());
            return;
        }
        
        System.out.println("\n4. Verifying singleton guarantee...");
        boolean same = (original == deserialized);
        System.out.println("   original == deserialized: " + same);
        if (same) {
            System.out.println("   ✓ Deserialization returned the existing singleton (readResolve worked)");
        } else {
            System.out.println("   ✗ Deserialization created a NEW instance (BROKEN!)");
        }
        
        System.out.println("\n✓ Singleton protected against serialization attacks!\n");
    }
}
