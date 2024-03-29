package com.lukullu.undersquare.entities;

import com.lukullu.tbck.enums.Actions;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.DeltaTimer;
import com.lukullu.tbck.utils.InputManager;
import com.lukullu.tbck.utils.Vec2;

public class Player extends EntityObject {


    public Player(Shapes shapeDesc, Vec2 position, double rotation, double scaling) {
        super(shapeDesc, position, rotation, scaling, 1);
        mass = 2;
    }

    @Override
    public void update() {

        movement();
        super.update();
    }

    private void movement()
    {
        if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ applyForce(new Vec2(0  , -50)); }
        if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ applyForce(new Vec2(0  , 50));  }
        if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ applyForce(new Vec2(-50, 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ applyForce(new Vec2(50 , 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.ROTATE_CLOCKWISE          )){ updateRot(PI * DeltaTimer.getInstance().getDeltaTime());   }
        if(InputManager.getInstance().isActionQueued(Actions.ROTATE_COUNTERCLOCKWISE   )){ updateRot(-PI * DeltaTimer.getInstance().getDeltaTime());   }
        if(InputManager.getInstance().isActionQueued(Actions.SLOWMO  )){ DeltaTimer.getInstance().setTimeModifier(0.75); } else {DeltaTimer.getInstance().setTimeModifier(1);}
        /*if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ updatePos(new Vec2(0  , -10)); }
        if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ updatePos(new Vec2(0  , 10));  }
        if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ updatePos(new Vec2(-10, 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ updatePos(new Vec2(10 , 0));   }*/
    }

}
