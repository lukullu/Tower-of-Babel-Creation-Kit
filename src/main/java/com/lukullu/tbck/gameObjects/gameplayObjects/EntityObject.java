package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Actions;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.tbck.enums.Shapes;

import java.util.ArrayList;
import java.util.List;

public class EntityObject extends MeshObject {

    public int pushPriority;
    public double mass = 2; // Unit: kg
    public Vec2 force = Vec2.ZERO_VECTOR2;

    public EntityObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, int pushPriority)
    {
        super(shapeDesc, position, rotation, scaling);
        this.pushPriority = pushPriority;
    }

    public int getPushPriority() { return pushPriority; }

    public void applyForce(Vec2 appliedForce)
    {
        force = force.add(appliedForce);
    }

    @Override
    public void update()
    {


        ArrayList<Polygon> colliders = dynamicCollisionUpdatePolygon();


        updatePos(calcDeltaPos());
        super.update();

        // TODO: Temp; Actually make this work properly | Friction
        applyForce(force.multiply(-1 * 0.05));
    }

    public Vec2 calcDeltaPos()
    {
        Vec2 acceleration = force.divide(mass);
        Vec2 velocity = acceleration.multiply(DeltaTimer.getInstance().getDeltaTime());
        return velocity.multiply(DeltaTimer.getInstance().getDeltaTime());
    }

    public ArrayList<Polygon> dynamicCollisionUpdatePolygon()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object)UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            if (this != entity)
            {
                for (Polygon polygon : getPolygons())
                {
                    for (Polygon entityPolygon : entity.getPolygons())
                    {
                        CollisionResult res = Collision.collisionResolutionSAT(polygon.getVertices(), polygon.getPosition(), entityPolygon.getVertices(), entityPolygon.getPosition());

                        if (res.collisionCheck)
                        {
                            if (this.getPushPriority() <= entity.getPushPriority())
                            {
                                this.updatePos(res.delta);
                            } else entity.updatePos(res.delta.multiply(-1));

                            Vec2 combinedForce = this.force.subtract(entity.force);
                            Vec2 queryForce = combinedForce.multiply(entity.mass / (this.mass + entity.mass));

                            Vec2 generalDirectionQuery = polygon.getPosition().subtract(entityPolygon.getPosition());

                            Vec2 deltaNorm = res.delta.normalise();

                            if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
                            {
                                this.applyForce(res.delta.normalise().multiply(-1).multiply(queryForce).align(generalDirectionQuery));
                            }
                            out.add(polygon);
                        }

                    }
                }
            }
        }
        return out;
    }

}
