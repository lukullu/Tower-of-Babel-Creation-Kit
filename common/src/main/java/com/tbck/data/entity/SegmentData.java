package com.tbck.data.entity;



import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class SegmentData extends Polygon
{
    public int ArmorPoints;
    public SegmentRoles role;
    public transient boolean enabled = true;


    public SegmentData(ArrayList<Vec2> vertices) {
        super(vertices);
    }
}
