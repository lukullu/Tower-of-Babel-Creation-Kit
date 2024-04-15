package com.lukullu.tbck.utils;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;

import java.util.ArrayList;

public class GameObjectMultiHashMap extends MultiHashMap<Class<? extends GameplayObject>, GameplayObject>
{
    public void put(GameplayObject value) {
        super.computeIfAbsent(value.getClass(), a -> new ArrayList<>()).add(value);
    }
    public void putEntity(GameplayObject value)
    {
        super.computeIfAbsent(EntityObject.class, a -> new ArrayList<>()).add(value);
    }
    public void putMeta(GameplayObject value)
    {
        super.computeIfAbsent(MetaObject.class, a -> new ArrayList<>()).add(value);
    }

    public void putStatic(GameplayObject value)
    {
        super.computeIfAbsent(StaticObject.class, a -> new ArrayList<>()).add(value);
    }

    // TODO: make this shit work!!!
    public void remove(GameplayObject value) {
        values().forEach(list -> list.removeIf(item ->{return item.equals(value);}));
    }

}
