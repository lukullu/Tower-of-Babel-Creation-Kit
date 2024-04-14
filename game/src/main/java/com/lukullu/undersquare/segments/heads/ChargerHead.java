package com.lukullu.undersquare.segments.heads;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.utils.DeltaTimer;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.undersquare.objectTypes.Entity;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class ChargerHead extends SegmentData implements IHeadSegment
{

    private static final double sensoryRange = 600;
    private static final double coolDownTimeMs = 500;
    private static final double chargeTimeMs = 500;
    private static final double chargeForce = 50;

    private AiState state = AiState.IDLE;
    private Entity target = null;
    private double cooldownTimer = coolDownTimeMs;

    public ChargerHead(SegmentData segment) {
        super(segment);
    }
    public ChargerHead(ArrayList<Vec2> vertices) {
        super(vertices);
    }

    public void update(Entity owner)
    {

        behavior(owner);

        cooldownTimer -= DeltaTimer.getInstance().getDeltaTime() * 1000;
    }

    public void behavior(Entity owner)
    {
        switch (state)
        {
            case IDLE -> idleBehavior(owner);
            case READY -> readyBehavior(owner);
            case CHARGE -> chargeBehavior(owner);
            default -> { return; }
        }
    }

    private void idleBehavior(Entity owner)
    {
        Entity target = selectTarget(owner);
        if(target != null)
        {
            this.target = target;
            state = AiState.READY;
        }
    }
    private void readyBehavior(Entity owner)
    {
        if(!Entity.isEntityInRange(this.getPosition(),target,sensoryRange))
        {
            state = AiState.IDLE;
            cooldownTimer = coolDownTimeMs;
            return;
        }

        if(cooldownTimer <= 0)
        {
            state = AiState.CHARGE;
            cooldownTimer = chargeTimeMs;
            return;
        }

    }
    private void chargeBehavior(Entity owner)
    {
        if(!Entity.isEntityInRange(this.getPosition(),target,sensoryRange))
        {
            state = AiState.IDLE;
            cooldownTimer = coolDownTimeMs;
            return;
        }


        if(cooldownTimer <= 0)
        {
            state = AiState.READY;
            cooldownTimer = coolDownTimeMs;
            return;
        }

        // Move Entity
        owner.applyForce(target.getPosition().subtract(this.getPosition()).normalise().multiply(chargeForce));

    }

    protected Entity selectTarget(Entity owner)
    {
        ArrayList<GameplayObject> allObjects = new ArrayList<>(UnderSquare3.gameObjects.get(EntityObject.class));
        ArrayList<Entity> allEntities = new ArrayList<>();
        for(GameplayObject obj : allObjects)
        {
            if(obj instanceof Entity)
                allEntities.add((Entity)obj);
        }

        ArrayList<Entity> nearbyEntities = Entity.getEntitiesInRange(this.getPosition(), allEntities, sensoryRange);

        if(nearbyEntities.isEmpty())
            return null;

        double minDistance = Double.MIN_VALUE;
        Entity bestTarget = null;
        for(Entity entity : nearbyEntities)
        {
            double distance = entity.getPosition().subtract(this.getPosition()).length2();
            if(distance < minDistance && target.getAffiliation() != owner.getAffiliation())
            {
                bestTarget = entity;
                minDistance = distance;
            }
        }

        return bestTarget;
    }

    private enum AiState
    {
        IDLE,
        READY,
        CHARGE;
    }

}
