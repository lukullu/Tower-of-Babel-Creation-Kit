package com.tbck.math;

import net.aether.utils.utils.reflection.Exposed;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon implements Serializable
{
    
    @Serial private static final long serialVersionUID = 3756687872714555217L;
    
    @Exposed(hidden = true) public ArrayList<Vec2> vertices;

    public Polygon(java.util.List<Vec2> vertices) { this(new ArrayList<>(vertices)); }
    
    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices = vertices;
    }
    public Polygon(Polygon poly)
    {
        this.vertices = poly.vertices;
    }

    public boolean isConvex()
    {
        boolean direction = false;

        for(int i = 1; i < vertices.size()+1; i++)
        {
            Vec2 preVertex = vertices.get((i-1) % vertices.size());
            Vec2 vertex = vertices.get((i) % vertices.size());
            Vec2 postVertex = vertices.get((i+1) % vertices.size());

            double crossProduct = ((vertex.x - preVertex.x) * (vertex.y - postVertex.y)) - ((vertex.y - preVertex.y) * (vertex.x - postVertex.x));

            if(i == 1)
            {
                direction = crossProduct > 0;
            }

            if ((crossProduct > 0 != direction))
            {
                return false;
            }
        }
        return true;
    }

    public void transform(Vec2 origin, Vec2 deltaPos, double deltaRot, double deltaSca)
    {
        ArrayList<Vec2> newVertices = new ArrayList<>();
        for (Vec2 vertex : vertices) {
            vertex = vertex.add(deltaPos);
            vertex = vertex.rotate(origin,deltaRot);
            vertex = vertex.scale(origin,deltaSca);
            newVertices.add(vertex);
        }
        vertices = newVertices;
    }

    public Polygon scale(double scale) {
        return new Polygon(new ArrayList<>(
                vertices.stream().map(vec -> vec.multiply(scale)).toList()
        ));
    }
    
    public java.awt.Polygon asNative() {
        int[] xPoints = new int[vertices.size()];
        int[] yPoints = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            xPoints[i] = (int) vertices.get(i).x;
            yPoints[i] = (int) vertices.get(i).y;
        }
        return new java.awt.Polygon(xPoints, yPoints, vertices.size());
    }
    
    public ArrayList<Vec2> getVertices(){ return vertices; }
    public Vec2 getPosition() { return calcPos(); }


    private Vec2 calcPos()
    {
        Vec2 acc = Vec2.ZERO_VECTOR2;
        int counter = 0;
        for(Vec2 vertex : vertices)
        {
            acc = acc.add(vertex);
            counter++;
        }
        return acc.divide(counter);
    }

    public String toString() {
        return vertices.toString();
    }

}
