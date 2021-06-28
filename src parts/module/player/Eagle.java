package client.module.player;

import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Eagle extends Module {
    public Eagle() {
        super("Eagle", 0, Category.MOVEMENT);
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            ItemStack i = this.mc.thePlayer.getCurrentEquippedItem();
            BlockPos bP = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.5D, this.mc.thePlayer.posZ);
            if (i != null) {
                if (i.getItem() instanceof ItemBlock) {
                    this.mc.gameSettings.keyBindSneak.pressed = false;
                    if (this.mc.theWorld.getBlockState(bP).getBlock() == Blocks.air) {
                        this.mc.gameSettings.keyBindSneak.pressed = true;
                    }
                }
            }
        }
    }
    @Override
    public void onEnable() {
        NotificationPublisher.queue("Eagle", "Eagle was Enable", NotificationType.INFO);
        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationPublisher.queue("Eagle", "Eagle was Disabled", NotificationType.Disable);
        super.onDisable();
    }
}