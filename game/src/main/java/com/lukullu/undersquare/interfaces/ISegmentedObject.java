package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.Polygon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ISegmentedObject extends IGameObject
{
    default List<SegmentData> getSegments()
    {
        return getShape().stream().map((poly)->{return (SegmentData)(Object)poly;}).toList();
    }
    default void setSegments(List<SegmentData> segments)
    {
        setShape(new ArrayList<>(segments.stream().map((poly)->{return (Polygon)(Object)poly;}).toList()));
    }
    default void paint()
    {
        for (int i = 0; i < getPolygons().size(); i++)
            if(getSegments().get(i).enabled)
                paintPolygon(getPolygons().get(i));
    }

    // TODO: Test intensively
    default boolean checkSegmentIntegrity()
    {
        SegmentData startSegment = null;
        for(SegmentData segment : getSegments())
        {
            if(segment.isValid && segment.enabled)
            {
                startSegment = segment;
                break;
            }
        }

        if(startSegment == null)
            return false;

        HashSet<HashSet<SegmentData>> segmentSetsSet = new HashSet<>();
        for(SegmentData neighbor : startSegment.neighborSegments)
        {
            segmentSetsSet.add(neighbor.checkNeighborsRec(new HashSet<>()));
        }

        if(segmentSetsSet.size() == 1)
            return true;

        if(segmentSetsSet.isEmpty())
        {
            System.out.println("Anakin, Start Panicking...");
            //TODO: Die
            return false;
        }

        //TODO create multiple daughter SegmentObjects from differing Sets
        return false;

    }



}
