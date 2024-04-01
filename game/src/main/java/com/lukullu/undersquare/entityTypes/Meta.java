package com.lukullu.undersquare.entityTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Meta extends MetaObject implements ISegmentedObject
{
    public Meta(String psff_resource, Vec2 position, Runnable action, boolean isTrigger) {
        super(SegmentDataManager.loadInternal(psff_resource), position, action, isTrigger);
    }

}
