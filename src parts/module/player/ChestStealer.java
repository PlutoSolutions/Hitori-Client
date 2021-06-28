package client.module.player;

import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.NumberValue;
import net.minecraft.client.gui.inventory.GuiChest;

/*    */
/*    */ public class ChestStealer extends Module
        /*    */ {
    /*    */   public static net.minecraft.network.play.server.S30PacketWindowItems packet;
    int delay;

    /*    */
    /*    */
    /*    */
    public ChestStealer()
    /*    */ {
        /* 13 */
        super("ChestStealer", 0, Category.PLAYER);


        /*    */
    }
@Override
public void setup() {
    Client.instance.settingsManager.rSetting(new Setting("Steal", this, 0, 0.1, 15, true));

}
    /*    */
    /*    */
    /*    */
    /*    */
    @EventTarget
    /*    */ public void onUpdate(EventUpdate event)
    /*    */ {
        /* 21 */
        this.delay += Client.instance.settingsManager.getSettingByName("Steal").getValDouble();
        if ((mc.currentScreen instanceof GuiChest)) {
            GuiChest chest = (GuiChest) mc.currentScreen;
            if (isChestEmpty(chest)) {
                mc.thePlayer.closeScreen();
                packet = null;
            }
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); index++) {
                net.minecraft.item.ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                if ((stack != null) && (this.delay > 2) && (!stack.hasDisplayName())) {
                    mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                    this.delay = 0;
                }
            }
        }
    }

    private boolean isChestEmpty(GuiChest chest) {
        for (int index = 0; index <= chest.lowerChestInventory.getSizeInventory(); index++) {
            net.minecraft.item.ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
            if (stack != null) {
                return false;
            }
        }
        return true;
    }
}
