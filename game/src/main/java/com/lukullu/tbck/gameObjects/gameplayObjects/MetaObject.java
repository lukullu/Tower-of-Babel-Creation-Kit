package com.lukullu.tbck.gameObjects.gameplayObjects;


import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.interfaces.ICollidableObject;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;
import com.lukullu.undersquare.UnderSquare3;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MetaObject extends GameplayObject implements ICollidableObject
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
    public MetaObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling, Runnable action, boolean isTrigger)
    {
        super(polygons,position,rotation,scaling);
        this.action = action;
        this.isContinuous = isTrigger;
    }

    @Override
    public void update()
    {
        if(isContinuous && isActive) action.run();
    }

    @Override
    public ArrayList<Polygon> staticCollisionUpdate()
    {
        ArrayList<Polygon> polygonColliders = ICollidableObject.super.staticCollisionUpdate();

        if(polygonColliders.isEmpty())
            setInActive();

        return polygonColliders;
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

    @Override
    public void collisionResponse(CollisionResult res, EntityObject entity) {
        // TODO: is right activator (entity.class == ?)
        setActive();
    }
}
