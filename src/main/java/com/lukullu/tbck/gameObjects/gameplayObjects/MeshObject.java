package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.MyMath;
import com.lukullu.tbck.utils.Triangle;
import com.lukullu.tbck.utils.Vec2;

import java.util.ArrayList;

public class MeshObject extends GameplayObject
{

    ArrayList<Triangle> shapeTriangles = new ArrayList<>();

    MeshObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
        shapeTriangles = constructTrianglesRec(new ArrayList<>(),getVertices(),1);
    }

    @Override
    public void update()
    {
        super.update();
        //shapeTriangles = constructTrianglesRec(new ArrayList<>(),this.getVertices(),1);
    }

    @Override
    public void paint()
    {
        //super.paint();
        fill(255);
        for (Triangle triangle: shapeTriangles)
        {
            //if(!triangle.enabled) fill(255,0,0);
            //else fill(255);
            if(triangle.enabled) paintTriangle(triangle);
        }

    }

    @Override
    public void updatePos(Vec2 delta)
    {
        super.updatePos(delta);

        for(Triangle triangle : shapeTriangles)
        {
            triangle.updatePos(delta);
        }
    }

    @Override
    public void updateRot(double delta)
    {
        super.updateRot(delta);

        for(Triangle triangle : shapeTriangles)
        {
            triangle.updateRot(this.getPosition(),delta);
        }
    }

    // depth has to start at 1!
    private ArrayList<Triangle> constructTrianglesRec(ArrayList<Triangle> triangles, ArrayList<Vec2> vertices, int depth)
    {

        // Recursion Beginning
        if(Math.ceil(vertices.size() / 2.0) < 3)
        {
            for (int i = 0; i < vertices.size()-2; i++)
            {
                triangles.add(new Triangle(vertices.get(i*2),vertices.get(i*2+1),vertices.get((i*2+2) % vertices.size())));
            }
            return triangles;
        }

        // Recursion Body
        int trianglesInLayer = (int) Math.floor(shape.size() / Math.pow(2,depth));
        ArrayList<Vec2> newVertices = new ArrayList<>();
        for(int i = 0; i < trianglesInLayer; i++)
        {
            triangles.add(new Triangle(vertices.get(i*2),vertices.get(i*2+1),vertices.get((i*2+2) % vertices.size())));
            newVertices.add(vertices.get((i*2+2) % vertices.size()));
        }

        if(trianglesInLayer * Math.pow(2,depth) != shape.size())
        {
            newVertices.add(vertices.get(0));
        }

        return constructTrianglesRec(triangles,newVertices,depth+1);
    }

    private void paintTriangle(Triangle triangle)
    {
        beginShape();
        vertex((float)triangle.v1.x,(float)triangle.v1.y);
        vertex((float)triangle.v2.x,(float)triangle.v2.y);
        vertex((float)triangle.v3.x,(float)triangle.v3.y);
        vertex((float)triangle.v1.x,(float)triangle.v1.y);
        endShape();
    }

}
