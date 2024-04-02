package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.tbck.data.entity.SegmentData;

import java.util.List;

public interface ISegmentedObject extends IGameObject
{
    default List<SegmentData> getSegments()
    {
        return getShape().stream().map((poly)->{return (SegmentData)(Object)poly;}).toList();
    }
    default void paint()
    {
        for (int i = 0; i < getPolygons().size(); i++)
            if(getSegments().get(i).enabled)
                paintPolygon(getPolygons().get(i));
    }



}
