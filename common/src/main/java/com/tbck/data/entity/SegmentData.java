package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class SegmentData extends Polygon implements Serializable
{

    static {
        FieldButton.GENERATOR_REGISTRY.put(Vec2.class, oldValue -> {
            String input = JOptionPane.showInputDialog(null, "Input Vec2", oldValue);
            System.out.println(input);
            return new Vec2(0, 0);
        });
    }

    @Serial private static final long serialVersionUID = 3756687822214555217L;
    public int ArmorPoints; // TODO lukullu why this not armorPoints?
    public SegmentRoles role;
    public boolean enabled = true;
    public static boolean isValid = true;
    public ArrayList<SegmentData> neighborSegments;

    public int armorPoints = 0;
    public SegmentRoles role = SegmentRoles.SEGMENT;
    @Exposed(hidden = true) public boolean enabled = true;

    @Exposed(as = "rolename")
    public String getRole() { return role.name().toLowerCase(Locale.ROOT); }

    public Vec2 testVec = new Vec2(69, 420);

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
        return "SegmentData{" + role + ", " + armorPoints + "ap, " + vertices.toString() + '}';
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
