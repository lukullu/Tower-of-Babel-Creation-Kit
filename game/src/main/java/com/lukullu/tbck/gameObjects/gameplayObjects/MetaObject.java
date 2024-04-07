package com.lukullu.tbck.gameObjects.gameplayObjects;


import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MetaObject extends GameplayObject implements ICollidableObject
{
    Consumer<CollisionResult> action = null;
    boolean isActive = false;
    boolean isContinuous = true;
    private ArrayList<LineSegment> interiorLines;

    public MetaObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling, Consumer<CollisionResult> action, boolean isTrigger)
    {
        super(polygons,position,rotation,scaling);
        setInteriorLines(initInteriorLines(getPolygons()));
        this.action = action;
        this.isContinuous = isTrigger;
    }

    @Override
    public void update()
    {

        if(isContinuous && isActive)
            action.accept(null);

        super.update();
    }

    @Override
    public void paint()
    {
        noStroke();
        super.paint();
        stroke(0);
    }

    @Override
    public ArrayList<Polygon> staticCollisionUpdate()
    {
        ArrayList<Polygon> polygonColliders = super.staticCollisionUpdate();

        if(polygonColliders.isEmpty())
            setInActive();

        return polygonColliders;
    }

    private void setActive(CollisionResult res)
    {
        if(!isContinuous && !isActive) action.accept(res);
        isActive = true;
    }

    private void setInActive()
    {
        isActive = false;
    }

    @Override
    public void collisionResponse(CollisionResult res) {
        // TODO: is right activator (entity.class == ?)
        setActive(res);
    }


}
