package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.Constants;
import com.lukullu.undersquare.enums.Affiliation;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;

public class Entity extends EntityObject implements ISegmentedObject
{

    private Affiliation affiliation;

    public Entity(String psff_resource, Vec2 position, double rotation, double scaling, Affiliation affiliation)
    {
        super(SegmentDataManager.copyFromResource(SegmentDataManager.loadInternal(psff_resource)),position,rotation, scaling);
        this.affiliation = affiliation;
        initSegments();
    }

    public Entity(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling, Affiliation affiliation)
    {
        super(polygons,position,rotation, scaling);
        this.affiliation = affiliation;
    }

    @Override
    public void paint()
    {
        paintSegments();
    }

    //@Override
    public ArrayList<CollisionResult> dynamicCollisionUpdate(int depth) {

        ArrayList<CollisionResult> collisionResults = super.dynamicCollisionUpdate(depth);
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

    public static ArrayList<Entity> getEntitiesInRange(Vec2 origin, ArrayList<Entity> entities, double range)
    {
        ArrayList<Entity> out = new ArrayList<>();
        for(Entity entity : entities)
        {
            if(isEntityInRange(origin,entity,range))
                out.add(entity);
        }
        return out;
    }

    public static boolean isEntityInRange(Vec2 origin, Entity query, double range)
    {
        return query.getPosition().subtract(origin).length2() <= range * range;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
