package client.module.render;

import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;

public class SlowMotionMod extends Module {
    public SlowMotionMod() {
        super("SlowMotionMod", 0, Category.MISC);
    }
    @EventTarget
    public void onSlow(EventUpdate eventUpdate) {
        mc.timer.timerSpeed = (float) 0.8;
    }
    @Override
    public  void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }
}
