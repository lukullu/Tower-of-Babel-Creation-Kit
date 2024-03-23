package com.lukullu.tbck.utils;

public class DeltaTimer
{
    private static DeltaTimer single_instance = null;

    private static double timeModifier = 1;

    public static synchronized DeltaTimer getInstance()
    {
        if (single_instance == null)
            single_instance = new DeltaTimer();

        return single_instance;
    }

    private double lastFrameTime = System.currentTimeMillis() / 1000.0;
    private double deltaTime;
    private DeltaTimer(){ update(); }
    public double getDeltaTime(){ return deltaTime * timeModifier; }
    public void setTimeModifier(double value){ timeModifier = value; }
    public void update()
    {
        deltaTime = ((System.currentTimeMillis() / 1000.0) - lastFrameTime);
        lastFrameTime = System.currentTimeMillis() / 1000.0;
    }

}
