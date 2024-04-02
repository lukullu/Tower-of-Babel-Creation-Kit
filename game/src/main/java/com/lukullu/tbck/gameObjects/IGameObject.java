package com.lukullu.tbck.gameObjects;

import com.tbck.math.Polygon;

import java.util.ArrayList;

public interface IGameObject {

    int getID();
    void update();
    void paint();
    void paintPolygon(Polygon polygon);
    ArrayList<Polygon> getPolygons();
    ArrayList<Polygon> getShape();

}
