package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import javax.sound.sampled.Line;

public class CollisionResult
{
    public ICollidableObject collider;
    public final boolean collisionCheck;
    public Vec2 delta = Vec2.ZERO_VECTOR2;
    public boolean collisionAxisOriginIsQuery;
    public LineSegment collisionAxisVertices;
    public Polygon colliderPolygon;
    public Polygon queryPolygon;
    public double minOverlap;
    public Vec2 transformationAxis;

    CollisionResult(boolean collisionCheck)
    {
        this.collisionCheck = collisionCheck;
        this.delta = Vec2.ZERO_VECTOR2;
    }

    CollisionResult(boolean collisionCheck, ICollidableObject collider, Polygon colliderPolygon, Polygon queryPolygon, Vec2 delta, boolean collisionAxisOriginIsQuery, LineSegment collisionAxisVertices, double minOverlap, Vec2 transformationAxis)
    {
        this.collisionAxisOriginIsQuery = collisionAxisOriginIsQuery;
        this.delta = delta;
        this.collisionCheck = collisionCheck;
        this.collider = collider;
        this.colliderPolygon = colliderPolygon;
        this.queryPolygon = queryPolygon;
        this.collisionAxisVertices = collisionAxisVertices;
        this.minOverlap = minOverlap;
        this.transformationAxis = transformationAxis;
    }
}
