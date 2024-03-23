package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;

import java.util.ArrayList;

public class GameObjectMultiHashMap extends MultiHashMap<Class<? extends IGameObject>, IGameObject>
{
    public void put(IGameObject value) {
        super.computeIfAbsent(value.getClass(), a -> new ArrayList<>()).add(value);
    }
    public void putEntity(IGameObject value)
    {
        super.computeIfAbsent(EntityObject.class, a -> new ArrayList<>()).add(value);
    }
    public void putMetaObject(IGameObject value)
    {
        super.computeIfAbsent(MetaObject.class, a -> new ArrayList<>()).add(value);
    }
}
