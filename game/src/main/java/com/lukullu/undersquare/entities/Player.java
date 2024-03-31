package com.lukullu.undersquare.entities;

import com.lukullu.tbck.enums.Actions;
import com.lukullu.tbck.utils.DeltaTimer;
import com.lukullu.tbck.utils.InputManager;
import com.tbck.math.Vec2;
import com.lukullu.undersquare.entityTypes.SegmentEntity;

public class Player extends SegmentEntity {

    private static final Vec2 MOVEMENT_FORCE = new Vec2(50,50);

    public Player(String psff_url, Vec2 position, double rotation, double scaling) {
        super(psff_url, position, rotation, scaling);
        mass = 2;
    }

    @Override
    public void update() {

        movement();
        super.update();
    }

    private void movement()
    {

        // core movement
        Vec2 deltaForce = Vec2.ZERO_VECTOR2;
        if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ deltaForce = deltaForce.add(MOVEMENT_FORCE.multiply(new Vec2( 0,-1))); }
        if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ deltaForce = deltaForce.add(MOVEMENT_FORCE.multiply(new Vec2( 0, 1))); }
        if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ deltaForce = deltaForce.add(MOVEMENT_FORCE.multiply(new Vec2(-1, 0))); }
        if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ deltaForce = deltaForce.add(MOVEMENT_FORCE.multiply(new Vec2( 1, 0))); }

        if(Math.abs(deltaForce.x) == Math.abs(deltaForce.y))
            deltaForce = deltaForce.multiply(0.707);

        // TODO: Charge/Dodge mechanic
        
        applyForce(deltaForce);

        if(InputManager.getInstance().isActionQueued(Actions.ROTATE_CLOCKWISE          )){ updateRot(PI * DeltaTimer.getInstance().getDeltaTime());   }
        if(InputManager.getInstance().isActionQueued(Actions.ROTATE_COUNTERCLOCKWISE   )){ updateRot(-PI * DeltaTimer.getInstance().getDeltaTime());   }
        if(InputManager.getInstance().isActionQueued(Actions.SLOWMO  )){ DeltaTimer.getInstance().setTimeModifier(0.75); } else {DeltaTimer.getInstance().setTimeModifier(1);}



        /*if(InputManager.getInstance().isActionQueued(Actions.FORWARD )){ updatePos(new Vec2(0  , -10)); }
        if(InputManager.getInstance().isActionQueued(Actions.BACKWARD)){ updatePos(new Vec2(0  , 10));  }
        if(InputManager.getInstance().isActionQueued(Actions.LEFT    )){ updatePos(new Vec2(-10, 0));   }
        if(InputManager.getInstance().isActionQueued(Actions.RIGHT   )){ updatePos(new Vec2(10 , 0));   }*/
    }

}
