package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.lukullu.undersquare.UnderSquare3;
import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;
import java.util.List;

public class Entity extends EntityObject implements ISegmentedObject
{

    @Deprecated
    public ArrayList<SegmentData> segments;

    public Entity(String psff_resource, Vec2 position, double rotation, double scaling)
    {
        super(SegmentDataManager.loadInternal(psff_resource),position,rotation, scaling);
    }

    @Override
    public ArrayList<Polygon> dynamicCollisionUpdatePolygon()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            if (this.equals(entity)) { continue; }

            for (int i = 0; i < getPolygons().size(); i++)
            {
                for (int j = 0; j < entity.getPolygons().size(); j++)
                {

                    if(entity instanceof Entity)
                        if(!((Entity) entity).getSegments().get(j).enabled)
                            continue;

                    if (!getSegments().get(i).enabled) continue;

                    //CollisionResult res = Collision.collisionResolutionSAT(polygon.getVertices(), polygon.getPosition(), entityPolygon.getVertices(), entityPolygon.getPosition());
                    CollisionResult res = Collision.collisionResolutionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition());

                    if (!res.collisionCheck){ continue; }

                    Vec2 combinedForce = this.force.subtract(entity.force);
                    Vec2 queryForce = combinedForce.multiply(this.mass / (this.mass + entity.mass));
                    Vec2 generalDirectionQuery = this.getPosition().subtract(entity.getPosition());
                    Vec2 deltaNorm = res.delta.normalise();

                    if (!(Double.isNaN(deltaNorm.x) || Double.isNaN(deltaNorm.y)))
                    {
                        this.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(1));
                        entity.applyForce(deltaNorm.multiply(queryForce).align(generalDirectionQuery).multiply(-1));
                    }

                    this.updatePos(res.delta.multiply(1));

                    out.add(getPolygons().get(i));
                }
            }
        }

        Polygon furthestPolygon = null;
        double furthestDistance = 0;

        if(out.size() != 1)
        {
            for(Polygon polygon : out)
            {
                if(polygon.getPosition().subtract(this.getPosition()).length2() > furthestDistance)
                {
                    furthestDistance = polygon.getPosition().subtract(this.getPosition()).length2();
                    furthestPolygon = polygon;
                }
            }
        }else
        {
            furthestPolygon = out.get(0);
        }

        //if(getPolygons().contains(furthestPolygon))
            //segments.get(getPolygons().indexOf(furthestPolygon)).enabled = false;

        return out;
    }

}
