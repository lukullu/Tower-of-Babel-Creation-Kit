package com.lukullu.tbck.gameObjects;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.UnderSquare3;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public interface ICollidableObject extends IGameObject
{

    ArrayList<LineSegment> getInteriorLines();
    void setInteriorLines(ArrayList<LineSegment> lines);

    default ArrayList<LineSegment> initInteriorLines(ArrayList<? extends Polygon> polygons)
    {
        ArrayList<LineSegment> out = new ArrayList<>();

        for (Polygon query : polygons)
        {
            for(Polygon polygon : polygons)
            {
                if(polygon.equals(query))
                    continue;

                for (int j = 0; j < polygon.vertices.size(); j++)
                {
                    if(!query.vertices.contains(polygon.vertices.get(j)) && query.vertices.contains(polygon.vertices.get((j + 1) % polygon.vertices.size())))
                        out.add(new LineSegment(polygon.vertices.get(j),polygon.vertices.get((j + 1) % polygon.vertices.size())));
                }
            }
        }
        return out;
    }

    default ArrayList<CollisionResult> dynamicCollisionUpdate(int depth)
    {

        if(depth <= 0) return new ArrayList<>();

        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<CollisionResult> results = new ArrayList<>();
        ArrayList<EntityObject> colliders = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            if (this.equals(entity)) { continue; }

            for (int i = 0; i < getPolygons().size(); i++)
            {
                if(getPolygons().get(i) instanceof SegmentData)
                    if(!((SegmentData) (Object) getPolygons().get(i)).enabled)
                        continue;

                for (int j = 0; j < entity.getPolygons().size(); j++)
                {

                    if(entity.getPolygons().get(j) instanceof SegmentData)
                        if(!((SegmentData) (Object) entity.getPolygons().get(j)).enabled)
                            continue;

                    CollisionResult res = Collision.interpretCollisionDetectionSAT(getPolygons().get(i).getVertices(), getPolygons().get(i).getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPolygons().get(j).getPosition(),entity);

                    if (!res.collisionCheck)
                        continue;

                    res.queryPolygon = getPolygons().get(i);
                    res.colliderPolygon = entity.getPolygons().get(j);

                    if(!colliders.contains(entity))
                    {
                        colliders.add(entity);
                    }

                    collisionResponse(res);

                    results.add(res);
                }
            }
        }

        collisionResolutionResponse(results);

        for (EntityObject entity : colliders) entity.dynamicCollisionUpdate(depth-1);

        return results;
    }

    default ArrayList<Polygon> staticCollisionUpdate()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();
        ArrayList<CollisionResult> results = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            for (int i = 0; i < getPolygons().size(); i++)
            {

                if(getPolygons().get(i) instanceof SegmentData)
                    if(!((SegmentData) (Object) getPolygons().get(i)).enabled)
                        continue;

                for (int j = 0; j < entity.getPolygons().size(); j++)
                {

                    if(entity.getPolygons().get(j) instanceof SegmentData)
                        if(!((SegmentData) (Object) entity.getPolygons().get(j)).enabled)
                            continue;

                    CollisionResult res = Collision.interpretCollisionDetectionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition(), entity);

                    if (!res.collisionCheck)
                        continue;

                    res.queryPolygon = getPolygons().get(i);
                    res.colliderPolygon = entity.getPolygons().get(j);

                    collisionResponse(res);
                    results.add(res);
                    out.add(getPolygons().get(i));
                }
            }
        }



        return out;
    }

    void collisionResponse(CollisionResult res);

    void collisionResolutionResponse(ArrayList<CollisionResult> res);
}
