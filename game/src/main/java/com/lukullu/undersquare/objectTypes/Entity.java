package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.Constants;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;
import java.util.List;

public class Entity extends EntityObject implements ISegmentedObject
{

    public Entity(String psff_resource, Vec2 position, double rotation, double scaling)
    {
        super(SegmentDataManager.copyFromResource(SegmentDataManager.loadInternal(psff_resource)),position,rotation, scaling);
        initSegments();
    }

    public Entity(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling)
    {
        super(polygons,position,rotation, scaling);
    }

    @Override
    public void paint()
    {
        paintSegments();
    }

    //@Override
    public ArrayList<CollisionResult> dynamicCollisionUpdate(List<EntityObject> entities, int depth) {

        ArrayList<CollisionResult> collisionResults = super.dynamicCollisionUpdate(entities, depth);
        ArrayList<Polygon> colliderPolygons = new ArrayList<>();
        ArrayList<Polygon> queryPolygons = new ArrayList<>();

        if(collisionResults.isEmpty())
            return collisionResults;

        collisionResults.forEach((res) -> {
            colliderPolygons.add(res.colliderPolygon);
        });
        collisionResults.forEach((res) -> {
            queryPolygons.add(res.queryPolygon);
        });

        Polygon furthestPolygon = Polygon.getPolygonFurthestFromPoint(queryPolygons,this.getPosition());

        if(furthestPolygon instanceof SegmentData)
            for (CollisionResult result : collisionResults)
            {
                Vec2 deltaForce = Vec2.ZERO_VECTOR2;
                if(result.collider instanceof EntityObject)
                {
                    deltaForce = this.force.subtract(((EntityObject)result.collider).force);
                }


                if(deltaForce.length() > ((SegmentData) furthestPolygon).armorPoints * Constants.COLLISION_FORCE_TOLERANCE_PER_ARMOR_POINT)
                {
                    if(result.collider instanceof ISegmentedObject)
                        this.takeDamage((SegmentData)furthestPolygon,deltaForce);
                }

            }

        for (CollisionResult res : collisionResults)
        {
            furthestPolygon = Polygon.getPolygonFurthestFromPoint(colliderPolygons, res.collider.getPosition());

            if (furthestPolygon instanceof SegmentData)
                for (CollisionResult result : collisionResults) {
                    Vec2 deltaForce = Vec2.ZERO_VECTOR2;
                    if (result.collider instanceof EntityObject)
                        deltaForce = this.force.subtract(((EntityObject) result.collider).force);

                    if (deltaForce.length() > ((SegmentData) furthestPolygon).armorPoints * Constants.COLLISION_FORCE_TOLERANCE_PER_ARMOR_POINT) {
                        if (result.collider instanceof ISegmentedObject)
                            ((ISegmentedObject) res.collider).takeDamage((SegmentData) furthestPolygon, deltaForce);
                    }
                }
        }


        return collisionResults;
    }

}
