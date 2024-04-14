package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import net.aether.utils.utils.reflection.Exposes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

@Exposes({"armorPoints", "healthPoints", "role"})
public class SegmentData extends Polygon implements Serializable, ISegment
{

    @Serial private static final long serialVersionUID = 3756687822214555218L;

    public final boolean isValid;
    public boolean enabled;
    public int armorPoints = 0;
    public int healthPoints = 0;
    public SegmentRoles role = SegmentRoles.SEGMENT;
    public ArrayList<SegmentData> neighborSegments;


    public SegmentData(ArrayList<Vec2> vertices) {
        super(SegmentDataManager.copyFromVertices(vertices)); enabled = true; isValid = true;
    }

    public SegmentData(SegmentData data) {
        super(data.vertices);
        enabled = data.enabled;
        isValid = data.isValid;
        armorPoints = data.armorPoints;
        role = data.role;
        neighborSegments = data.neighborSegments;
    }

    public ArrayList<SegmentData> checkNeighborsRec(ArrayList<SegmentData> visited)
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
        return "SegmentData{" + role + ", " + armorPoints + "-ap, " + vertices.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SegmentData that)) return false;
        return enabled == that.enabled && armorPoints == that.armorPoints && role == that.role && vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(armorPoints, role, enabled, armorPoints);
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

    @Exposed(hidden = true)
 */
