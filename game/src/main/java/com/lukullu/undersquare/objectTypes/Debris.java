package com.lukullu.undersquare.objectTypes;

import com.lukullu.tbck.utils.DeltaTimer;
import com.tbck.math.Vec2;

public class Debris extends Entity
{
    protected double timeToLive;
    private double timeSinceBirth = 0;

    // Debug TODO: remove
    private boolean isDead = false;

    public Debris(String psff_resource, Vec2 position, double rotation, double scaling, double timeToLive) {
        super(psff_resource, position, rotation, scaling);
        this.timeToLive = timeToLive;
    }

    @Override
    public void update()
    {

        super.update();

        timeSinceBirth += DeltaTimer.getInstance().getDeltaTime();

        if(timeSinceBirth >= timeToLive && !isDead)
        {
            die();
            isDead = true;
        }

    }

}
