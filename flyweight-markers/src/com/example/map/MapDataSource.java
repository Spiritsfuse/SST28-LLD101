package com.example.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates markers for demo/testing using Flyweight pattern.
 * 
 * Instead of creating a new MarkerStyle for each marker,
 * uses MarkerStyleFactory to obtain shared style instances.
 * 
 * With 3 shapes × 4 colors × 4 sizes × 2 filled options = 96 possible styles,
 * even with 30,000 markers, we only create at most 96 MarkerStyle objects.
 */
public class MapDataSource {

    private static final String[] SHAPES = {"PIN", "CIRCLE", "SQUARE"};
    private static final String[] COLORS = {"RED", "BLUE", "GREEN", "ORANGE"};
    private static final int[] SIZES = {10, 12, 14, 16};

    private final MarkerStyleFactory styleFactory = new MarkerStyleFactory();

    public List<MapMarker> loadMarkers(int count) {
        Random rnd = new Random(7);
        List<MapMarker> out = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            double lat = 12.9000 + rnd.nextDouble() * 0.2000;
            double lng = 77.5000 + rnd.nextDouble() * 0.2000;
            String label = "M-" + i;

            // Force many duplicates by choosing from small pools
            String shape = SHAPES[rnd.nextInt(SHAPES.length)];
            String color = COLORS[rnd.nextInt(COLORS.length)];
            int size = SIZES[rnd.nextInt(SIZES.length)];
            boolean filled = rnd.nextBoolean();

            // Use factory to get shared MarkerStyle (Flyweight pattern)
            MarkerStyle style = styleFactory.get(shape, color, size, filled);
            out.add(new MapMarker(lat, lng, label, style));
        }
        
        System.out.println("\n[MapDataSource] Loaded " + count + " markers");
        System.out.println("[MapDataSource] Unique styles created: " + styleFactory.cacheSize());
        return out;
    }
}
