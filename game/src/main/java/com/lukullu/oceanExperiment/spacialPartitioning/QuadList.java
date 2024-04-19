package com.lukullu.oceanExperiment.spacialPartitioning;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.gameObjects.IAtom;
import com.tbck.math.UInt32;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;

public class QuadList implements ProcessingClass
{
    /*
    *   Used for Spacial Partitioning of 2D Point-Clouds into a uniform grid via a Z-Order-Curve
    */

    private int cellCount;
    private int depth;
    private final double initSideLength;
    private int sideLengthExponent;
    private int partitionExponent;
    ArrayList<IAtom>[][] partitionedPoints;

    public QuadList(double sideLength, int depth)
    {
        this.depth = depth;
        this.initSideLength = sideLength;

        this.sideLengthExponent = calcSideLengthExponent(sideLength);
        this.partitionedPoints = initPointArray();
        partitionExponent = (sideLengthExponent - depth) * 2;

    }

    /// --- INIT FUNCTIONS --- \\\
    private ArrayList<IAtom>[][] initPointArray()
    {
        this.cellCount = (int)Math.pow(2,depth*2);
        ArrayList<IAtom>[][] out = new ArrayList[cellCount][cellCount];

        for(int y = 0; y < cellCount; y++)
            for(int x = 0; x < cellCount; x++)
                out[y][x] = new ArrayList<IAtom>();

        return out;
    }

    private int calcSideLengthExponent(double sideLength)
    {
        int counter = 1;
        while (sideLength >= 2)
        {
            sideLength /= 2;
            counter++;
        }

        return counter;
    }

    public void partition(List<IAtom> points)
    {
        // calc how big a cell should be
        int partitionExponent = (sideLengthExponent - depth)*2;

        if(partitionExponent <= 0)
            partitionExponent = 1;

        // clear arrays
        partitionedPoints = new ArrayList[(int)Math.pow(2,depth*2)][(int)Math.pow(2,depth*2)];

        // fill points into their partitions
        for(IAtom point : points)
        {
            UInt32 zOrderPosition = point.getPosition().getZOrderPosition();
            long zOrderCoords = zOrderPosition.longValue() / (long) Math.pow(2,partitionExponent);
            Vec2 cartesianIndex = Vec2.getFromZOrderPosition(new UInt32(zOrderCoords));
            if(partitionedPoints[(int)cartesianIndex.y][(int)cartesianIndex.x] == null)
                partitionedPoints[(int)cartesianIndex.y][(int)cartesianIndex.x] = new ArrayList<>();
            partitionedPoints[(int)cartesianIndex.y][(int)cartesianIndex.x].add(point);
        }

    }

    public Vec2 getCartesianIndex(Vec2 position)
    {;
        UInt32 zOrderPosition = position.getZOrderPosition();
        long zOrderCoords = zOrderPosition.longValue() / (long) Math.pow(2,partitionExponent);
        return Vec2.getFromZOrderPosition(new UInt32(zOrderCoords));
    }

    public ArrayList<IAtom>[][] getPartitionedPoints() {
        return partitionedPoints;
    }

    public void paint()
    {
        fill(0,30);
        stroke(1);
        for(int y = 0; y < cellCount; y++)
            for(int x = 0; x < cellCount; x++)
            {
                int partitionExponent = sideLengthExponent - depth;
                rect((float)(x*Math.pow(2,partitionExponent)),(float)(y*Math.pow(2,partitionExponent)),(float)Math.pow(2,partitionExponent),(float)Math.pow(2,partitionExponent));
            }
    }
}
