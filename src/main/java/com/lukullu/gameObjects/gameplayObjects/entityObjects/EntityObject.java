package com.lukullu.gameObjects.gameplayObjects.entityObjects;

import com.lukullu.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.enums.Shapes;
import com.lukullu.utils.Vec2;

public class EntityObject extends GameplayObject {

    public EntityObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling, int pushPriority)
    {
        super(shapeDesc, position, rotation, scaling, pushPriority);
    }

}
