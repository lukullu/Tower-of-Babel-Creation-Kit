package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.UnderSquare3;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;

public class StaticObject extends GameplayObject
{

    public StaticObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
    }

    public StaticObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons,position,rotation,scaling);
    }

    public ArrayList<Polygon> dynamicCollisionUpdatePolygon()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            for (int i = 0; i < getPolygons().size(); i++)
            {
                for (int j = 0; j < entity.getPolygons().size(); j++)
                {
                    //CollisionResult res = Collision.collisionResolutionSAT(polygon.getVertices(), polygon.getPosition(), entityPolygon.getVertices(), entityPolygon.getPosition());
                    CollisionResult res = Collision.collisionResolutionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition());

                    if (!res.collisionCheck){ continue; }


                    Vec2 generalDirectionQuery = this.getPosition().subtract(entity.getPosition());
                    Vec2 deltaNorm = res.delta.normalise();

                    if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
                    {
                        entity.applyForce(deltaNorm.multiply(entity.force).align(generalDirectionQuery).multiply(-1));
                    }

                    entity.updatePos(res.delta.multiply(-1));

                    out.add(getPolygons().get(i));
                }
            }
        }
        return out;
    }

}
