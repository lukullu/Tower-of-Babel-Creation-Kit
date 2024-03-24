package com.lukullu.tbck.gameObjects.gameplayObjects;


import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.tbck.utils.Polygon;
import com.lukullu.tbck.utils.Vec2;
import com.lukullu.undersquare.UnderSquare3;

import java.util.ArrayList;
import java.util.List;

public class MetaObject extends GameplayObject
{
    Runnable action = null;
    boolean isActive = false;
    boolean isContinuous = true;

    public MetaObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, Runnable action, boolean isContinuous)
    {
        super(shapeDesc, position, rotation, scaling);
        this.action = action;
        this.isContinuous = isContinuous;
    }
    public MetaObject(ArrayList<Polygon> polygons, Vec2 position, Runnable action, boolean isTrigger)
    {
        super(new ArrayList<>(polygons
                .stream()
                .map((polygon) -> new Polygon(new ArrayList<>(polygon
                    .getVertices()
                    .stream()
                    .map((vertex)->vertex
                            .add(position))
                    .toList())))
                .toList()));

        this.action = action;
        this.isContinuous = isTrigger;
    }

    @Override
    public void update()
    {
        collisionUpdate();
        if(isContinuous && isActive) action.run();
    }

    private void collisionUpdate()
    {
        @SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<CollisionResult> out = new ArrayList<>();

        if(entities == null) return;

        for (EntityObject entity : entities)
        {
            for (Polygon polygon : getPolygons())
            {
                for (Polygon entityPolygon : entity.getPolygons())
                {
                    CollisionResult res = Collision.collisionResolutionSAT(polygon.getVertices(), polygon.getPosition(), entityPolygon.getVertices(), entityPolygon.getPosition());

                    if(res.collisionCheck)
                    {
                        // TODO: is right activator (entity.class == ?)
                        setActive();
                        out.add(res);
                    }
                }
            }
        }
        if(out.isEmpty()) setInActive();

    }

    private void setActive()
    {
        if(!isContinuous && !isActive) action.run();
        isActive = true;
    }

    private void setInActive()
    {
        isActive = false;
    }
}
