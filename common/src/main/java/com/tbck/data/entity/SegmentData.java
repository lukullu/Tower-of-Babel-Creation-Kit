package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class SegmentData extends Polygon implements Serializable
{
    @Serial private static final long serialVersionUID = 3756687822214555217L;
    public int ArmorPoints; // TODO lukullu why this not armorPoints?
    public SegmentRoles role;
    public boolean enabled = true;
    public static boolean isValid = true;
    public ArrayList<SegmentData> neighborSegments;
    public SegmentData(ArrayList<Vec2> vertices) {
        super(vertices); enabled = true; isValid = true;
    }

    public HashSet<SegmentData> checkNeighborsRec(HashSet<SegmentData> visited)
    {
        if(visited.contains(this))
            return visited;

        visited.add(this);

        for (SegmentData neighbor : neighborSegments)
        {
            if(neighbor.isValid && neighbor.enabled)
                visited.addAll(neighbor.checkNeighborsRec(visited));
        }
        return visited;
    }

    public String toString() {
        return "SegmentData{" + role + ", " + ArmorPoints + "ap, " + vertices.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SegmentData that)) return false;
        return ArmorPoints == that.ArmorPoints && role == that.role && vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ArmorPoints, role, vertices);
    }
}
