package com.example.pen;

public final class PenFactory {
    private PenFactory() {
    }

    public static Pen createPen(String penTypeValue, String mechanismValue, String color, boolean withGrip) {
        PenType penType = PenType.fromInput(penTypeValue);
        MechanismType mechanismType = MechanismType.fromInput(mechanismValue);

        RefillStrategy refillStrategy = new SimpleRefill();
        StartStrategy startStrategy = mechanismType == MechanismType.CLICK ? new ClickStart() : new CapStart();

        Pen pen = createBasePen(penType, color, refillStrategy, startStrategy);
        if (withGrip) {
            return new GripDecorator(pen);
        }
        return pen;
    }

    private static Pen createBasePen(PenType penType, String color, RefillStrategy refillStrategy, StartStrategy startStrategy) {
        switch (penType) {
            case BALL:
                return new BallPen(color, refillStrategy, startStrategy);
            case GEL:
                return new GelPen(color, refillStrategy, startStrategy);
            case INK:
            default:
                return new InkPen(color, refillStrategy, startStrategy);
        }
    }
}
