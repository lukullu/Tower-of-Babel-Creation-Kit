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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MetaObject extends GameplayObject implements ICollidableObject
{
    Consumer<CollisionResult> action = null;
    boolean isActive = false;
    boolean isContinuous = true;

    public MetaObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, Consumer<CollisionResult> action, boolean isContinuous)
    {
        super(shapeDesc, position, rotation, scaling);
        this.action = action;
        this.isContinuous = isContinuous;
    }
    public MetaObject(ArrayList<? extends Polygon> polygons, Vec2 position, double rotation, double scaling, Consumer<CollisionResult> action, boolean isTrigger)
    {
        super(polygons,position,rotation,scaling);
        this.action = action;
        this.isContinuous = isTrigger;
    }

    @Override
    public void update()
    {
        if(isContinuous && isActive) action.accept(null);
    }

    @Override
    public ArrayList<Polygon> staticCollisionUpdate()
    {
        ArrayList<Polygon> polygonColliders = ICollidableObject.super.staticCollisionUpdate();

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
