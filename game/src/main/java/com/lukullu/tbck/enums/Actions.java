package com.lukullu.tbck.enums;

public enum Actions {
    FORWARD ("Forward"),
    BACKWARD ("Backward"),
    LEFT ("Left"),
    RIGHT ("Right"),
    ROTATE_CLOCKWISE ("RotateClockwise"),
    ROTATE_COUNTERCLOCKWISE ("RotateCounterClockwise"),
    SCALE_UP ("ScaleUp"),
    SCALE_DOWN ("ScaleDown"),
    SLOWMO ("SlowMo"),
    CYCLE_CAMERA ("Cycle Camera"),
    DEBUGTOGGLE ("DebugEnum");

    private final String name;

    private Actions(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
