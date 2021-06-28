package client.module.render;

import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventPacket;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambience extends Module {
    public Ambience() {
        super("Ambience", 0, Category.RENDER);
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {

        time += slow;
        mc.theWorld.setWorldTime(time);
        super.onUpdate();
    }

    @EventTarget
    public void onPacket(EventPacket event) {
        if (((EventPacket) event).getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
   public int time = 0;
     public int slow = 100;
@Override
    public void onEnable() {
        NotificationPublisher.queue("Ambience", "Ambience was Enable", NotificationType.INFO);
        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationPublisher.queue("Ambience", "Ambience was Disabled", NotificationType.Disable);
        super.onDisable();
    }
}
