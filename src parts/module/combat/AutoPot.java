package client.module.combat;

import client.event.Event;
import client.event.EventTarget;
import client.event.events.EventPreMotionUpdate;
import client.module.Category;
import client.module.Module;
import client.utils.client.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class AutoPot extends Module {
    private float prevPitch;
    public static Minecraft mc = Minecraft.getMinecraft();
    private static Stopwatch timer = new Stopwatch();
    public boolean potting;

    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT);
    }

    @EventTarget
    public void onEvent(EventPreMotionUpdate e) {
        int prevSlot = mc.thePlayer.inventory.currentItem;
                    if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        if (timer.elapsed(50)) {
                            if (isSpeedPotsInHotBar() && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashSpeedPot(stack)) {
                                        potting = true;
                                        prevPitch = mc.thePlayer.rotationPitch;

                                    }
                                }
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashSpeedPot(stack) && potting) {
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
                                        sendPacketNoEvent(new C09PacketHeldItemChange(index - 36));
                                        sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, prevPitch, mc.thePlayer.onGround));
                                        break;

                            } else {
                                getSpeedPotsFromInventory();
                            }
                        }
                    }

                    if (!mc.thePlayer.isPotionActive(Potion.regeneration) && mc.thePlayer.getHealth() <= 6.0f * 2) {
                        if (timer.elapsed(50)) {
                            if (isRegenPotsInHotBar()) {
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashRegenPot(stack)) {
                                        potting = true;
                                        prevPitch = mc.thePlayer.rotationPitch;
                                    }
                                }
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashRegenPot(stack)) {
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
                                        sendPacketNoEvent(new C09PacketHeldItemChange(index - 36));
                                        sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, prevPitch, mc.thePlayer.onGround));
                                        break;
                                    }
                                }
                                timer.reset();
                                potting = false;
                            } else {
                                getRegenPotsFromInventory();
                            }
                        }
                    }

                    if (mc.thePlayer.getHealth() <= 6.0f * 2) {
                        if (timer.elapsed(50)) {
                            if (isHealthPotsInHotBar()) {
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashHealthPot(stack)) {
                                        potting = true;
                                        prevPitch = mc.thePlayer.rotationPitch;
                                    }
                                }
                                for (int index = 36; index < 45; index++) {
                                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                                    if (stack == null)
                                        continue;
                                    if (isStackSplashHealthPot(stack)) {
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
                                        sendPacketNoEvent(new C09PacketHeldItemChange(index - 36));
                                        sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                                        sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, prevPitch, mc.thePlayer.onGround));
                                        break;
                                    }
                                }
                                timer.reset();
                                potting = false;
                            } else {
                                getPotsFromInventory();
                            }
                        }
                    }
                } else if (e.isPost()) {
                    potting = false;
                }
        }
    }

    private boolean isSpeedPotsInHotBar() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (stack.getDisplayName().contains("Frog")) continue;
            if (isStackSplashSpeedPot(stack))
                return true;
        }
        return false;
    }

    private boolean isHealthPotsInHotBar() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isStackSplashHealthPot(stack))
                return true;
        }
        return false;
    }

    private boolean isRegenPotsInHotBar() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isStackSplashRegenPot(stack))
                return true;
        }
        return false;
    }

    private void getSpeedPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (stack.getDisplayName().contains("Frog")) continue;
            if (isStackSplashSpeedPot(stack)) {
                mc.playerController.windowClick(0, index, 6, 2, mc.thePlayer);
                break;
            }
        }
    }

    private int getPotionCount() {
        int count = 0;
        for (int index = 0; index < 45; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isStackSplashHealthPot(stack) || isStackSplashHealthPot(stack) || isStackSplashRegenPot(stack))
                count++;
        }
        return count;
    }

    private void getPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isStackSplashHealthPot(stack)) {
                mc.playerController.windowClick(0, index, 6, 2, mc.thePlayer);
                break;
            }
        }
    }

    private boolean isStackSplashSpeedPot(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (stack.getDisplayName().contains("Frog")) return false;
                    if (effect.getPotionID() == Potion.moveSpeed.id && effect.getPotionID() != Potion.jump.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isStackSplashHealthPot(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.heal.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void getRegenPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isStackSplashRegenPot(stack)) {
                mc.playerController.windowClick(0, index, 6, 2, mc.thePlayer);
                break;
            }
        }
    }

    private boolean isStackSplashRegenPot(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.regeneration.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void sendPacket(Packet packet){
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketNoEvent(Packet packet){
        mc.getNetHandler().addToSendQueue(packet);
    }



}

