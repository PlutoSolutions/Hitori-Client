package client.module.combat;

import client.event.EventTarget;
import client.event.events.EventPacket;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals",0, Category.COMBAT);
    }
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (KillAura.target != null) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
            }

    }


}
