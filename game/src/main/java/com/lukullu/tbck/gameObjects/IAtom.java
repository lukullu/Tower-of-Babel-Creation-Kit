package com.lukullu.tbck.gameObjects;

import com.tbck.math.Vec2;

public interface IAtom
{
    void update();
    void paint();
    void reset();
    void die();
    Vec2 getPosition();

}
