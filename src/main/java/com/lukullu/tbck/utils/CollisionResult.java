package com.lukullu.tbck.utils;

public class CollisionResult
{
    public boolean collisionCheck;
    public Vec2 delta;
    public boolean collisionAxisOriginIsQuery;

    public Vec2[] collisionAxisVertices;

    CollisionResult(boolean collisionCheck, Vec2 delta, boolean collisionAxisOriginIsQuery, Vec2[] collisionAxisVertices)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
    }
}
