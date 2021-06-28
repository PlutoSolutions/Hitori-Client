package client.module.player;

import client.event.Event;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module {
    public Phase() {
        super("Phase", 0, Category.MISC);
    }
    @EventTarget
    public void af(EventUpdate eventUpdate) {
        setToggled(false);
    }


        @Override
    public void onEnable() {
            if (mc.thePlayer.isCollidedHorizontally) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + -0.00000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
            }
        }

}


