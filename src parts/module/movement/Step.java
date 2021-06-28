package client.module.movement;


import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;

public class Step extends Module {

    public Step() {
        super("Step", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.stepHeight = 1.5F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.stepHeight = .5F;
    }
}
