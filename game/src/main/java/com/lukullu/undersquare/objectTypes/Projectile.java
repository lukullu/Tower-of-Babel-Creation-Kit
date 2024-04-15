package com.lukullu.undersquare.objectTypes;

import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.math.Vec2;

public class Projectile extends Debris
{
    public ISegmentedObject origin;

    public Projectile(Entity origin, String psff_resource, Vec2 position, double rotation, double scaling, Vec2 initForce, double timeToLive) {
        super(psff_resource, position, rotation, scaling, timeToLive);
        this.origin = origin;
        force = initForce;
        coefficientOfFriction = 0;
    }

}
