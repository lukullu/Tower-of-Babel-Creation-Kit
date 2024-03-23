package com.lukullu.tbck.gameObjects.gameplayObjects;


import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.tbck.utils.Triangle;
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
    public MetaObject(ArrayList<Vec2> vertices, Runnable action, boolean isTrigger)
    {
        super(vertices);
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
        List<EntityObject> entities = (List<EntityObject>)(Object) UnderSquare3.getGameObjects().get(EntityObject.class);

        ArrayList<CollisionResult> out = new ArrayList<>();

        for (EntityObject entity : entities)
        {

            for (Triangle triangle : entity.shapeTriangles)
            {
                if(triangle.enabled)
                {
                    CollisionResult res = Collision.collisionResolutionSAT(this.getVertices(),this.getPosition(),triangle.getVertices(),triangle.getPosition());

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
