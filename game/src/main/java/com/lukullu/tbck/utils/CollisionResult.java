package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.undersquare.interfaces.ICollidableObject;
import com.tbck.data.entity.SegmentRoles;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class CollisionResult
{
    public final ICollidableObject collider;
    public final boolean collisionCheck;
    public final Vec2 delta;
    public final boolean collisionAxisOriginIsQuery;
    public Vec2[] collisionAxisVertices;

    CollisionResult(boolean collisionCheck, ICollidableObject collider, Vec2 delta, boolean collisionAxisOriginIsQuery, Vec2[] collisionAxisVertices)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
        this.collider = collider;
    }
}
