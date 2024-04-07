package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.utils.*;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.Objects;

public class EntityObject extends GameplayObject{

    public static final double gravity = 10;
    public double mass = 2.5; // Unit: kg
    public Vec2 force = Vec2.ZERO_VECTOR2;
    private Vec2 deltaPos = Vec2.ZERO_VECTOR2;
    public double coefficientOfFriction = 0.05;

    public EntityObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons, position, rotation, scaling);
        setInteriorLines(initInteriorLines(getPolygons()));
    }


    public void applyForce(Vec2 appliedForce)
    {
        force = force.add(appliedForce);
    }

    @Override
    public void update()
    {

        updatePos(calcDeltaPos());

        // TODO: Temp; Actually make this work properly | Friction
        if(force.x != 0 || force.y != 0)
            applyForce(force.multiply(-1 * coefficientOfFriction));

        super.update();
    }

    public Vec2 calcDeltaPos()
    {
        Vec2 acceleration = force.divide(mass);
        Vec2 velocity = acceleration.multiply(DeltaTimer.getInstance().getDeltaTime());
        Vec2 delta = velocity.multiply(DeltaTimer.getInstance().getDeltaTime()).multiply(100).add(deltaPos); // 1px = 1cm
        deltaPos = Vec2.ZERO_VECTOR2;
        return delta;
    }

    public void collisionResponse(CollisionResult res)
    {
        if(!(res.collider instanceof EntityObject)) return;
        EntityObject entity = (EntityObject)(Object) res.collider;

        Vec2 combinedForce = this.force.subtract(entity.force);
        Vec2 queryForce = combinedForce.multiply(this.mass / (this.mass + entity.mass));
        Vec2 generalDirectionQuery = res.queryPolygon.getPosition().subtract(res.colliderPolygon.getPosition());
        Vec2 deltaNorm = res.delta.normalise();

        if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
        {
            this.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(1));
            entity.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(-1));
        }
        this.updatePos(res.delta.multiply(1.001));
    }
}
