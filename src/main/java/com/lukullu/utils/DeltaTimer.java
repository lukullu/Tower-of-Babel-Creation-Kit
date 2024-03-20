package com.lukullu.utils;

public class DeltaTimer
{
    private static DeltaTimer single_instance = null;

    public static synchronized DeltaTimer getInstance()
    {
        if (single_instance == null)
            single_instance = new DeltaTimer();

        return single_instance;
    }

    private long lastFrameTime = System.currentTimeMillis();
    private int deltaTime;
    private DeltaTimer(){ update(); }
    public int getDeltaTime(){ return deltaTime; }
    public void update()
    {
        deltaTime = (int)(System.currentTimeMillis() - lastFrameTime);
        lastFrameTime = System.currentTimeMillis();
    }

}
