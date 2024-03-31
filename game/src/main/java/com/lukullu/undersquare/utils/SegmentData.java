package com.lukullu.undersquare.utils;

import com.lukullu.undersquare.enums.SegmentRoles;
import com.lukullu.tbck.utils.Vec2;

import java.util.ArrayList;

public class SegmentData
{
    public ArrayList<Vec2> vertices;
    public int ArmorPoints;
    public SegmentRoles role;
    public transient boolean enabled = true;


}
