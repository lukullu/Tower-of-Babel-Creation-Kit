package com.lukullu.undersquare.entityTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.Polygon;
import com.lukullu.tbck.utils.Vec2;
import com.lukullu.undersquare.utils.PSFF_Utils;
import com.lukullu.undersquare.utils.SegmentData;

import java.util.ArrayList;

public class SegmentEntity extends EntityObject
{

    ArrayList<SegmentData> segments = new ArrayList<>();

    public SegmentEntity(String psff_url, Vec2 position, double rotation, double scaling)
    {
        super(polygonsFromPSFF(psff_url,scaling),position,rotation);
        segments = segmentsFromPSFF(psff_url);
    }

    private static ArrayList<Polygon> polygonsFromPSFF(String url,double scaling) // Proprietary Shape File Format
    {
        ArrayList<Polygon> polygons = new ArrayList<>();
        ArrayList<String> dataLines = PSFF_Utils.getLines(url);
        ArrayList<SegmentData> segmentData = new ArrayList<>();
        for (String line : dataLines)
        {
            segmentData.add(PSFF_Utils.getVerticesFromLine(line));
        }
        for(SegmentData segment : segmentData)
        {
            ArrayList<Vec2> vertices = new ArrayList<>(segment.vertices.stream().map((v)->{return v.multiply(scaling);}).toList());
            polygons.add(new Polygon(vertices));
        }

        return polygons;

    }

    private static ArrayList<SegmentData> segmentsFromPSFF(String url) // Proprietary Shape File Format
    {
        ArrayList<Polygon> polygons = new ArrayList<>();
        ArrayList<String> dataLines = PSFF_Utils.getLines(url);
        ArrayList<SegmentData> segmentData = new ArrayList<>();
        for (String line : dataLines)
        {
            segmentData.add(PSFF_Utils.getVerticesFromLine(line));
        }

        return segmentData;

    }

}
