package client.module.movement;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.BooleanValue;
import client.values.NumberValue;

public class HighJump extends Module {

    public HighJump() {
        super("HighJump", 0, Category.MOVEMENT);

    }
        @Override
        public void setup() {
            Client.instance.settingsManager.rSetting(new Setting("Speed", this, 0, 1, 4, true));

        }
    @EventTarget
    public void onfs(EventUpdate eventUpdate) {
        if (mc.thePlayer == null) return;
        if (this.mc.thePlayer.fallDistance != 0.0F)
            this.mc.thePlayer.motionY += 0.039D;
        if (this.mc.thePlayer.onGround)
            this.mc.thePlayer.jump();
        if (!this.mc.thePlayer.onGround) {
            this.mc.thePlayer.motionY += 0.075D;
            this.mc.thePlayer.motionX *= 1.065000057220459D;
            this.mc.thePlayer.motionZ *= 1.065000057220459D;
            mc.timer.timerSpeed = (float)Client.instance.settingsManager.getSettingByName("Speed").getValDouble();

        }
    }


    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;

        super.onDisable();
    }

    @Override
    public void onEnable() {
        NotificationPublisher.queue("HighJump", "HighJump was Enable", NotificationType.INFO);
        super.onEnable();

    }
}
