package com.lukullu.utils;

import com.lukullu.gameObjects.IGameObject;
import com.lukullu.gameObjects.gameplayObjects.entityObjects.EntityObject;

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
}
