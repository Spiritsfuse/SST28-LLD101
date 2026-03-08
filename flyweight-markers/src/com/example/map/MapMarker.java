package com.example.map;

/**
 * MapMarker: Stores extrinsic (unique) state and references shared intrinsic state.
 * 
 * Extrinsic state (unique per marker): lat, lng, label
 * Intrinsic state (shared): MarkerStyle (obtained from MarkerStyleFactory)
 * 
 * By accepting a shared MarkerStyle instead of creating a new one,
 * thousands of markers can reference the same style object.
 */
public class MapMarker {

    private final double lat;
    private final double lng;
    private final String label;

    // Shared reference to immutable MarkerStyle (Flyweight)
    private final MarkerStyle style;

    public MapMarker(double lat, double lng, String label, MarkerStyle style) {
        this.lat = lat;
        this.lng = lng;
        this.label = label;
        this.style = style;
    }

    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public String getLabel() { return label; }
    public MarkerStyle getStyle() { return style; }
}
