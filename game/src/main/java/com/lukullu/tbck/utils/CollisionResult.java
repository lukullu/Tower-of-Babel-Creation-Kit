package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import javax.sound.sampled.Line;

public class CollisionResult
{
    public final ICollidableObject collider;
    public final boolean collisionCheck;
    public final Vec2 delta;
    public final boolean collisionAxisOriginIsQuery;
    public LineSegment collisionAxisVertices;
    public Polygon colliderPolygon;
    public Polygon queryPolygon;

    CollisionResult(boolean collisionCheck, ICollidableObject collider, Polygon colliderPolygon, Polygon queryPolygon, Vec2 delta, boolean collisionAxisOriginIsQuery, LineSegment collisionAxisVertices)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
        this.collider = collider;
        this.colliderPolygon = colliderPolygon;
        this.queryPolygon = queryPolygon;

    }
}
