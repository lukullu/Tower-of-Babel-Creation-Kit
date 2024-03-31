package com.lukullu.tbck.utils;

import com.tbck.data.entity.SegmentRoles;
import com.tbck.math.Vec2;

import java.util.ArrayList;

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

    public static class SegmentData
    {
        public SegmentRoles role;
        public ArrayList<Vec2> vertices;
        public Double ArmorPoints;

    }
}
