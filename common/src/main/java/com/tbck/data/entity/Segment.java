package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import net.aether.utils.utils.reflection.Exposed;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Segment extends Polygon implements Serializable
{

    @Serial private static final long serialVersionUID = 3756687822214555217L;
    public int ArmorPoints; // TODO lukullu why this not armorPoints?
    public SegmentRoles role = SegmentRoles.SEGMENT;
    @Exposed(hidden = true) public boolean enabled = true;
    public static boolean isValid = true;
    public ArrayList<Segment> neighborSegments;
    public int armorPoints = 0;

    public Segment(ArrayList<Vec2> vertices) {
        super(vertices); enabled = true; isValid = true;
    }

    public HashSet<Segment> checkNeighborsRec(HashSet<Segment> visited)
    {
        if(visited.contains(this))
            return visited;

        visited.add(this);

        for (Segment neighbor : neighborSegments)
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
        if (!(o instanceof Segment that)) return false;
        return ArmorPoints == that.ArmorPoints && enabled == that.enabled && armorPoints == that.armorPoints && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ArmorPoints, role, enabled, armorPoints);
    }
}

/* Kilix Editor Demo Code
static {
        FieldButton.GENERATOR_REGISTRY.put(Vec2.class, oldValue -> {
            String input = JOptionPane.showInputDialog(null, "Input Vec2", oldValue);
            System.out.println(input);
            return new Vec2(0, 0);
        });
    }

    @Exposed(as = "rolename")
    public String getRole() { return role.name().toLowerCase(Locale.ROOT); }
 */
