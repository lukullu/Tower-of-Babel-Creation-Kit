package com.lukullu.tbck.utils;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.enums.Actions;

import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements ProcessingClass {

    private static HashMap<Character, Actions> keyActions = new HashMap<>();
    private static ArrayList<Actions> currentActions = new ArrayList<>();
    private static InputManager single_instance = null;

    private static char oldKey;
    private boolean oldPressed = false;

    private InputManager()
    {
        loadConfig();
    }

    public static synchronized InputManager getInstance()
    {
        if (single_instance == null)
            single_instance = new InputManager();

        return single_instance;
    }

    // TODO config for keyActions
    private void loadConfig()
    {
        // temp
        keyActions.put('w',Actions.FORWARD);
        keyActions.put('s',Actions.BACKWARD);
        keyActions.put('a',Actions.LEFT);
        keyActions.put('d',Actions.RIGHT);
        keyActions.put('e',Actions.ROTATE_CLOCKWISE);
        keyActions.put('q',Actions.ROTATE_COUNTERCLOCKWISE);
        keyActions.put('f',Actions.SCALE_UP);
        keyActions.put('r',Actions.SCALE_DOWN);
        keyActions.put('l',Actions.SLOWMO);
        keyActions.put('k',Actions.DEBUGTOGGLE);
    }
    private void saveConfig(){}
    private void changeKey(){}

    public boolean isActionQueued(Actions action)
    {
        return currentActions.contains(action);
    }

    public void keyPressed()
    {
        char key = getKey();

        if (keyActions.containsKey(key))
        {
            if(!currentActions.contains(keyActions.get(key)))
            {
                currentActions.add(keyActions.get(key));
            }
        }
    }
    public void keyReleased()
    {
        char key = getKey();

        if (keyActions.containsKey(key))
        {
            currentActions.remove(keyActions.get(key));
        }
    }

}
