package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class StaticObject extends GameplayObject implements ICollidableObject
{

    private ArrayList<LineSegment> interiorLines;

    public StaticObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons,position,rotation,scaling);
        setInteriorLines(initInteriorLines(getPolygons()));
    }

    @Override
    public void collisionResponse(CollisionResult res) {

        if(!(res.collider instanceof EntityObject))
            return;

        EntityObject entity = (EntityObject)(Object) res.collider;

        Vec2 generalDirectionQuery = this.getPosition().subtract(entity.getPosition());
        Vec2 deltaNorm = res.delta.normalise();

        if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
            entity.applyForce(deltaNorm.multiply(entity.force).align(generalDirectionQuery).multiply(-1));


        entity.updatePos(res.delta.multiply(-1));

    }
}
