
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Attempts to create multiple instances via reflection.
 * Starter allows this. After fix, it must fail.
 */
public class ReflectionAttack {

    public static void main(String[] args) throws Exception {
        MetricsRegistry singleton = MetricsRegistry.getInstance();

        Constructor<MetricsRegistry> ctor = MetricsRegistry.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        try {
            MetricsRegistry evil = ctor.newInstance();
            System.out.println("Singleton identity: " + System.identityHashCode(singleton));
            System.out.println("Evil identity     : " + System.identityHashCode(evil));
            System.out.println("Same object?      : " + (singleton == evil));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            if (ex.getCause() != null) {
                System.out.println("Reflection blocked: " + ex.getCause().getMessage());
            } else {
                System.out.println("Reflection blocked: " + ex.getMessage());
            }
        }
    }
}
