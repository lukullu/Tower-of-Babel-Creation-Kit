package com.lukullu.oceanExperiment.spacialPartitioning;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.gameObjects.IAtom;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class QuadList implements ProcessingClass
{
    /*
    *   Used for Spacial Partitioning of 2D Point-Clouds into a uniform grid via a Z-Order-Curve
    */

    private int cellCount;
    private int depth;
    private final double initSideLength;
    private int sideLengthExponent;

    ArrayList<Vec2>[][] points;

    public QuadList(double sideLength, int depth)
    {
        this.depth = depth;
        this.initSideLength = sideLength;

        this.sideLengthExponent = adjustSideLength(sideLength);
        this.points = initPointArray();
    }

    /// --- INIT FUNCTIONS --- \\\
    private ArrayList[][] initPointArray()
    {
        this.cellCount = (int)Math.floor(Math.pow(2,sideLengthExponent) / Math.pow(2,sideLengthExponent-depth));
        ArrayList[][] out = new ArrayList[cellCount][cellCount];

        for(int y = 0; y < cellCount; y++)
            for(int x = 0; x < cellCount; x++)
                out[y][x] = new ArrayList<Vec2>();

        return out;
    }

    private int adjustSideLength(double sideLength)
    {
        int counter = 1;
        while (sideLength >= 1)
        {
            sideLength /= 2;
            counter++;
        }

        return counter;
    }

    public void partition(ArrayList<Vec2> points)
    {

    }


}
