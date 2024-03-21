package com.lukullu.utils;

public class CollisionResult
{
    public boolean collisionCheck;
    public Vec2 delta;
    public boolean collisionAxisOriginIsQuery;

    CollisionResult(boolean collisionCheck, Vec2 delta, boolean collisionAxisOriginIsQuery)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
    }
}
