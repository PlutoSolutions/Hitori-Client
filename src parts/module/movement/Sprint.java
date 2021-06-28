package client.module.movement;


import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Keyboard.KEY_M, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.gameSettings.keyBindForward.isPressed())
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}
