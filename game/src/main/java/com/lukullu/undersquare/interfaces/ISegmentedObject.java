package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.LineSegment;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ISegmentedObject extends IGameObject, ICollidableObject
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

    default void paintSegments()
    {
        for (Polygon polygon : getPolygons())
            if(polygon instanceof SegmentData)
                if(((SegmentData)(Object) polygon).enabled)
                    paintPolygon(polygon);
    }

    // TODO: Test intensively
    default void checkSegmentIntegrity(SegmentData startSegment)
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
                return;
        }


        HashSet<HashSet<SegmentData>> segmentSetsSet = new HashSet<>();
        for(SegmentData neighbor : startSegment.neighborSegments)
        {
            segmentSetsSet.add(neighbor.checkNeighborsRec(new HashSet<>()));
        }

        // Everything is Awesome
        if(segmentSetsSet.size() == 1)
            return;


        // Die
        if(segmentSetsSet.isEmpty())
        {
            // TODO: delete this Object
            System.out.println("Anakin, Start Panicking...");
            return;
        }

        // Perform Mitosis
        System.out.println("We did it lads");
        //TODO: create multiple daughter SegmentObjects from differing Sets

    }

    default void setSegmentInactive(SegmentData segment)
    {
        segment.enabled = false;
        checkSegmentIntegrity(segment);
        setInteriorLines(initInteriorLines(getPolygons()));
    }

    default void takeDamage(SegmentData segment, Vec2 force)
    {
        // ToDo: implement
        segment.healthPoints--;
        if(segment.healthPoints <= 0)
            setSegmentInactive(segment);
    }

    @Override
    default ArrayList<LineSegment> initInteriorLines(ArrayList<? extends Polygon> polygons)
    {

        ArrayList<LineSegment> out = new ArrayList<>();

        for (Polygon query : polygons)
        {
            if(query instanceof SegmentData)
                if(!((SegmentData)query).enabled)
                    continue;

            for(Polygon polygon : polygons)
            {
                if(polygon instanceof SegmentData)
                    if(!((SegmentData)polygon).enabled)
                        continue;

                if(polygon.equals(query))
                    continue;

                for (int j = 0; j < polygon.vertices.size(); j++)
                {

                    if(query.vertices.contains(polygon.vertices.get(j)) && query.vertices.contains(polygon.vertices.get((j + 1) % polygon.vertices.size())))
                        out.add(new LineSegment(polygon.vertices.get(j),polygon.vertices.get((j + 1) % polygon.vertices.size())));

                }
            }
        }

        return out;
    }



}
