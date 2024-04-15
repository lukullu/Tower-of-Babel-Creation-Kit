package com.tbck.math;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.aether.utils.utils.reflection.Exposed;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon implements Serializable
{
    
    public static final TypeAdapter<Polygon> TYPE_ADAPTER = new TypeAdapter<>() {
        public void write(JsonWriter jsonWriter, Polygon polygon) throws IOException {
            jsonWriter.beginArray();
            polygon.vertices.forEach(vec2 -> {
				try { Vec2.TYPE_ADAPTER.write(jsonWriter, vec2); }
                catch (IOException e) { throw new RuntimeException(e); }
			});
            jsonWriter.endArray();
        }
        public Polygon read(JsonReader jsonReader) throws IOException {
            jsonReader.beginArray();
            Polygon out = new Polygon(new ArrayList<>());
            while (jsonReader.peek() != JsonToken.END_ARRAY) {
                out.vertices.add(Vec2.TYPE_ADAPTER.read(jsonReader));
            }
            jsonReader.endArray();
            return out;
        }
    };
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
            Vec2 newVertex;
            newVertex = vertex.add(deltaPos);
            newVertex = newVertex.rotate(origin,deltaRot);
            newVertex = newVertex.scale(origin,deltaSca);
            newVertices.add(newVertex);
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
    public Vec2 getPosition() { return calcPos(vertices); }

    public static Vec2 getPositionFromPolygons(ArrayList<Polygon> polygons)
    {
        Vec2 acc = Vec2.ZERO_VECTOR2;
        for(Polygon polygon : polygons)
        {
            acc = acc.add(polygon.getPosition());
        }
        return acc.divide(polygons.size());
    }

    public static Polygon getPolygonFurthestFromPoint(ArrayList<Polygon> polygons, Vec2 origin)
    {
        Polygon out = null;
        double furthestDistance = Double.MIN_VALUE;

        if (polygons.size() > 1) {
            for (Polygon polygon : polygons) {
                if (polygon.getPosition().subtract(origin).length2() > furthestDistance) {
                    furthestDistance = polygon.getPosition().subtract(origin).length2();
                    out = polygon;
                }
            }
        } else {
            out = polygons.get(0);
        }
        return out;
    }

    public static Vec2 calcPos(ArrayList<Vec2> vertices)
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
