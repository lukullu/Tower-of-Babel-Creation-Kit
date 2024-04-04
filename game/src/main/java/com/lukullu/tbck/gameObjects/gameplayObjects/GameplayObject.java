package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.utils.*;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.MyMath;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class GameplayObject implements IGameObject, ProcessingClass
{

    protected int[] debugColor = {(int)random(0,255),(int)random(0,255),(int)random(0,255)};
    protected float debugAlpha = 255;

    private int id = ID_Manager.getInstance().generateNewID();
    public Shapes shapeDesc = Shapes.PENTAGON;
    private Vec2 position = new Vec2(0,0), originalPosition;
    public double rotation = 0, originalRotation; // Unit: radians
    private double scaling = 1, originalScaling;
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
        this.position = position; this.originalPosition = position;
        this.rotation = rotation; this.originalRotation = rotation;
        this.scaling = scaling; this.originalScaling = scaling;
        initShape();
        initVertices();
    }

    public GameplayObject(ArrayList<? extends Polygon> shape, Vec2 position, double rotation, double scaling)
    {
        this.shape.addAll(shape);
        this.position = position; this.originalPosition = position;
        this.rotation = rotation; this.originalRotation = rotation;
        this.scaling = scaling; this.originalScaling = scaling;
        initVertices();
    }

    public int getID() { return 0; }
    public Vec2 getPosition() { return position; }
    public ArrayList<Vec2> getVertices(int i){ return polygons.get(i).getVertices(); }
    public ArrayList<Polygon> getPolygons(){ return polygons; }
    public ArrayList<Polygon> getShape() { return shape; }
    public void setShape(ArrayList<Polygon> shape) { this.shape = polygons; }
    public void setPolygons(ArrayList<Polygon> polygons) { this.polygons = polygons; }
    public void update() {  } // TODO: Check if this can be moved to the updatePos / updateRot functions to save resources
    public void paint()
    {
        for (Polygon polygon : polygons)
            paintPolygon(polygon);
    }

    public void reset()
    {
        rotation = originalRotation;
        scaling = originalScaling;
        position = originalPosition;
        initVertices();
    }
    public void updatePos(Vec2 delta)
    {
        position = position.add(delta);
        updateVertices(delta,0,0);
    }

    public void updateRot(double delta)
    {
        rotation += delta;
        updateVertices(Vec2.ZERO_VECTOR2,delta,0);
    }

    public void updateSca(double delta)
    {
        rotation += delta;
        updateVertices(Vec2.ZERO_VECTOR2,0,delta);
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
        polygons = new ArrayList<>();
        polygons.addAll(shape);
        updateVertices(position,rotation,scaling);
    }

    public void updateVertices(Vec2 deltaPos, double deltaRot, double deltaSca)
    {
        for (Polygon polygon : polygons) {
            polygon.transform(getPosition(), deltaPos, deltaRot, deltaSca);
        }
    }


    public void paintPolygon(Polygon polygon)
    {
        fill(debugColor[0],debugColor[1],debugColor[2],debugAlpha);

        beginShape();
        for (int i = 0; i < polygon.getVertices().size() + 1; i++)
        {
            vertex((float) polygon.getVertices().get(i % polygon.getVertices().size()).x,(float) polygon.getVertices().get(i % polygon.getVertices().size()).y);
        }
        endShape(CLOSE);

        //TODO: Debug remove

        fill(0);
        rectMode(CENTER);
        for (int i = 0; i < polygon.getVertices().size() + 1; i++)
        {
            ellipse((float)polygon.getPosition().x,(float)polygon.getPosition().y,2,2);
        }
        fill(255);
    }

}
