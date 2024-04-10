package com.tbck.data.entity;

public enum SegmentRoles
{
    // Body
    SEGMENT (new RoleAttribute(false, "segment.png")),
    ARMOR (new RoleAttribute(false, "armor.png")),
    WEAKPOINT (new RoleAttribute(false, "weakpoint.png")),

    // Organs
    HEART (new RoleAttribute(false, "heart.png")),
    HEAD (new RoleAttribute(false, "head.png")),

    // Util
    GUN (new RoleAttribute(true, "gun.png")),
    TURRET (new RoleAttribute(true, "turret.png"));

    private final RoleAttribute attribute;

    SegmentRoles(RoleAttribute attribute)
    {
        this.attribute = attribute;
    }

    public RoleAttribute getAttributes()
    {
        return attribute;
    }

    public static class RoleAttribute
    {
        boolean isDirectional = false;
        String texture_url = "nil"; // ToDo make actual Texture Class

        RoleAttribute(boolean isDirectional, String texture_url)
        {
            this.isDirectional = isDirectional;
            this.texture_url = texture_url;
        }
    }
}
