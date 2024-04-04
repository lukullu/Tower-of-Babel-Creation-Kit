package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ISegmentedObject extends IGameObject
{

    default void initSegments()
    {
        for (SegmentData query : getSegments())
        {
            query.neighborSegments = new ArrayList<>();
            for(SegmentData segment : getSegments())
            {
                if(segment.equals(query))
                    continue;

                for (Vec2 vertex : query.vertices)
                {
                    if(segment.vertices.contains(vertex))
                    {
                        query.neighborSegments.add(segment);
                    }
                }
            }
        }
    }

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
    default boolean checkSegmentIntegrity(SegmentData startSegment)
    {
        if(startSegment == null)
        {
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
        }


        HashSet<HashSet<SegmentData>> segmentSetsSet = new HashSet<>();
        for(SegmentData neighbor : startSegment.neighborSegments)
        {
            segmentSetsSet.add(neighbor.checkNeighborsRec(new HashSet<>()));
        }

        // Everything is Awesome
        if(segmentSetsSet.size() == 1)
            return true;


        // Die
        if(segmentSetsSet.isEmpty())
        {
            // TODO: delete this Object
            System.out.println("Anakin, Start Panicking...");
            return false;
        }

        // Perform Mitosis
        //TODO: create multiple daughter SegmentObjects from differing Sets

        return false;

    }



}
