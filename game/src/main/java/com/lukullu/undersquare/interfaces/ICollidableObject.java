package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.UnderSquare3;
import com.tbck.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public interface ICollidableObject extends IGameObject
{
    default ArrayList<Polygon> dynamicCollisionUpdate(int depth)
    {

        if(depth <= 0) return new ArrayList<>();

        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<Polygon> out = new ArrayList<>();
        ArrayList<EntityObject> colliders = new ArrayList<>();

        for (EntityObject entity : entities)
        {
            if (this.equals(entity)) { continue; }

            for (int i = 0; i < getPolygons().size(); i++)
            {
                for (int j = 0; j < entity.getPolygons().size(); j++)
                {
                     CollisionResult res = Collision.collisionResolutionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition());

                    if (!res.collisionCheck){ continue; }

                    collisionResponse(res,entity);

                    if(!colliders.contains(entity)){ colliders.add(entity); }
                    out.add(getPolygons().get(i));
                }
            }
        }

        for (EntityObject entity : colliders) entity.dynamicCollisionUpdate(depth-1);

        return out;
    }

    default ArrayList<Polygon> staticCollisionUpdate()
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
                    CollisionResult res = Collision.collisionResolutionSAT(getPolygons().get(i).getVertices(), this.getPosition(), entity.getPolygons().get(j).getVertices(), entity.getPosition());

                    if (!res.collisionCheck){ continue; }

                    collisionResponse(res,entity);

                    out.add(getPolygons().get(i));
                }
            }
        }
        return out;
    }

    void collisionResponse(CollisionResult res, EntityObject entity);
}
