package com.lukullu.tbck.gameObjects;

import com.kilix.processing.ProcessingClass;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public interface IGameObject extends ProcessingClass {

    int getID();
    void update();
    void paint();
    void reset();
    Vec2 getPosition();
    ArrayList<Polygon> getPolygons();
    ArrayList<Polygon> getShape();
    void setShape(ArrayList<Polygon> polygons);
    default void paintPolygon(Polygon polygon)
    {
        //fill(debugColor[0],debugColor[1],debugColor[2],debugAlpha);

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
