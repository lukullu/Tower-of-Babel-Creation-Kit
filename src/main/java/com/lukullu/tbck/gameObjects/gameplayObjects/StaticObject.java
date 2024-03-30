package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Polygon;
import com.lukullu.tbck.utils.Vec2;

import java.util.ArrayList;

public class StaticObject extends GameplayObject
{

    public StaticObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
    }

    public StaticObject(ArrayList<Polygon> polygons, Vec2 position)
    {
        super(new ArrayList<>(polygons
                .stream()
                .map((polygon) -> new Polygon(new ArrayList<>(polygon
                        .getVertices()
                        .stream()
                        .map((vertex)->vertex
                                .add(position))
                        .toList())))
                .toList()),position,0);
    }



}
