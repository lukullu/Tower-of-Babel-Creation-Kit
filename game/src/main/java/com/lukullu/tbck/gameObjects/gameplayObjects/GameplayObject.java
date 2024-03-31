package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.utils.*;
import com.tbck.math.MyMath;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class GameplayObject implements IGameObject, ProcessingClass
{

    private int id = ID_Manager.getInstance().generateNewID();
    public Shapes shapeDesc = Shapes.PENTAGON;
    private Vec2 position = new Vec2(0,0);
    public double rotation = 0; // Unit: radians
    private double scaling = 1;
    public ArrayList<Polygon> shape = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();

    public boolean debugOverlap = false;

    public GameplayObject()
    {
        initShape();
        initVertices();
    }

    public GameplayObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        this.shapeDesc = shapeDesc;
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
        initShape();
        initVertices();
    }

    public GameplayObject(ArrayList<Polygon> shape, Vec2 position, double rotation)
    {
        this.shape = shape;
        this.rotation = rotation;
        this.position = position;
        initVertices();
    }

    public int getID() { return 0; }
    public Vec2 getPosition() { return position; }
    public ArrayList<Vec2> getVertices(int i){ return polygons.get(i).getVertices(); }
    public ArrayList<Polygon> getPolygons(){ return polygons; }
    public void setPolygons(ArrayList<Polygon> polygons) { this.polygons = polygons; }
    public void update() {  } // TODO: Check if this can be moved to the updatePos / updateRot functions to save resources
    public void paint()
    {
        for (Polygon polygon : polygons)
            paintPolygon(polygon);
    }

    public void updatePos(Vec2 delta)
    {
        position = position.add(delta);
        updateVertices(delta,0);
    }

    public void updateRot(double delta)
    {
        rotation += delta;
        updateVertices(Vec2.ZERO_VECTOR2,delta);
    }

    public void initShape()
    {
        int vertexCount = switch (shapeDesc) {
            case POLY -> -1; // TODO do some custom shit idk/idc
            case TRIANGLE -> 3;
            case SQUARE -> 4;
            case PENTAGON -> 5;
            case HEXAGON -> 6;
            case HEPTAGON -> 7;
            case OKTAGON -> 8;
            case POLY16 -> 16;
            case POLY32 -> 32;
        };


        if(vertexCount != -1)
        {
            double radialFractions = ( 2 * PI ) / vertexCount;
            double radialAccumulator = MyMath.isEven(vertexCount) ? radialFractions / 2 : 0;

            ArrayList<Vec2> newShape = new ArrayList<>();

            for (int i = 0; i < vertexCount; i++)
            {
                Vec2 noramlVec2 = new Vec2(1,0);
                newShape.add(new Vec2(
                        (noramlVec2.x * Math.cos(radialAccumulator) - noramlVec2.y * Math.sin(radialAccumulator)) * scaling,
                        (noramlVec2.y * Math.cos(radialAccumulator) + noramlVec2.x * Math.sin(radialAccumulator)) * scaling));

                radialAccumulator += radialFractions;
            }
            shape.add(new Polygon(newShape));
        }
    }
    private void initVertices()
    {
        polygons.addAll(shape);
        updateVertices(position,rotation);
    }
    public void updateVertices(Vec2 deltaPos, double deltaRot)
    {
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        for (Polygon polygon : polygons)
        {
            newPolygons.add(calcVertices(polygon.getVertices(),deltaPos,deltaRot));
        }
        polygons = newPolygons;
    }

    private Polygon calcVertices(ArrayList<Vec2> vertices, Vec2 position, double rotation) {
        ArrayList<Vec2> output = new ArrayList<Vec2>();
        for (Vec2 vertex : vertices) {

            vertex = vertex.add(position);
            vertex = vertex.rotate(getPosition(),rotation);

            output.add(vertex);
        }
        return new Polygon(output);
    }

    protected void paintPolygon(Polygon polygon)
    {
        fill(255);

        beginShape();
        for (int i = 0; i < polygon.getVertices().size() + 1; i++)
        {
            vertex((float) polygon.getVertices().get(i % polygon.getVertices().size()).x,(float) polygon.getVertices().get(i % polygon.getVertices().size()).y);
        }
        endShape(CLOSE);

        //TODO: Debug remove

        fill(255,0,0);
        rectMode(CENTER);
        for (int i = 0; i < polygon.getVertices().size() + 1; i++)
        {
            ellipse((float)polygon.getPosition().x,(float)polygon.getPosition().y,2,2);
        }
        fill(255);
    }


}
