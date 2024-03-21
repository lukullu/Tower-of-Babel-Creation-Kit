package com.lukullu.gameObjects.gameplayObjects;

import com.kilix.processing.ProcessingClass;
import com.lukullu.UnderSquare3;
import com.lukullu.enums.Shapes;
import com.lukullu.gameObjects.IGameObject;
import com.lukullu.gameObjects.gameplayObjects.entityObjects.EntityObject;
import com.lukullu.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameplayObject implements IGameObject, ProcessingClass
{

    private int id = ID_Manager.getInstance().generateNewID();
    private Shapes shapeDesc = Shapes.PENTAGON;
    private Vec2 position = new Vec2(100,100);
    private double rotation = 0; // Unit: radians
    private double scaling = 10;
    private ArrayList<Vec2> shape = new ArrayList<>();
    private ArrayList<Vec2> vertices = new ArrayList<>();
    private int pushPriority = 1;

    public boolean debugOverlap = false;

    public GameplayObject()
    {
        initShape();
        initVertices();
    }
    public GameplayObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, int pushPriority)
    {
        this.shapeDesc = shapeDesc;
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
        this.pushPriority = pushPriority;
        initShape();
        initVertices();
    }

    public int getID() { return 0; }
    public int getPushPriority() { return pushPriority; }
    public Vec2 getPosition() { return position; }
    public ArrayList<Vec2> getVertices(){ return vertices; }
    public void update() { updateVertices(); }
    public void paint()
    {
        paintPolygon(vertices);
    }

    Random debugRan = new Random();

    public void updatePos(Vec2 delta)
    {

        //debug



        //rotation += PI/(160);
        //rotation %= 2*PI;

        position = position.add(delta);
        //checkForCollision(displacement,50);
        updateVertices();
        //System.out.println("Hello");
    }

    private void initShape()
    {
        int vertexCount = switch (shapeDesc) {
            case POLY -> -1; // TODO do some custom shit idk/idc
            case TRIANGLE -> 3;
            case SQUARE -> 4;
            case PENTAGON -> 5;
            case HEXAGON -> 6;
        };

        if(vertexCount != -1)
        {
            double radialFractions = ( 2 * PI ) / vertexCount;
            double radialAccumulator = MyMath.isEven(vertexCount) ? radialFractions / 2 : 0;

            for (int i = 0; i < vertexCount; i++)
            {
                Vec2 noramlVec2 = new Vec2(1,0);
                shape.add(new Vec2(
                        (noramlVec2.x * Math.cos(radialAccumulator) - noramlVec2.y * Math.sin(radialAccumulator)),
                        (noramlVec2.y * Math.cos(radialAccumulator) + noramlVec2.x * Math.sin(radialAccumulator))));

                radialAccumulator += radialFractions;
            }
        }
    }
    private void initVertices()
    {
        vertices.addAll(shape);
        updateVertices();
    }
    public void updateVertices() {
        vertices = calcVertices(position, rotation, scaling);
    }

    private ArrayList<Vec2> calcVertices(Vec2 position, double rotation, double scaling) {
        ArrayList<Vec2> output = new ArrayList<Vec2>();
        for (Vec2 vertex : shape) {
            Vec2 newVec2 = new Vec2(
                    (vertex.x * Math.cos(rotation) - vertex.y * Math.sin(rotation)) * scaling,
                    (vertex.y * Math.cos(rotation) + vertex.x * Math.sin(rotation)) * scaling);

            output.add(newVec2.add(position));
        }
        return output;
    }

    private void paintPolygon(ArrayList<Vec2> vertices)
    {
        if(debugOverlap)
        {fill(255,0,0);
        }else { fill(255);}

        beginShape();
        for (int i = 0; i < vertices.size() + 1; i++)
        {
            vertex((float) vertices.get(i % vertices.size()).x,(float) vertices.get(i % vertices.size()).y);
        }
        endShape();
    }

    private void checkForCollision(Vec2 deltaPos, int steps)
    {

        Vec2 simPos = deltaPos.add(position);
        ArrayList<Vec2> simVertices = calcVertices(simPos,rotation,scaling);

        ArrayList<GameplayObject> colliders = new ArrayList<>();

        for (GameplayObject obj : (List<GameplayObject>)(Object)UnderSquare3.gameObjects.get(EntityObject.class))
        {
            if(obj != this)
            {
                if(Collision.collisionCheckSAT(this,obj))
                {
                    colliders.add(obj);
                }
            }
        }

        if(!colliders.isEmpty()){
            simPos = position;
            Vec2 stepSize = deltaPos.divide(steps);
            Vec2 stepAcc = Vec2.ZERO_VECTOR2;

            int stepcounter = 0;
            for(int i = 0; i < steps; i++)
            {
                stepcounter++;
                simPos = simPos.add(stepAcc);
                simVertices = calcVertices(simPos,rotation,scaling);

                for (GameplayObject obj : colliders)
                {
                    if(Collision.collisionCheckSAT(this,obj))
                    {
                        println(stepcounter);
                        stepAcc = stepAcc.subtract(stepSize);
                        break;
                    }
                    stepAcc = stepAcc.add(stepSize);

                }
            }
            position = position.add(stepAcc);
        }
        else
        {
            position = position.add(deltaPos);
        }
    }

}
