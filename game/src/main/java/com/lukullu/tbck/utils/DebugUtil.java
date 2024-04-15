package com.lukullu.tbck.utils;

import com.kilix.processing.ProcessingClass;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class DebugUtil implements ProcessingClass
{

    private static DebugUtil single_instance = null;

    public static synchronized DebugUtil getInstance()
    {
        if (single_instance == null)
            single_instance = new DebugUtil();

        return single_instance;
    }
    private DebugUtil(){}

    private static final Vec2 position = new Vec2(40,40);
    private static final float TEXT_SPACING = 20;
    ArrayList<String> dynamicText = new ArrayList<>();
    ArrayList<String> staticText = new ArrayList<>();

    public void addDynamicText(String string)
    {
        dynamicText.add(string);
    }

    public void addStaticText(String string)
    {
        staticText.add(string);
    }

    public void update()
    {

        fill(0,200,50);

        textSize(16);
        text("Dynamic Messages:",(float)position.x, (float)position.y);
        textSize(12);

        for(int i = 0; i < dynamicText.size(); i++)
        {
            text(dynamicText.get(i),(float)position.x,(float)position.y + TEXT_SPACING*(i+1));
        }

        textSize(16);
        text("Static Messages:",(float)position.x, (float)position.y + TEXT_SPACING * (dynamicText.size()+2));
        textSize(12);

        for(int i = 0; i < staticText.size(); i++)
        {
            text(staticText.get(i),(float)position.x,(float)position.y + TEXT_SPACING * i + TEXT_SPACING * (dynamicText.size()+3));
        }

        dynamicText = new ArrayList<>();
    }



}
