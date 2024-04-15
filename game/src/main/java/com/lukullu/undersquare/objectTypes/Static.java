package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

public class Static extends StaticObject implements ISegmentedObject
{
    public Static(String psff_resource, Vec2 position, double rotation, double scaling)
    {
        super(SegmentDataManager.copyFromResource(SegmentDataManager.loadInternal(psff_resource)), position, rotation, scaling);
    }

}
