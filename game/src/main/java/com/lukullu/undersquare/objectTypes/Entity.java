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
    public ArrayList<Polygon> dynamicCollisionUpdatePolygon(int depth)
    {
        ArrayList<Polygon> colliderPolygons = super.dynamicCollisionUpdatePolygon(depth);

        Polygon furthestPolygon = null;
        double furthestDistance = 0;

        if(colliderPolygons.size() != 1)
        {
            for(Polygon polygon : colliderPolygons)
            {
                if(polygon.getPosition().subtract(this.getPosition()).length2() > furthestDistance)
                {
                    furthestDistance = polygon.getPosition().subtract(this.getPosition()).length2();
                    furthestPolygon = polygon;
                }
            }
        }else
        {
            furthestPolygon = colliderPolygons.get(0);
        }

        //if(getPolygons().contains(furthestPolygon))
        //getSegments().get(getPolygons().indexOf(furthestPolygon)).enabled = false;

        return colliderPolygons;
    }

}