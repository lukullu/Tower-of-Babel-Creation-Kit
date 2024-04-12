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
        updatePos(calcDeltaPos(force,mass));

        // TODO: Temp; Actually make this work properly | Friction
        if(force.x != 0 || force.y != 0)
            applyForce(force.multiply(-1 * coefficientOfFriction));

        super.update();
    }

    public static Vec2 calcDeltaPos(Vec2 force,double mass)
    {
        Vec2 acceleration = force.divide(mass);
        Vec2 velocity = acceleration.multiply(DeltaTimer.getInstance().getDeltaTime());
        return velocity.multiply(DeltaTimer.getInstance().getDeltaTime()).multiply(100);
    }

    public void collisionResponse(CollisionResult result)
    {
        if(!result.delta.equals(Vec2.ZERO_VECTOR2))
            this.updatePos(result.delta.multiply(1.001));
    }

    public void collisionResolutionResponse(ArrayList<CollisionResult> results)
    {
        for(CollisionResult result : results)
        {
            if(!(result.collider instanceof EntityObject entity))
                continue;

            Vec2 combinedForce = this.force.subtract(entity.force);
            Vec2 queryForce = combinedForce.multiply(entity.mass / (this.mass + entity.mass));
            Vec2 deltaNorm = result.delta.normalise();

            if(!Double.isNaN(deltaNorm.x) && !Double.isNaN(deltaNorm.y))
            {
                Vec2 alignment = new Vec2(
                        Math.min(1,(double)Math.abs(Math.round(result.delta.x * 10d) / 10d)),
                        Math.min(1,(double)Math.abs(Math.round(result.delta.y * 10d) / 10d)));

                this.applyForce(queryForce.multiply(-1).multiply(alignment));
                entity.applyForce(queryForce.multiply(1).multiply(alignment));
                // ToDo: inter polygon friction ... .add(queryForce.multiply(0.1*other.coefficientOfFriction))
            }
        }
    }
}
