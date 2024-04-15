package com.lukullu.tbck.gameObjects;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public interface IGameObject extends ProcessingClass {

    int getID();
    void update();
    void paint();
    void reset();
    void die();
    void birth(EntityObject obj);

    void paintPolygon(Polygon polygon);

    Vec2 getPosition();
    ArrayList<Polygon> getPolygons();
    ArrayList<Polygon> getShape();
    void setShape(ArrayList<Polygon> polygons);

}
