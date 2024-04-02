package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.undersquare.interfaces.ICollidableObject;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;

public class StaticObject extends GameplayObject implements ICollidableObject
{

    public StaticObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
    }

    public StaticObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons,position,rotation,scaling);
    }

    @Override
    public void collisionResponse(CollisionResult res, EntityObject entity) {
        Vec2 generalDirectionQuery = this.getPosition().subtract(entity.getPosition());
        Vec2 deltaNorm = res.delta.normalise();

        if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
        {
            entity.applyForce(deltaNorm.multiply(entity.force).align(generalDirectionQuery).multiply(-1));
        }

        entity.updatePos(res.delta.multiply(-1));
    }
}
