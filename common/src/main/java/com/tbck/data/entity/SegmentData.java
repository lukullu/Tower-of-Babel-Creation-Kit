package com.tbck.data.entity;


import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import net.aether.utils.utils.reflection.Exposed;
import net.aether.utils.utils.reflection.Exposes;
import net.aether.utils.utils.reflection.FieldAccess;
import net.aether.utils.utils.swing.FieldButton;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Exposes("*")
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

    public int armorPoints;
    public SegmentRoles role;
    @Exposed(hidden = true) public boolean enabled = true;
    
    public Vec2 testVec = new Vec2(69, 420);
    
    public SegmentData(ArrayList<Vec2> vertices) {
        super(vertices);
    }
    
    public String toString() {
        return "SegmentData{" + role + ", " + armorPoints + "ap, " + vertices.toString() + '}';
    }
}
