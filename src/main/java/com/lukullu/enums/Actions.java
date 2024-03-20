package com.lukullu.enums;

public enum Actions {
    FORWARD ("Forward"),
    BACKWARD ("Backward"),
    LEFT ("Left"),
    RIGHT ("Right");

    private final String name;

    private Actions(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
