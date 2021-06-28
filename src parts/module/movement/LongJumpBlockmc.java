package client.module.movement;

import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;

public class LongJumpBlockmc extends Module {
    private int air;

    public LongJumpBlockmc() {
        super("LongJumpBlockmc", 0, Category.MOVEMENT);
    }
    @EventTarget
    public void onIdap(EventUpdate eventUpdate) {
        if (!mc.thePlayer.onGround) {
            air++;
        } else if (air > 2) {
        }
        if (mc.thePlayer.onGround || air == 0) {
            mc.thePlayer.jump();
        }
        mc.thePlayer.jumpMovementFactor = (float) 0.100;
        mc.thePlayer.motionY += 0.010;
mc.timer.timerSpeed = 0.9f;


        }
        @Override
    public void onDisable() {
        mc.timer.timerSpeed  =1;
        super.onDisable();
        }

}
