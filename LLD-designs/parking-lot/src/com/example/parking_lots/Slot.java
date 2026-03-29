package com.example.parking_lots;
import java.util.Map;
import java.util.UUID;

public class Slot {
    private final UUID slotId;
    private final int level;
    private final SlotType slotType;
    private boolean occupied;
    private final Map<Gate, Integer> distanceFromGate;
    public Slot(int level, SlotType slotType, Map<Gate, Integer> distanceFromGate) {
        this.slotId = UUID.randomUUID();
        this.level = level;
        this.slotType = slotType;
        this.occupied = false;
        this.distanceFromGate = distanceFromGate;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public boolean isOccupied() {
        return this.occupied;
    }
    public UUID getSlotId() {
        return this.slotId;
    }
    public int getLevel() {
        return this.level;
    }
    public SlotType getSlotType() {
        return this.slotType;
    }
    public Map<Gate, Integer> getDistanceFromGate() {
        return this.distanceFromGate;
    }
}
