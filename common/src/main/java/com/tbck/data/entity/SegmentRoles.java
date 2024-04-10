package com.tbck.data.entity;

public enum SegmentRoles
{
    // Body
    SEGMENT (false, "segment.png"),
    ARMOR (false, "armor.png"),
    WEAK_POINT (false, "weak_point.png"),

    // Organs
    HEART (false, "heart.png"),
    HEAD (false, "head.png"),

    // Util
    GUN (true, "gun.png"),
    TURRET (true, "turret.png");

    boolean isDirectional = false;
    String texture_url = "nil"; // ToDo make actual Texture Class

    SegmentRoles(boolean isDirectional, String texture_url)
    {
        this.isDirectional = isDirectional;
        this.texture_url = texture_url;
    }
}
