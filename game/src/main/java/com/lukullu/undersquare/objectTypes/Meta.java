package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.utils.CollisionResult;
import com.lukullu.undersquare.interfaces.ISegmentedObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Meta extends MetaObject implements ISegmentedObject
{

    public Meta(String psff_resource, Vec2 position, double rotation, double scaling, Consumer<CollisionResult> action, boolean isTrigger) {
        super(SegmentDataManager.loadInternal(psff_resource), position, rotation, scaling, action, isTrigger);
        // TODO: remove Debug
        super.debugAlpha = 50f;
    }

    public Meta(ArrayList<? extends Polygon> polygons, Vec2 position, int rotation, int scaling, Consumer<CollisionResult> action, boolean isTrigger) {
        super(polygons, position, rotation, scaling, action, isTrigger);
    }
}
