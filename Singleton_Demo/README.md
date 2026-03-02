# Singleton Design Pattern — Complete Deep Dive

## What is a Singleton?

A **Singleton** is a **creational design pattern** ensuring a class has only **one instance** throughout the application lifetime, providing a **global point of access** to that instance.

Use Cases:
- Database connection pools
- Logger(s)
- Configuration managers
- Caching layers

## Key characteristics

1. **Private Constructor**: Prevents direct instantiation via `new`.
2. **Static Instance**: A static field holds the single, shared instance.
3. **Public Static Accessor**: `getInstance()` method returns the singleton instance.

## Implementation Approaches

### 1. Eager Initialization
Instance is created at class load time.

```java
public class Database {
    private static final Database INSTANCE = new Database();
    
    private Database() { }
    
    public static Database getInstance() {
        return INSTANCE;
    }
}
```

**Pros**: Simple, inherently thread-safe.
**Cons**: Instance created whether used or not (wastes resources).

### 2. Lazy Initialization — Synchronized Method (Naive)
Thread-safety via method synchronization.

```java
public class Database {
    private static Database instance;
    
    private Database() { }
    
    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
```

**Pros**: Lazy creation.
**Cons**: Every call synchronized (performance overhead).

### 3. Double-Checked Locking (DCL)
Optimize synchronized method with a check outside the lock.

```java
public class Database {
    private static volatile Database instance;
    
    private Database() { }
    
    public static Database getInstance() {
        if (instance == null) {  // check 1
            synchronized (Database.class) {
                if (instance == null) {  // check 2
                    instance = new Database();
                }
            }
        }
        return instance;
    }
}
```

**Why `volatile`?**
Ensures memory visibility: all threads see the most recent instance reference safely.

**Pros**: Lazy, minimal synchronization overhead.
**Cons**: Slightly more complex, `volatile` required.

### 4. Initialization-on-Demand Holder (Bill Pugh Singleton) ⭐ Recommended
Use inner static class to enable lazy, thread-safe initialization via JVM guarantees.

```java
public class Database {
    private Database() { }
    
    private static class Holder {
        static final Database INSTANCE = new Database();
    }
    
    public static Database getInstance() {
        return Holder.INSTANCE;
    }
}
```

**Why this works:**
- JVM loads `Holder` class only when `getInstance()` is first called.
- Class initialization is thread-safe by JVM guarantee.
- No need for `volatile` or manual synchronization.

**Pros**: Lazy, thread-safe, simple, no synchronization overhead.
**Cons**: None significant.

### 5. Enum Singleton (Best for Serialization Protection)
Java enums provide builtin serialization + reflection safety.

```java
public enum Database {
    INSTANCE;
    
    public void query(String sql) {
        System.out.println("Query: " + sql);
    }
}
```

Usage: `Database.INSTANCE.query(...)`

**Pros**: Handles serialization & reflection automatically, most concise, idiomatically Java.
**Cons**: Different usage pattern than `getInstance()`.

## Threats to singleton guarantee

### 1. Reflection
```java
Constructor<Database> ctor = Database.class.getDeclaredConstructor();
ctor.setAccessible(true);
Database evil = ctor.newInstance();  // Creates a second instance!
```

**Guard:** Check in constructor if instance already exists, throw exception.

### 2. Serialization
```java
ObjectInputStream ois = new ObjectInputStream(...);
Database deserialized = (Database) ois.readObject();  // New instance!
```

**Guard:** Implement `readResolve()` to return existing singleton.

```java
private Object readResolve() {
    return getInstance();
}
```

### 3. Cloning
Some singleton classes might be Cloneable, exposing a copy method.

**Guard:** Override `clone()` to return singleton.

```java
@Override
protected Object clone() {
    return getInstance();
}
```

## When NOT to use Singleton

- Multiple instances needed (e.g., database connections with different credentials).
- Testing requires substituting implementations (prefer dependency injection).
- Stateless shared utility (use static methods or pure utility class).
- Objects that must be garbage collected when unused.

## Singleton vs Dependency Injection

### Singleton Pattern (Static access)
```java
// Singleton usage
Database db = Database.getInstance();
db.query("SELECT * FROM users");
```

### Dependency Injection (Loose coupling, testable)
```java
// Injected dependency
class UserService {
    private final Database db;
    
    public UserService(Database db) {
        this.db = db;  // Can inject test mock
    }
    
    public void fetchUsers() {
        db.query("SELECT * FROM users");
    }
}
```

**Best practice:** Use **DI with a singleton backing store**, not raw singletons in business logic.

## Testing implications

Singletons make testing harder because:
- Global state persists across test runs.
- Hard to isolate one test from another.
- Mock/stub injection is cumbersome.

**Solution:** Use dependency injection + a testing-friendly singleton factory.

## Conclusion

- **Recommended**: Initialization-on-Demand Holder for standard scenarios.
- **For serialization+reflection-safe**: Use Enum singleton.
- **For general architecture**: Prefer DI + singleton backing store over raw singletons.

