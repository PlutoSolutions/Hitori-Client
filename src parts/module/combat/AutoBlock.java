package client.module.combat;


import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;


import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoBlock extends Module {

    private static Minecraft mc;

    public AutoBlock() {
        super("AutoBlock",0, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate){
        if(mc.thePlayer.inventory.getCurrentItem() == null) {
            return;
        }
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(),
                this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
    }
    @EventTarget
    public static void startBlock(EventUpdate eventUpdate) {
        if(mc.thePlayer.isBlocking()) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
            mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(),
                    mc.thePlayer.getHeldItem().getMaxItemUseDuration());
        }
    }
    @EventTarget
    public static void stopBlock(EventUpdate eventUpdate) {
        if(mc.thePlayer.isBlocking()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
        }
    }


}
