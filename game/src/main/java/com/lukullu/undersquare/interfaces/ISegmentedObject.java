package com.lukullu.undersquare.interfaces;

import com.lukullu.tbck.enums.Actions;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.InputManager;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.undersquare.enums.Affiliation;
import com.lukullu.undersquare.objectTypes.Entity;
import com.lukullu.undersquare.objectTypes.Meta;
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

        for(SegmentData query : getSegments())
        {
            query.neighborSegments = new ArrayList<>();
            for(SegmentData segment : getSegments())
            {
                if(segment.equals(query))
                    continue;

                int counter = 0;
                for(Vec2 queryVertex : query.vertices)
                {
                    if(segment.vertices.contains(queryVertex))
                        counter++;
                }
                if(counter >= 2)
                    query.neighborSegments.add(segment);
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

        ArrayList<ArrayList<SegmentData>> segmentGroups = new ArrayList<>();
        for(SegmentData neighbor : startSegment.neighborSegments)
        {
            if(neighbor.enabled)
                segmentGroups.add(neighbor.checkNeighborsRec(new ArrayList<SegmentData>()));
        }

        // Everything is Awesome
        if(segmentGroups.size() == 1)
            return;

        // Die
        if(segmentGroups.isEmpty())
        {
            die();
            return;
        }

        // Perform Mitosis

        die();
        for(ArrayList<SegmentData> segments : segmentGroups)
        {
            Vec2 position = Polygon.getPositionFromPolygons(new ArrayList<>(segments));

            ArrayList<SegmentData> newSegments = new ArrayList<>();
            for(SegmentData segment : segments)
            {
                SegmentData newSegment = new SegmentData(segment);
                ArrayList<Vec2> newVertices = new ArrayList<>();
                for(Vec2 vertex : segment.vertices)
                {
                    newVertices.add(vertex.subtract(position));
                }
                newSegment.vertices = newVertices;
                newSegments.add(newSegment);
            }

            if(this instanceof Entity)
                birth(new Entity(newSegments,position,0,0,((Entity)this).getAffiliation()));
            else
                birth(new Entity(newSegments,position,0,0, Affiliation.NONE));

        }

    }

    default void setSegmentInactive(SegmentData segment)
    {
        if(!segment.enabled)
            return;

        segment.enabled = false;
        checkSegmentIntegrity(segment);
        setInteriorLines(initInteriorLines(getPolygons()));
    }

    default void takeDamage(SegmentData segment, Vec2 force)
    {
        // ToDo: implement ArmorPoints and Force based damage
        segment.healthPoints--;

        if(segment.healthPoints <= 0 && InputManager.getInstance().isActionQueued(Actions.DEBUGTOGGLE))
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
