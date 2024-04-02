package com.lukullu.undersquare.utils.segmentTree;

import com.tbck.data.entity.SegmentData;

import java.sql.Array;
import java.util.ArrayList;

public class SegmentTree
{
    SegmentNode root;

    SegmentTree(ArrayList<SegmentData> segments)
    {
        construct(segments);
    }

    public boolean contains(SegmentData data){ return root.contains(data); }
    public SegmentTree[] split(SegmentData shatterPoint)
    {
        if(!this.contains(shatterPoint))
            return new SegmentTree[]{this};

        return null; //TODO
    }

    public void construct(ArrayList<SegmentData> segments)
    {

    }

}
