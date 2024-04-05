package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.math.LineSegment;
import com.tbck.math.Vec2;

import javax.sound.sampled.Line;

public class CollisionResult
{
    public final ICollidableObject collider;
    public final boolean collisionCheck;
    public final Vec2 delta;
    public final boolean collisionAxisOriginIsQuery;
    public LineSegment collisionAxisVertices;

    CollisionResult(boolean collisionCheck, ICollidableObject collider, Vec2 delta, boolean collisionAxisOriginIsQuery, LineSegment collisionAxisVertices)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
        this.collider = collider;
    }
}
