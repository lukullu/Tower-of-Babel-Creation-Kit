package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.Constants;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;
import java.util.Arrays;

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
    public ArrayList<CollisionResult> dynamicCollisionUpdate(int depth) {

        ArrayList<CollisionResult> collisionResults = super.dynamicCollisionUpdate(depth);
        ArrayList<Polygon> colliderPolygons = new ArrayList<>();
        collisionResults.forEach((res) -> {
            colliderPolygons.add(res.colliderPolygon);
        });

        Polygon furthestPolygon = null;
        double furthestDistance = 0;

        if (colliderPolygons.size() > 1) {
            for (Polygon polygon : colliderPolygons) {
                if (polygon.getPosition().subtract(this.getPosition()).length2() > furthestDistance) {
                    furthestDistance = polygon.getPosition().subtract(this.getPosition()).length2();
                    furthestPolygon = polygon;
                }
            }
        } else if (colliderPolygons.size() == 1) {
            furthestPolygon = colliderPolygons.get(0);
        }

        if (furthestPolygon == null) return null;

        if(furthestPolygon instanceof SegmentData)
            for (CollisionResult result : collisionResults)
            {
                Vec2 deltaForce = Vec2.ZERO_VECTOR2;
                if(result.collider instanceof EntityObject)
                     deltaForce = this.force.subtract(((EntityObject)result.collider).force);

                if(deltaForce.length() > ((SegmentData) furthestPolygon).armorPoints * Constants.COLLISION_FORCE_TOLERANCE_PER_ARMOR_POINT)
                {
                    this.takeDamage((SegmentData)furthestPolygon,deltaForce);
                }

            }

        return collisionResults;
    }

}
