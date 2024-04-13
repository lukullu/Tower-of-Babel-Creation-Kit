package com.lukullu.undersquare.components;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.enums.Actions;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.utils.Collision;
import com.lukullu.tbck.utils.DebugUtil;
import com.lukullu.tbck.utils.DeltaTimer;
import com.lukullu.tbck.utils.InputManager;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class Camera implements ProcessingClass
{
    private static Camera single_instance = null;

    public static synchronized Camera getInstance()
    {
        if (single_instance == null)
            single_instance = new Camera();

        return single_instance;
    }
    private GameplayObject target;
    private ArrayList<GameplayObject> possibleTargets = new ArrayList<>();
    private int currentTargetIndex = 0;
    private Vec2 lastPosition = new Vec2(0,0);
    private Vec2 cameraPosition = new Vec2(0,0);
    private boolean matrixIsActive = false;
    private boolean hasBeenReleased = true;
    private Vec2 freeZoneDimensions = new Vec2(getWidth()/4d,getHeight()/3d);
    private double breakTime = 300; // ms
    private Vec2 lastdeltaPos = new Vec2(0,0);

    public void setPossibleTargets(ArrayList<GameplayObject> targets)
    {
        possibleTargets = new ArrayList<>();
        possibleTargets.addAll(targets);
    }
    public void setTarget(GameplayObject target)
    {
        this.target = target;
        this.lastPosition = target.getPosition();
        this.cameraPosition = target.getPosition().add(new Vec2(-getWidth()/2d,-getHeight()/2d));
    }

    public void update()
    {

        DebugUtil.getInstance().addDynamicText("Current Camera: " + currentTargetIndex);

        if(hasBeenReleased)
        {
            if(InputManager.getInstance().isActionQueued(Actions.CYCLE_CAMERA))
            {
                if(!possibleTargets.isEmpty())
                {
                    currentTargetIndex = (currentTargetIndex+1)%(possibleTargets.size()+1);
                    if(currentTargetIndex != 0)
                        setTarget(possibleTargets.get(currentTargetIndex-1));
                    hasBeenReleased = false;
                }

            }
        }
        else
        {
            if(!InputManager.getInstance().isActionQueued(Actions.CYCLE_CAMERA))
            {
                hasBeenReleased = true;
            }
        }
    }

    public void push()
    {
        if (matrixIsActive)
            popMatrix();

        if(currentTargetIndex == 0)
            return;

        matrixIsActive = true;
        pushMatrix();

        boolean isCollidingX;
        boolean isCollidingY;

        if(target != null)
        {
            Vec2 cameraMidPos = cameraPosition.add(new Vec2(getWidth()/2d,getHeight()/2d));

            isCollidingX = Math.abs(target.getPosition().subtract(cameraMidPos).x) <= freeZoneDimensions.x/2d;
            isCollidingY = Math.abs(target.getPosition().subtract(cameraMidPos).y) <= freeZoneDimensions.y/2d;

            if(!isCollidingX || !isCollidingY)
            {
                lastdeltaPos = target.getPosition().subtract(lastPosition).multiply(new Vec2(!isCollidingX ? 1 : 0, !isCollidingY ? 1 : 0));
                cameraPosition = cameraPosition.add(lastdeltaPos);
            }
            else
            {
                if(!lastdeltaPos.equals(Vec2.ZERO_VECTOR2))
                {
                    lastdeltaPos = lastdeltaPos.subtract(lastdeltaPos.multiply((DeltaTimer.getInstance().getDeltaTime() * 1000)/breakTime));
                    cameraPosition = cameraPosition.add(lastdeltaPos);
                    System.out.println(lastdeltaPos);
                }
            }

            translate((float)(-cameraPosition.x),(float)(-cameraPosition.y));
            this.lastPosition = target.getPosition();
        }

    }

    public void pop()
    {
        if(matrixIsActive)
            popMatrix();

        matrixIsActive = false;
    }

}
