package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.tbck.enums.Shapes;
import com.lukullu.undersquare.interfaces.ICollidableObject;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityObject extends GameplayObject implements ICollidableObject {

    public static final double gravity = 10;
    public double mass = 2.5; // Unit: kg
    public Vec2 force = Vec2.ZERO_VECTOR2;

    //DEBUG
    private Vec2 deltaPos = Vec2.ZERO_VECTOR2;

    public EntityObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
    }

    public EntityObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons, position, rotation, scaling);
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
        double coefficientOfFriction = 0.05;
        if(!force.equals(Vec2.ZERO_VECTOR2)) applyForce(force.multiply(-1 * coefficientOfFriction));
    }

    public Vec2 calcDeltaPos()
    {
        Vec2 acceleration = force.divide(mass);
        Vec2 velocity = acceleration.multiply(DeltaTimer.getInstance().getDeltaTime());
        Vec2 delta = velocity.multiply(DeltaTimer.getInstance().getDeltaTime()).multiply(100).add(deltaPos); // 1px = 1cm
        deltaPos = Vec2.ZERO_VECTOR2;
        return delta;
    }

    public ArrayList<Polygon> dynamicCollisionUpdate(int depth)
    {

        if(depth <= 0) return new ArrayList<>();

        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object)UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();
        ArrayList<EntityObject> colliders = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            if (this.equals(entity)) { continue; }

            for (int i = 0; i < getPolygons().size(); i++)
            {
                for (int j = 0; j < entity.getPolygons().size(); j++)
                {
                    //CollisionResult res = Collision.collisionResolutionSAT(polygon.getVertices(), polygon.getPosition(), entityPolygon.getVertices(), entityPolygon.getPosition());
                    CollisionResult res = Collision.collisionResolutionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition(),entity);

                    if (!res.collisionCheck){ continue; }

                    collisionResponse(res);

                    if(!colliders.contains(entity)){ colliders.add(entity); }
                    out.add(getPolygons().get(i));
                }
            }
        }

        for (EntityObject entity : colliders) entity.dynamicCollisionUpdate(depth-1);

        return out;
    }

    public void collisionResponse(CollisionResult res)
    {
        if(!(res.collider instanceof EntityObject)) return;
        EntityObject entity = (EntityObject)(Object) res.collider;

        Vec2 combinedForce = this.force.subtract(entity.force);
        Vec2 queryForce = combinedForce.multiply(this.mass / (this.mass + entity.mass));
        Vec2 generalDirectionQuery = this.getPosition().subtract(res.collider.getPosition());
        Vec2 deltaNorm = res.delta.normalise();

        if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
        {
            this.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(1));
            entity.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(-1));
        }

        this.updatePos(res.delta.multiply(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityObject that)) return false;
        return Double.compare(mass, that.mass) == 0 && Objects.equals(force, that.force) && Objects.equals(deltaPos, that.deltaPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash( mass, force, deltaPos);
    }
}
