package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import net.aether.utils.utils.reflection.Exposed;
import net.aether.utils.utils.reflection.Exposes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class SegmentData extends Polygon implements Serializable
{
    
    @Serial private static final long serialVersionUID = 3756687822214555217L;

    @Exposed public int ArmorPoints;
    @Exposed public SegmentRoles role;
    public boolean enabled = true;

    public SegmentData(ArrayList<Vec2> vertices) {
        super(vertices);
    }
    
    public String toString() {
        return "SegmentData{" + role + ", " + ArmorPoints + "ap, " + vertices.toString() + '}';
    }
}
