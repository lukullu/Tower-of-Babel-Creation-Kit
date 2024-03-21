package com.lukullu.gameObjects.gameplayObjects.entityObjects.Player;

import com.lukullu.enums.Actions;
import com.lukullu.gameObjects.gameplayObjects.entityObjects.EntityObject;
import com.lukullu.enums.Shapes;
import com.lukullu.utils.InputManager;
import com.lukullu.utils.Vec2;

public class Player extends EntityObject {

    public Player(Shapes shapeDesc, Vec2 position, double rotation, double scaling) {
        super(shapeDesc, position, rotation, scaling, 1);
    }

    @Override
    public void update() {

        movement();
        super.update();
    }

    private void movement()
    {
        //if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ applyForce(new Vec2(0  , -5000)); }
        //if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ applyForce(new Vec2(0  , 5000));  }
        //if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ applyForce(new Vec2(-5000, 0));   }
        //if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ applyForce(new Vec2(5000 , 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ updatePos(new Vec2(0  , -10)); }
        if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ updatePos(new Vec2(0  , 10));  }
        if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ updatePos(new Vec2(-10, 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ updatePos(new Vec2(10 , 0));   }
    }

}
