package com.lukullu.gameObjects.gameplayObjects.entityObjects;

import com.lukullu.UnderSquare3;
import com.lukullu.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.enums.Shapes;
import com.lukullu.utils.Collision;
import com.lukullu.utils.CollisionResult;
import com.lukullu.utils.DeltaTimer;
import com.lukullu.utils.Vec2;

import java.util.List;

public class EntityObject extends GameplayObject {

    private double weight = 1; // Unit: kg
    public Vec2 force = Vec2.ZERO_VECTOR2;
    private Vec2 deltaPos = Vec2.ZERO_VECTOR2;

    public EntityObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, int pushPriority)
    {
        super(shapeDesc, position, rotation, scaling, pushPriority);
    }

    public void applyForce(Vec2 appliedForce)
    {
        force = force.add(appliedForce);
        Vec2 acceleration = force.divide(weight);
        Vec2 velocity = acceleration.multiply(DeltaTimer.getInstance().getDeltaTime());
        deltaPos = velocity.multiply(DeltaTimer.getInstance().getDeltaTime());

    }

    @Override
    public void update()
    {
        collisionUpdate();
        updatePos(deltaPos);
        updateVertices();

        // TODO: Temp; Actually make this work properly | Friction
        applyForce(force.multiply(-1 * 0.05));
    }

    public void collisionUpdate()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object)UnderSquare3.getGameObjects().get(EntityObject.class);
        for (EntityObject entity : entities) {
            if (this != entity) {
                CollisionResult res = Collision.collisionResolutionSAT(this,entity);

                if(res.collisionCheck)
                {
                    // TODO: implement proper push physics based on weight
                    if(this.getPushPriority() <= entity.getPushPriority())
                    {
                        this.updatePos(res.delta);
                    }
                    else
                    {
                        entity.updatePos(res.delta.multiply(-1));
                    }
                }
            }
        }
    }

}
