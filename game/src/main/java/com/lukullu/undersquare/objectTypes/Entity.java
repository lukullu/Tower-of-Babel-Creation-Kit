package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;

public class Entity extends EntityObject implements ISegmentedObject
{

    public Entity(String psff_resource, Vec2 position, double rotation, double scaling)
    {
        super(SegmentDataManager.loadInternal(psff_resource),position,rotation, scaling);
        initSegments();
    }

    @Override
    public void paint()
    {
        paintSegments();
    }

    @Override
    public ArrayList<Polygon> dynamicCollisionUpdate(int depth)
    {

        ArrayList<Polygon> colliderPolygons = super.dynamicCollisionUpdate(depth);

        Polygon furthestPolygon = null;
        double furthestDistance = 0;

        if(colliderPolygons.size() > 1)
        {
            for(Polygon polygon : colliderPolygons)
            {
                if(polygon.getPosition().subtract(this.getPosition()).length2() > furthestDistance)
                {
                    furthestDistance = polygon.getPosition().subtract(this.getPosition()).length2();
                    furthestPolygon = polygon;
                }
            }
        }
        else if (colliderPolygons.size() == 1)
        {
            furthestPolygon = colliderPolygons.get(0);
        }

        if(furthestPolygon == null) return null;


        //TODO: Check if Force is high enough for damage
        //TODO: Check if Health is set to below 0 on hit
        //TODO: Only then set Segment Inactive
        /*if(getPolygons().contains(furthestPolygon))
            if(furthestPolygon instanceof SegmentData)
                this.setSegmentInactive((SegmentData) (Object)furthestPolygon);*/


        return colliderPolygons;
    }

}
