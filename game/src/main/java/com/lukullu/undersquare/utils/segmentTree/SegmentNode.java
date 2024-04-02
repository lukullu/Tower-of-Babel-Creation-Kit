package com.lukullu.undersquare.utils.segmentTree;

import com.tbck.data.entity.SegmentData;

import java.util.ArrayList;

public class SegmentNode
{
    public SegmentData segmentData;
    public ArrayList<SegmentNode> children;
    public boolean isLeaf;
    SegmentNode(SegmentData segmentData, ArrayList<SegmentNode> children)
    {
        this.segmentData = segmentData;
        this.children = children;
        this.isLeaf = children.isEmpty();
    }
    public boolean contains(SegmentData data){ return !isLeaf && (segmentData.equals(data) || children.stream().map((child) -> {return child.contains(data);}).toList().contains(true)); }

}
