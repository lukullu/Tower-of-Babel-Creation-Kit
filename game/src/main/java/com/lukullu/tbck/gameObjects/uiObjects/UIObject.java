package com.lukullu.tbck.gameObjects.uiObjects;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class UIObject implements IGameObject {
    public int getID(){ return 1; }
    public void update()
    {

    }
    public void paint()
    {

    }

    @Override
    public void paintPolygon(Polygon polygon) {

    }

    @Override
    public Vec2 getPosition() {
        return null;
    }

    @Override
    public ArrayList<Polygon> getPolygons() {
        return null;
    }

    @Override
    public ArrayList<Polygon> getShape() {
        return null;
    }
}
